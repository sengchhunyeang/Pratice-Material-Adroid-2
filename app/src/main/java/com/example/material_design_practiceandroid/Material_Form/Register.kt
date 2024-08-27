package com.example.material_design_practiceandroid.Material_Form

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.material_design_practiceandroid.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm() {
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var selected by remember { mutableStateOf("") }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var isDialogOpen by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(20.dp)) {
        val imageModifier = Modifier
            .size(50.dp)
        Row(modifier = Modifier.fillMaxWidth()) {

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.hrdlogo),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Fit,
                modifier = imageModifier
            )

            // Call button sheet
            ChangeLanguage()
        }
        Column() {
            Text(
                text = "Register Form",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = lastName,
                onValueChange = { newName -> lastName = newName },
                label = { Text("Input Your Name") },
                placeholder = { Text(text = "Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { newName -> firstName = newName },
                label = { Text(text = "Input Your Firstname") },
                placeholder = { Text(text = "First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

        }
        //Gender
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Gender")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Male")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
                Text(text = "Female")
            }
        }
        // DropDown
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            SchoolDropdown()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column() {
            Row() {
                Button(
                    onClick = {
                        lastName = ""
                        firstName = ""
                        gender = "Male"
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(text = "Reset")
                }
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Button(
                    onClick = { isDialogOpen=true }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = "Save")
                }
            }

        }
        if (isDialogOpen) {
            FullScreenDialog(onDismiss = { isDialogOpen = false })
        }

    }

}

// Button change language
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeLanguage() {
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedCountry by remember {
        mutableStateOf(Pair("Khmer", "\uD83C\uDDF0\uD83C\uDDED"))
    }
    Button(
        onClick = { isSheetOpen = true }, modifier = Modifier, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            contentColor = Color.Black
        )
    ) {
        Text(text = "${selectedCountry.second} ${selectedCountry.first}")
    }
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            modifier = Modifier
        ) {
            CountryList(onCountrySelected = { country ->
                selectedCountry = country
                isSheetOpen = false
            })
        }
    }
}

@Composable
fun CountryList(onCountrySelected: (Pair<String, String>) -> Unit) {
    var countries: List<Pair<String, String>> = listOf(
        Pair("Khmer", "\uD83C\uDDF0\uD83C\uDDED"),
        Pair("Korean", "\uD83C\uDDF0\uD83C\uDDF7"),
        Pair("English", "\uD83C\uDDEC\uD83C\uDDE7")
    )
    LazyColumn {
        items(countries) { country: Pair<String, String> ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCountrySelected(country) }
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(text = country.second, modifier = Modifier.padding(end = 20.dp))
                Text(text = country.first)
            }
        }
    }
}
//========================
@Composable
fun FullScreenDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Your data has been saved!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Close", color = Color.White)
                }
            }
        }
    }
}