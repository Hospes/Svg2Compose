package app.s2c.ui.converter

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.s2c.ui.common.input.TextInputState
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.common.ui.AppSwitch
import app.s2c.ui.common.ui.AppTextField
import app.s2c.ui.common.ui.AppToggleGroup
import app.s2c.ui.common.ui.spaceBetween
import app.s2c.ui.di.injectedViewModel
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinx.serialization.Serializable

@Serializable
data object ConverterScreen

@Composable
internal fun ConverterScreen(
    navigateConfig: () -> Unit,
) {
    ConverterScreen(
        viewModel = injectedViewModel(),
        navigateConfig = navigateConfig,
    )
}

@Composable
private fun ConverterScreen(
    viewModel: ConverterViewModel,
    navigateConfig: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConverterScreen(
        state = state,
        navigateConfig = navigateConfig,
        onFilePicked = viewModel::onFilePicked,
        sourceCodeInputState = viewModel.sourceCodeInputState,
        onSelectParser = viewModel::onSelectParser,
        onShowPreview = viewModel::onShowPreview,
        iconNameInputState = viewModel.iconNameInputState,
        outputCodeInputState = viewModel.outputCodeInputState,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ConverterScreen(
    state: ConverterViewState,
    navigateConfig: () -> Unit,
    onFilePicked: (PlatformFile?) -> Unit,
    sourceCodeInputState: TextInputState,
    onSelectParser: (ConverterViewState.Input.Parser) -> Unit,
    onShowPreview: (Boolean) -> Unit,
    iconNameInputState: TextInputState,
    outputCodeInputState: TextInputState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync, // Placeholder for custom icon
                            contentDescription = "App Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "Svg2Compose", fontWeight = FontWeight.Bold)
                    }
                },
                subtitle = { Text(text = "Convert Android Vector Drawables and SVG Paths to Jetpack Compose `ImageVector` code.") },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        // Use BoxWithConstraints to create a responsive layout
        BoxWithConstraints {
            val isLandscape = maxWidth > 1100.dp // Breakpoint for side-by-side layout
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddings)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InputPanel(
                        state = state.input,
                        onFilePicked = onFilePicked,
                        sourceCodeInputState = sourceCodeInputState,
                        onSelectParser = onSelectParser,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    )
                    OutputPanel(
                        state = state.output,
                        navigateConfig = navigateConfig,
                        onShowPreview = onShowPreview,
                        iconNameInputState = iconNameInputState,
                        outputCodeInputState = outputCodeInputState,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddings)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InputPanel(
                        state = state.input,
                        onFilePicked = onFilePicked,
                        sourceCodeInputState = sourceCodeInputState,
                        onSelectParser = onSelectParser,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp, max = 600.dp),
                    )
                    OutputPanel(
                        state = state.output,
                        navigateConfig = navigateConfig,
                        onShowPreview = onShowPreview,
                        iconNameInputState = iconNameInputState,
                        outputCodeInputState = outputCodeInputState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp, max = 600.dp),
                    )
                }
            }
        }
    }
}


