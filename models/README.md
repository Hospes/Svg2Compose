# Models Module

## 📋 Module Overview

The `models` module defines the core data models, entities, and type definitions used throughout the Svg2Compose application. It provides a centralized location for all data structures that represent vector graphics, conversion results, configuration settings, and application state, ensuring consistency and type safety across all modules.

## 🔑 Key Features & Components

### Core Data Models
- **Vector Graphics Models** - Representations of SVG and Vector Drawable data
- **Conversion Models** - Results and intermediate data from parsing operations
- **Configuration Models** - Settings and preference data structures
- **UI State Models** - Application and screen state representations

### Type Definitions
- **Primitive Types** - Custom type aliases for domain-specific values
- **Enum Definitions** - Enumerated types for categorical data
- **Sealed Classes** - Type-safe hierarchies for variant data
- **Data Classes** - Immutable data containers with value semantics

### Validation Support
- **Constraint Definitions** - Data validation rules and constraints
- **Validation Extensions** - Helper functions for model validation
- **Error Types** - Structured error representations
- **Result Wrappers** - Success/failure result containers

## 🔧 How It Works

### Model Architecture
The module organizes models into logical categories:

```kotlin
// Vector graphics representation
data class IconFileContents(
    val name: String,
    val defaultWidth: Dp,
    val defaultHeight: Dp,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val nodes: List<ImageVectorNode>
)

// Path data representation
sealed class PathNodes {
    data class MoveTo(val x: Float, val y: Float) : PathNodes()
    data class LineTo(val x: Float, val y: Float) : PathNodes()
    data class CurveTo(val x1: Float, val y1: Float, val x2: Float, val y2: Float, val x3: Float, val y3: Float) : PathNodes()
    object Close : PathNodes()
}
```

### Key Model Categories

#### Vector Graphics Models
- **`IconFileContents`**: Complete icon representation with metadata
- **`ImageVectorNode`**: Individual vector elements (paths, groups)
- **`PathNodes`**: Vector path command sequences
- **`VectorGroup`**: Grouped vector elements with transformations

#### Conversion Models
- **`ConversionResult`**: Results from parsing and conversion operations
- **`ParsingError`**: Detailed error information from failed parsing
- **`ColorMapping`**: Color value mappings for unknown colors
- **`ConversionConfig`**: Configuration for conversion operations

#### Configuration Models
- **`ParserConfig`**: Parser behavior and options
- **`UIPreferences`**: User interface preferences and settings
- **`ExportSettings`**: Code generation and export preferences
- **`ApplicationSettings`**: Global application configuration

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Standard Library**: Basic data types and collections
- **Compose UI**: UI-related types (Dp, Color, etc.)

### Minimal External Dependencies
This module intentionally has minimal dependencies to serve as a stable foundation for other modules.

## 🔄 Integration Points

### With Data Module
- **Parsing Results**: Models represent parsed vector data
- **Configuration**: Parser configuration models
- **Error Handling**: Structured error representations

### With UI Modules
- **State Models**: UI state and interaction models
- **Display Data**: Models optimized for UI display
- **User Input**: Models for capturing user input and preferences

### With Core Modules
- **Preferences**: Models for preference storage and retrieval
- **Base Utilities**: Common model patterns and utilities

## 🎯 Model Categories

### Vector Graphics
```kotlin
data class IconFileContents(
    val name: String,
    val defaultWidth: Dp,
    val defaultHeight: Dp,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val nodes: List<ImageVectorNode>,
    val tintColor: Color? = null
)

sealed class ImageVectorNode {
    data class Path(
        val pathData: List<PathNodes>,
        val fill: Brush? = null,
        val stroke: Brush? = null,
        val strokeWidth: Float = 1f,
        val fillType: PathFillType = PathFillType.NonZero
    ) : ImageVectorNode()
    
    data class Group(
        val name: String? = null,
        val rotation: Float = 0f,
        val scaleX: Float = 1f,
        val scaleY: Float = 1f,
        val translationX: Float = 0f,
        val translationY: Float = 0f,
        val clipPathData: List<PathNodes> = emptyList(),
        val children: List<ImageVectorNode> = emptyList()
    ) : ImageVectorNode()
}
```

