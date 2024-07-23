package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import theme.MyTheme

@Composable
internal fun IconInfoDialog(
    onValidateClick: (parent: String, group: String, name: String) -> Unit,
    onCancelClick: () -> Unit,
) {
    val iconParent = remember { mutableStateOf(TextFieldValue("WhIcons")) }
    val iconGroup = remember { mutableStateOf(TextFieldValue("")) }
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
                OutlinedTextField(
                    value = iconParent.value,
                    onValueChange = { iconParent.value = it },
                    label = { Text("Icon parent name") },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = iconGroup.value,
                    onValueChange = { iconGroup.value = it },
                    label = { Text("Icon group name") },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = iconName.value,
                    onValueChange = { iconName.value = it },
                    label = { Text("Icon name") },
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            TextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp),
                onClick = {
                    if (iconName.value.text.isNotBlank()) {
                        onValidateClick(iconParent.value.text, iconGroup.value.text, iconName.value.text)
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
            onValidateClick = { _, _, _ -> },
            onCancelClick = {},
        )
    }
}