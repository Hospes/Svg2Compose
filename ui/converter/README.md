# UI Converter Module

## 📋 Module Overview

The `ui:converter` module implements the main conversion interface for Svg2Compose. It provides the primary user interface for converting Vector Drawable files and SVG paths into Jetpack Compose ImageVector code. This module handles the core user workflow and integrates with the data layer to perform conversions.

## 🔑 Key Features & Components

### Conversion Interface
- **Tabbed Input Interface** - Separate tabs for Vector Drawable and SVG path input
- **Real-time Preview** - Live preview of conversion results
- **Three-Column Layout** - Paths, generated code, and rendered preview
- **Copy-to-Clipboard** - Easy code copying functionality

### Input Handling
- **Text Input Fields** - Large text areas for XML and SVG path input
- **File Selection** - File picker for loading Vector Drawable files
- **Drag-and-Drop Support** - Direct file dropping for quick conversion
- **Input Validation** - Real-time validation of input formats

### Output Management
- **Code Generation** - Clean, formatted ImageVector code output
- **Syntax Highlighting** - Highlighted Kotlin code display
- **Export Options** - Save generated code to files
- **Preview Rendering** - Visual preview of the converted vector

### Color Management
- **Unknown Color Detection** - Automatic detection of unmapped colors
- **Color Mapping Dialog** - Interactive color value mapping
- **Color Validation** - Hex color format validation
- **Color Preview** - Visual color representation

## 🔧 How It Works

### Conversion Workflow
1. **Input Selection**: User chooses between Vector Drawable or SVG path input
2. **Content Entry**: User enters or loads the source content
3. **Conversion Trigger**: User clicks the convert button
4. **Processing**: Content is parsed and converted to ImageVector format
5. **Color Resolution**: Unknown colors are mapped through user interaction
6. **Output Display**: Results are shown in the three-column layout
7. **Code Export**: User can copy or save the generated code

### Key Components

#### `ConverterScreen`
Main screen composable that orchestrates the conversion interface:
```kotlin
@Composable
fun ConverterScreen(
    onNavigateToConfig: () -> Unit
) {
    // Main conversion interface implementation
}
```

#### Input Processing
- **Vector Drawable Parser**: Processes Android XML vector files
- **SVG Path Parser**: Handles SVG path data conversion
- **File Reader**: Loads content from selected files
- **Validation Engine**: Validates input format and structure

#### Output Generation
- **Code Formatter**: Generates clean, readable Kotlin code
- **Preview Renderer**: Creates visual representation of vectors
- **Export Manager**: Handles code saving and clipboard operations

## 🏗️ Module-Specific Dependencies

### UI Dependencies
- **Compose Navigation**: Screen navigation and routing
- **Compose Material**: UI components and theming
- **Common UI Compose**: Shared UI components and patterns

### Data Dependencies
- `data` - Access to parsing engines and conversion logic
- `models` - Data models for conversion results

### Core Dependencies
- `core.base` - Base utilities and dependency injection
- `core.preferences` - User preferences and settings

### External Libraries
- **Compose Foundation**: Basic Compose functionality
- **Compose Animation**: Smooth transitions and animations
- **Kotlin Coroutines**: Asynchronous operations

## 🔄 Integration Points

### With Data Layer
- **Parser Integration**: Uses SVG and Vector Drawable parsers
- **Model Transformation**: Converts parsed data to UI models
- **Error Handling**: Processes parsing errors and validation issues

### With Navigation
- **Screen Registration**: Registers converter screen in navigation graph
- **Deep Linking**: Supports direct navigation to converter
- **State Preservation**: Maintains conversion state across navigation

### With Configuration
- **Settings Integration**: Uses conversion preferences and defaults
- **Theme Support**: Adapts to user theme preferences
- **Layout Preferences**: Respects user layout and display settings

## 🎨 User Interface Components

### Input Section
- **Tab Selector**: Switch between Vector Drawable and SVG path modes
- **Text Editor**: Large, scrollable text input with syntax awareness
- **File Picker**: Browse and select Vector Drawable files
- **Clear Button**: Reset input content

### Processing Section
- **Convert Button**: Trigger conversion process
- **Progress Indicator**: Show conversion progress
- **Error Display**: Show validation and parsing errors
- **Status Messages**: Inform user of conversion status

### Output Section
- **Path Display**: Show extracted path data
- **Code Display**: Formatted ImageVector code with syntax highlighting
- **Preview Panel**: Rendered vector image preview
- **Copy Actions**: Copy code to clipboard or save to file

### Color Mapping
- **Color Detection**: Automatic unknown color identification
- **Mapping Dialog**: Interactive color value input
- **Color Preview**: Visual representation of mapped colors
- **Validation Feedback**: Real-time color format validation

## 🚀 Usage Examples

### Basic Conversion Flow
```kotlin
@Composable
fun ConversionExample() {
    var inputText by remember { mutableStateOf("") }
    var conversionResult by remember { mutableStateOf<ConversionResult?>(null) }
    
    Column {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Vector Drawable XML") }
        )
        
        Button(
            onClick = { 
                conversionResult = convertVectorDrawable(inputText)
            }
        ) {
            Text("Convert")
        }
        
        conversionResult?.let { result ->
            CodeDisplay(code = result.imageVectorCode)
            ImagePreview(imageVector = result.imageVector)
        }
    }
}
```

### Color Mapping Integration
```kotlin
@Composable
fun ColorMappingExample() {
    var unknownColors by remember { mutableStateOf<Set<String>>(emptySet()) }
    var colorMappings by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    
    if (unknownColors.isNotEmpty()) {
        ColorMappingDialog(
            unknownColors = unknownColors,
            onColorsMaped = { mappings ->
                colorMappings = mappings
                unknownColors = emptySet()
            }
        )
    }
}
```

## 🎯 Design Principles

### User Experience
- **Immediate Feedback**: Real-time validation and preview
- **Error Recovery**: Clear error messages and recovery options
- **Workflow Efficiency**: Streamlined conversion process
- **Accessibility**: Keyboard navigation and screen reader support

### Performance
- **Lazy Loading**: Components loaded as needed
- **Efficient Rendering**: Optimized recomposition patterns
- **Memory Management**: Proper cleanup of resources
- **Background Processing**: Non-blocking conversion operations

This module serves as the primary user interface for the core functionality of Svg2Compose, providing an intuitive and efficient conversion experience.
