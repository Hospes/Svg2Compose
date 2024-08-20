package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import app.s2c.theme.MyTheme

@Composable
internal fun IconInfoDialog(
    onValidateClick: (receiverType: String, name: String) -> Unit,
    onCancelClick: () -> Unit,
) {
    val iconReceiverType = remember { mutableStateOf(TextFieldValue("WhIcons")) }
    val iconName = remember { mutableStateOf(TextFieldValue("")) }
    var hasError by remember { mutableStateOf(false) }
    DialogWindow(
        onCloseRequest = onCancelClick,
        title = "Choose an icon name",
        onKeyEvent = { keyEvent ->
            if (keyEvent.key == Key.Escape) onCancelClick()
            true
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
            ) {
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    value = iconReceiverType.value,
                    onValueChange = { iconReceiverType.value = it },
                    label = { Text("Icon receiver type") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = iconName.value,
                    onValueChange = { iconName.value = it },
                    label = { Text("Icon name") },
                    isError = hasError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            TextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp),
                onClick = {
                    if (iconName.value.text.isNotBlank()) {
                        onValidateClick(iconReceiverType.value.text, iconName.value.text)
                    } else {
                        hasError = true
                    }
                },
            ) {
                Text("Copy code to clipboard")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyTheme {
        IconInfoDialog(
            onValidateClick = { _, _ -> },
            onCancelClick = {},
        )
    }
}