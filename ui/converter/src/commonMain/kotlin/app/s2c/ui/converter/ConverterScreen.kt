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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.s2c.ui.common.input.TextInputState
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.common.ui.AppTextField
import app.s2c.ui.common.ui.AppToggleGroup
import app.s2c.ui.common.ui.spaceBetween
import com.teobaranga.kotlin.inject.viewmodel.runtime.compose.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
data object ConverterScreen

@Composable
internal fun ConverterScreen() {
    ConverterScreen(
        viewModel = injectedViewModel(),
    )
}

@Composable
private fun ConverterScreen(
    viewModel: ConverterViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConverterScreen(
        state = state,
        sourceCodeInputState = viewModel.sourceCodeInputState,
        onSelectParser = viewModel::onSelectParser,
        onSelectedPreviewType = viewModel::onSelectedPreviewType,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ConverterScreen(
    state: ConverterViewState,
    sourceCodeInputState: TextInputState,
    onSelectParser: (ConverterViewState.Input.Parser) -> Unit,
    onSelectedPreviewType: (ConverterViewState.Output.Result.Type) -> Unit,
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
        Column(
            modifier = Modifier.padding(paddings).padding(16.dp),
        ) {
            // Use BoxWithConstraints to create a responsive layout
            BoxWithConstraints {
                val isLandscape = maxWidth > 1100.dp // Breakpoint for side-by-side layout
                if (isLandscape) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InputPanel(
                            state = state.input,
                            sourceCodeInputState = sourceCodeInputState,
                            onSelectParser = onSelectParser,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                        )
                        OutputPanel(
                            state = state.output,
                            onSelectedPreviewType = onSelectedPreviewType,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InputPanel(
                            state = state.input,
                            sourceCodeInputState = sourceCodeInputState,
                            onSelectParser = onSelectParser,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 300.dp, max = 600.dp),
                        )
                        OutputPanel(
                            state = state.output,
                            onSelectedPreviewType = onSelectedPreviewType,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 300.dp, max = 600.dp),
                        )
                    }
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
                RoundedCornerShape(topStart = shape.topStart, topEnd = shape.topEnd, bottomStart = ZeroCornerSize, bottomEnd = ZeroCornerSize)
            }
            val bottomElementShape = remember(shape) {
                RoundedCornerShape(topStart = ZeroCornerSize, topEnd = ZeroCornerSize, bottomStart = shape.bottomStart, bottomEnd = shape.bottomEnd)
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
                        onClick = {},
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
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
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
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
        ) { Text("SVG") }
        OutlinedToggleButton(
            checked = selected == ConverterViewState.Input.Parser.VECTOR,
            onCheckedChange = { if (it) onSelectParser(ConverterViewState.Input.Parser.VECTOR) },
            shapes = lastShapes,
        ) { Text("VECTOR") }
    }
}