### Conversion Results
```kotlin
data class ConversionResult(
    val success: Boolean,
    val iconData: IconFileContents?,
    val generatedCode: String?,
    val errors: List<ConversionError> = emptyList(),
    val warnings: List<String> = emptyList(),
    val unknownColors: Set<String> = emptySet()
)

sealed class ConversionError {
    data class ParseError(val message: String, val line: Int? = null) : ConversionError()
    data class ValidationError(val field: String, val message: String) : ConversionError()
    data class UnsupportedFeature(val feature: String, val suggestion: String? = null) : ConversionError()
}
```

### Configuration Models
```kotlin
data class ParserConfig(
    val optimizePaths: Boolean = true,
    val validateColors: Boolean = true,
    val generatePreview: Boolean = true,
    val strictMode: Boolean = false,
    val colorMappings: Map<String, String> = emptyMap()
)

data class ExportSettings(
    val codeStyle: CodeStyle = CodeStyle.Standard,
    val includeComments: Boolean = true,
    val packageName: String? = null,
    val iconClassName: String = "Icons"
)

enum class CodeStyle {
    Standard,
    Material,
    Compact,
    Verbose
}
```

### UI State Models
```kotlin
data class ConverterScreenState(
    val selectedTab: Int = 0,
    val inputText: String = "",
    val conversionResult: ConversionResult? = null,
    val isConverting: Boolean = false,
    val showColorMappingDialog: Boolean = false,
    val showPreview: Boolean = true
)

data class ConfigDialogState(
    val isVisible: Boolean = false,
    val selectedSection: ConfigSection = ConfigSection.Appearance,
    val hasUnsavedChanges: Boolean = false
)

enum class ConfigSection {
    Appearance,
    Conversion,
    Files,
    Advanced
}
```

## 🚀 Usage Examples

### Creating Vector Data
```kotlin
val iconData = IconFileContents(
    name = "MyIcon",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
    nodes = listOf(
        ImageVectorNode.Path(
            pathData = listOf(
                PathNodes.MoveTo(12f, 2f),
                PathNodes.LineTo(22f, 12f),
                PathNodes.LineTo(12f, 22f),
                PathNodes.LineTo(2f, 12f),
                PathNodes.Close
            ),
            fill = SolidColor(Color.Black)
        )
    )
)
```

### Handling Conversion Results
```kotlin
fun processConversionResult(result: ConversionResult) {
    when {
        result.success && result.iconData != null -> {
            // Successful conversion
            displayGeneratedCode(result.generatedCode!!)
            showPreview(result.iconData)
        }
        result.unknownColors.isNotEmpty() -> {
            // Need color mapping
            showColorMappingDialog(result.unknownColors)
        }
        result.errors.isNotEmpty() -> {
            // Handle errors
            result.errors.forEach { error ->
                when (error) {
                    is ConversionError.ParseError -> showParseError(error)
                    is ConversionError.ValidationError -> showValidationError(error)
                    is ConversionError.UnsupportedFeature -> showFeatureError(error)
                }
            }
        }
    }
}
```

### Configuration Management
```kotlin
val defaultConfig = ParserConfig(
    optimizePaths = true,
    validateColors = true,
    generatePreview = true,
    strictMode = false
)

val customConfig = defaultConfig.copy(
    strictMode = true,
    colorMappings = mapOf(
        "primaryColor" to "#FF0000",
        "secondaryColor" to "#00FF00"
    )
)
```

## 🎯 Design Principles

### Immutability
All models are designed as immutable data classes to ensure thread safety and predictable behavior.

### Type Safety
Strong typing prevents runtime errors and provides compile-time validation of data structures.

### Composability
Models can be easily combined and transformed to create complex data structures.

### Validation
Built-in validation support ensures data integrity and provides meaningful error messages.

This module serves as the foundation for type-safe data handling throughout the Svg2Compose application, ensuring consistency, reliability, and maintainability across all components.
