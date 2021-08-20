package com.example.synopsiscompose.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.synopsiscompose.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldAlertDialog(
    dialogTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String) -> Unit
) {

    val text = rememberSaveable { mutableStateOf("") }
    val error = rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                Text(
                    text = dialogTitle,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))
                TextField(value = text.value, onValueChange = { text.value = it })
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (text.value.isBlank()) {
                        error.value = true
                    } else {
                        onConfirmClick(text.value)
                    }
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(stringResource(id = R.string.dialog_create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = android.R.string.cancel))
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    )
}