// --- OUTPUT PANEL ---
@Composable
private fun OutputPanel(
    state: ConverterViewState.Output,
    onSelectedPreviewType: (ConverterViewState.Output.Result.Type) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Text("2. Get Result", style = MaterialTheme.typography.titleMedium)

        when (state) {
            is ConverterViewState.Output.Error -> Unit

            is ConverterViewState.Output.Result -> ResultView(
                state = state,
                onSelectedPreviewType = onSelectedPreviewType,
                modifier = Modifier.fillMaxSize()
            )

            is ConverterViewState.Output.Placeholder -> PlaceholderView(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = MaterialTheme.shapes.large),
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
    onSelectedPreviewType: (ConverterViewState.Output.Result.Type) -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    border: BorderStroke = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
) {
    Column(modifier = modifier) {
        val topElementShape = remember(shape) {
            RoundedCornerShape(topStart = shape.topStart, topEnd = shape.topEnd, bottomStart = ZeroCornerSize, bottomEnd = ZeroCornerSize)
        }
        val bottomElementShape = remember(shape) {
            RoundedCornerShape(topStart = ZeroCornerSize, topEnd = ZeroCornerSize, bottomStart = shape.bottomStart, bottomEnd = shape.bottomEnd)
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
                    onClick = {},
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                ) {
                    Icon(imageVector = Icons.Default.FileOpen, contentDescription = "Open file")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Load from file...")
                }
                PreviewTypeSwitcher(
                    selected = state,
                    onSelected = onSelectedPreviewType,
                )
            }
        }

        when (state) {
            is ConverterViewState.Output.Result.Code -> GeneratedCodeCard()
            is ConverterViewState.Output.Result.Preview -> IconPreviewCard(state = state, modifier = modifier)
        }


        Button(
            onClick = { /* TODO: Export logic */ },
            modifier = Modifier.fillMaxWidth().height(48.dp),
        ) {
            Icon(Icons.Default.Download, contentDescription = "Export")
            Spacer(Modifier.width(8.dp))
            Text("Export to .kt file", fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PreviewTypeSwitcher(
    selected: ConverterViewState.Output.Result,
    onSelected: (ConverterViewState.Output.Result.Type) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppToggleGroup(
        modifier = modifier,
    ) {
        OutlinedToggleButton(
            checked = selected is ConverterViewState.Output.Result.Preview,
            onCheckedChange = { if (it) onSelected(ConverterViewState.Output.Result.Type.PREVIEW) },
            shapes = firstShapes,
        ) { Text("PREVIEW") }
        OutlinedToggleButton(
            checked = selected is ConverterViewState.Output.Result.Code,
            onCheckedChange = { if (it) onSelected(ConverterViewState.Output.Result.Type.CODE) },
            shapes = lastShapes,
        ) { Text("CODE") }
    }
}

// --- ICON PREVIEW CARD ---
@Composable
private fun IconPreviewCard(
    state: ConverterViewState.Output.Result.Preview,
    modifier: Modifier = Modifier,
) {
    var previewBgColor = true
    var zoom by remember { mutableStateOf(1f) }

    Card(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Icon Preview", fontWeight = FontWeight.SemiBold)
                Row {
                    IconButton(onClick = { zoom = (zoom - 0.2f).coerceAtLeast(0.4f) }) {
                        Icon(Icons.Default.ZoomOut, "Zoom Out")
                    }
                    IconButton(onClick = { zoom = (zoom + 0.2f).coerceAtMost(2f) }) {
                        Icon(Icons.Default.ZoomIn, "Zoom In")
                    }
                    IconButton(onClick = { previewBgColor = !previewBgColor }) {
                        Icon(Icons.Default.Contrast, "Toggle Background")
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (previewBgColor) MaterialTheme.colorScheme.surfaceBright else MaterialTheme.colorScheme.surfaceDim),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = state.icon,
                    contentDescription = "Preview Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size((64 * zoom).dp)
                )
            }
        }
    }
}

// --- GENERATED CODE CARD ---
@Composable
private fun GeneratedCodeCard(modifier: Modifier = Modifier) {
    val generatedCode = """
public val MyIcons.Filled.CheckCircle: ImageVector
    get() {
        if (_checkCircle != null) {
            return _checkCircle!!
        }
        _checkCircle = Builder(
            name = "CheckCircle", 
            defaultWidth = 24.0.dp,
            ...
        ).build()
        return _checkCircle!!
    }
    """.trimIndent()

    Card(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Generated Code", fontWeight = FontWeight.SemiBold)
                TextButton(onClick = { /* TODO: Copy logic */ }) {
                    Icon(Icons.Default.ContentCopy, "Copy", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Copy")
                }
            }
            OutlinedTextField(
                value = generatedCode,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxSize(),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp, fontFamily = FontFamily.Monospace),
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        ConverterScreen(
            state = ConverterViewState.Init,
            sourceCodeInputState = TextInputState.Preview,
            onSelectParser = {},
            onSelectedPreviewType = {},
        )
    }
}