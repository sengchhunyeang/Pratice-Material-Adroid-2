package com.example.material_design_practiceandroid.Material_Form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    context: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {
            Text(text = "handle view", modifier = Modifier.padding(20.dp))
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xABABABFF))
                .padding(16.dp),

            ) {

        }
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(true)
    }
    MakeBottomSheet(onDismissRequest = { /*TODO*/ }, sheetState = sheetState) {
        Text(text = "This is bottom sheet ")
        Button(onClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        }) {
            Text(text = "Dismiss button sheet ")
        }
    }

}
