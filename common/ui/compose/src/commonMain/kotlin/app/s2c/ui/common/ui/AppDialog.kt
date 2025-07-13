package app.s2c.ui.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppColumnDialog(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) = AppColumnDialog(
    onClose = onClose,
    modifier = modifier,
    contentPadding = contentPadding,
    verticalArrangement = verticalArrangement,
) {
    ProvideTextStyle(value = MaterialTheme.typography.titleLarge) {
        title()
    }
    content()
}

@Composable
fun AppColumnDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) {
    AppDialog(
        onClose = onClose,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier.padding(contentPadding),
        ) {
            content()
        }
    }
}

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onSurface,
        ) {
            onClose?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.align(Alignment.TopEnd),
                ) { Icon(imageVector = Icons.Default.Close, contentDescription = null) }
            }

            content()
        }
    }
}