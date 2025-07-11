package app.s2c.ui.converter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.verticalScroll
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
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ConverterScreen(
    state: ConverterViewState,
    sourceCodeInputState: TextInputState,
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
                            sourceCodeInputState = sourceCodeInputState,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                        )
                        OutputPanel(
                            state = state.output,
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
                            sourceCodeInputState = sourceCodeInputState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 300.dp, max = 600.dp),
                        )
                        OutputPanel(
                            state = state.output,
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
@Composable
private fun InputPanel(
    sourceCodeInputState: TextInputState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Text("1. Provide Input", style = MaterialTheme.typography.titleMedium)

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
            modifier = Modifier.fillMaxWidth().weight(1f),
        )
    }
}

// --- OUTPUT PANEL ---
@Composable
private fun OutputPanel(
    state: ConverterViewState.Output,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        Text("2. Get Result", style = MaterialTheme.typography.titleMedium)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ConverterViewState.Output.Error -> Unit
                is ConverterViewState.Output.Code -> Unit
                is ConverterViewState.Output.Preview -> IconPreviewCard(state = state)
                is ConverterViewState.Output.Placeholder -> PlaceholderView()
            }
        }
    }
}

// --- PLACEHOLDER VIEW ---
@Composable
private fun PlaceholderView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
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
    state: ConverterViewState.Output,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GeneratedCodeCard()

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

// --- ICON PREVIEW CARD ---
@Composable
private fun IconPreviewCard(
    state: ConverterViewState.Output.Preview,
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
        )
    }
}