// --- INPUT PANEL ---
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun InputPanel(
    state: ConverterViewState.Input,
    onFilePicked: (PlatformFile?) -> Unit,
    sourceCodeInputState: TextInputState,
    onSelectParser: (ConverterViewState.Input.Parser) -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    border: BorderStroke = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Text("1. Provide Input", style = MaterialTheme.typography.titleMedium)

        Column(
            modifier = Modifier,
        ) {
            val topElementShape = remember(shape) {
                RoundedCornerShape(
                    topStart = shape.topStart,
                    topEnd = shape.topEnd,
                    bottomStart = ZeroCornerSize,
                    bottomEnd = ZeroCornerSize
                )
            }
            val bottomElementShape = remember(shape) {
                RoundedCornerShape(
                    topStart = ZeroCornerSize,
                    topEnd = ZeroCornerSize,
                    bottomStart = shape.bottomStart,
                    bottomEnd = shape.bottomEnd
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spaceBetween(minSpace = 16.dp),
                modifier = Modifier.fillMaxWidth()
                    .clip(topElementShape)
                    .border(border = border, shape = topElementShape)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(8.dp),
            ) {
                val filePicker = rememberFilePickerLauncher(
                    type = FileKitType.File(extensions = listOf("svg"))
                ) { file -> onFilePicked(file) }
                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 40.dp) {
                    TextButton(
                        onClick = { filePicker.launch() },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                    ) {
                        Icon(imageVector = Icons.Default.FileOpen, contentDescription = "Open file")
                        Spacer(Modifier.width(8.dp))
                        Text(text = "Load from file...")
                    }
                    ParserSwitcher(
                        selected = state.parser,
                        onSelectParser = onSelectParser,
                    )
                }
            }

            AppTextField(
                state = sourceCodeInputState,
                textStyle = AppTheme.typography.sourceCode, //LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
                lineLimits = TextFieldLineLimits.MultiLine(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                ),
                shape = bottomElementShape,
                modifier = Modifier.fillMaxWidth().weight(1f),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ParserSwitcher(
    selected: ConverterViewState.Input.Parser,
    onSelectParser: (ConverterViewState.Input.Parser) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppToggleGroup(
        modifier = modifier,
    ) {
        OutlinedToggleButton(
            checked = selected == ConverterViewState.Input.Parser.SVG,
            onCheckedChange = { if (it) onSelectParser(ConverterViewState.Input.Parser.SVG) },
            shapes = firstShapes,
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
        ) { Text("SVG") }
        OutlinedToggleButton(
            checked = selected == ConverterViewState.Input.Parser.VECTOR,
            onCheckedChange = { if (it) onSelectParser(ConverterViewState.Input.Parser.VECTOR) },
            shapes = lastShapes,
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
        ) { Text("VECTOR") }
    }
}

// --- OUTPUT PANEL ---
@Composable
private fun OutputPanel(
    state: ConverterViewState.Output,
    navigateConfig: () -> Unit,
    onShowPreview: (Boolean) -> Unit,
    iconNameInputState: TextInputState,
    outputCodeInputState: TextInputState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Text("2. Get Result", style = MaterialTheme.typography.titleMedium)

        when (state) {
            is ConverterViewState.Output.Result -> ResultView(
                state = state,
                navigateConfig = navigateConfig,
                onShowPreview = onShowPreview,
                iconNameInputState = iconNameInputState,
                outputCodeInputState = outputCodeInputState,
                modifier = Modifier.fillMaxSize()
            )

            is ConverterViewState.Output.Placeholder -> PlaceholderView(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.large
                    ),
            )
        }
    }
}

// --- PLACEHOLDER VIEW ---
@Composable
private fun PlaceholderView(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star, // Placeholder for wand icon
            contentDescription = "Placeholder",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(Modifier.height(16.dp))
        Text("Your generated code will appear here.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// --- RESULT VIEW ---
@Composable
private fun ResultView(
    state: ConverterViewState.Output.Result,
    navigateConfig: () -> Unit,
    onShowPreview: (Boolean) -> Unit,
    iconNameInputState: TextInputState,
    outputCodeInputState: TextInputState,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    border: BorderStroke = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
) {
    Column(modifier = modifier) {
        val topElementShape = remember(shape) {
            RoundedCornerShape(
                topStart = shape.topStart,
                topEnd = shape.topEnd,
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize
            )
        }
        val bottomElementShape = remember(shape) {
            RoundedCornerShape(
                topStart = ZeroCornerSize,
                topEnd = ZeroCornerSize,
                bottomStart = shape.bottomStart,
                bottomEnd = shape.bottomEnd
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spaceBetween(minSpace = 16.dp),
            modifier = Modifier.fillMaxWidth()
                .clip(topElementShape)
                .border(border = border, shape = topElementShape)
                .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(8.dp),
        ) {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 40.dp) {
                TextButton(
                    onClick = navigateConfig,
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Open file")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Config")
                }

                AppTextField(
                    state = iconNameInputState,
                    placeholder = { Text("Icon name here...") },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    trailingIcon = {
                        val clipboard = LocalClipboardManager.current
                        TextButton(
                            onClick = {
                                outputCodeInputState.fieldState.text.toString()
                                    .let { clipboard.setText(AnnotatedString(it)) }
                                //TODO: Let user know that code was copied!
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        ) {
                            Icon(Icons.Default.ContentCopy, "Copy", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Copy")
                        }
                    },
                    modifier = Modifier.height(40.dp).weight(1f),
                )

                AppSwitch(
                    checked = state.preview != null,
                    onCheckedChange = { onShowPreview(it) },
                ) { Text("PREVIEW") }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AppTextField(
                state = outputCodeInputState,
                readOnly = true,
                textStyle = AppTheme.typography.sourceCode, //LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
                lineLimits = TextFieldLineLimits.MultiLine(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                ),
                shape = bottomElementShape,
                modifier = Modifier.matchParentSize(),
            )
            if (state.preview != null)
                IconPreviewCard(
                    icon = state.preview,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(64.dp),
                )
        }
    }
}

// --- ICON PREVIEW CARD ---
@Composable
private fun IconPreviewCard(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = icon,
        contentDescription = "Preview Icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.surfaceBright, shape = MaterialTheme.shapes.medium)
    )
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        ConverterScreen(
            state = ConverterViewState.Init,
            navigateConfig = {},
            onFilePicked = {},
            sourceCodeInputState = TextInputState.Preview,
            onSelectParser = {},
            onShowPreview = {},
            iconNameInputState = TextInputState.Preview,
            outputCodeInputState = TextInputState.Preview,
        )
    }
}