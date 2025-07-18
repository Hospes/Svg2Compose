# Data Module

## 📋 Module Overview

The `data` module serves as the core data processing layer for Svg2Compose. It contains the parsing engines, data transformation logic, and repository implementations that handle the conversion of Vector Drawable files and SVG paths into structured data models that can be transformed into Jetpack Compose ImageVector code.

## 🔑 Key Features & Components

### Parsing Engines
- **Vector Drawable Parser** - Processes Android XML vector drawable files
- **SVG Path Parser** - Handles SVG path data and commands
- **XML Processing** - Robust XML parsing and validation
- **Path Data Extraction** - Extracts and processes vector path information

### Data Transformation
- **Model Conversion** - Transforms parsed data into internal models
- **Path Optimization** - Optimizes vector paths for Compose usage
- **Color Processing** - Handles color extraction and mapping
- **Coordinate Transformation** - Converts between coordinate systems

### Code Generation
- **ImageVector Builder** - Generates Compose ImageVector code
- **Code Formatting** - Produces clean, readable Kotlin code
- **Template Engine** - Customizable code generation templates
- **Syntax Validation** - Ensures generated code is syntactically correct

### Error Handling
- **Parsing Validation** - Comprehensive input validation
- **Error Recovery** - Graceful handling of malformed input
- **Diagnostic Information** - Detailed error reporting and suggestions
- **Fallback Mechanisms** - Alternative processing strategies

## 🔧 How It Works

### Parsing Architecture
The module implements a multi-stage parsing pipeline:

1. **Input Validation**: Validates input format and structure
2. **Lexical Analysis**: Tokenizes input content
3. **Syntax Parsing**: Builds abstract syntax tree
4. **Semantic Analysis**: Validates semantic correctness
5. **Model Generation**: Creates internal data models
6. **Code Generation**: Produces final ImageVector code

### Key Components

#### `IconParser`
Central parsing coordinator that manages different parser implementations:
```kotlin
object IconParser {
    object SvgParser {
        fun parse(content: String, iconName: String, config: ParserConfig): Result<IconFileContents>
    }
    
    object VectorDrawableParser {
        fun parse(content: String, iconName: String, config: ParserConfig): Result<IconFileContents>
    }
}
```

#### Data Models
- **`IconFileContents`**: Represents parsed icon data
- **`PathNodes`**: Vector path command sequences
- **`ImageVectorNode`**: Compose-specific vector representation
- **`ParserConfig`**: Configuration for parsing behavior

#### Code Builders
- **`IconSourceBuilder`**: Generates basic ImageVector code
- **`MaterialIconSourceBuilder`**: Generates Material Design icon code
- **Template-based generation**: Customizable output formats

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Standard Library**: Core functionality and collections
- **Kotlin Coroutines**: Asynchronous processing support
- **XML Processing**: DOM and SAX parsing libraries

### Integration Dependencies
- `models` - Core data models and entities
- `core.base` - Base utilities and common patterns

### External Libraries
- **XML Parser**: Java XML processing APIs
- **Regular Expressions**: Pattern matching for path parsing
- **String Processing**: Text manipulation and formatting

## 🔄 Integration Points

### With UI Modules
- **Converter UI**: Provides parsing services for conversion interface
- **Error Display**: Supplies detailed error information for UI display
- **Progress Reporting**: Reports parsing progress for UI feedback

### With Models Module
- **Data Models**: Uses shared data models for consistency
- **Type Definitions**: Leverages common type definitions
- **Validation Rules**: Shares validation logic and constraints

### With Core Modules
- **Logging**: Reports parsing operations and errors
- **Preferences**: Uses parsing configuration preferences
- **Base Utilities**: Leverages common utility functions

## 🎯 Parsing Capabilities

### Vector Drawable Support
- **XML Structure**: Full Android Vector Drawable XML parsing
- **Path Commands**: Support for all SVG path commands (M, L, C, Q, Z, etc.)
- **Styling**: Color, stroke, fill, and transformation attributes
- **Groups**: Nested group elements with transformations
- **Clipping**: Clip path support and processing

### SVG Path Support
- **Path Data**: Direct SVG path string processing
- **Command Parsing**: All standard SVG path commands
- **Coordinate Systems**: Absolute and relative coordinates
- **Curve Support**: Bezier curves, arcs, and complex paths
- **Path Optimization**: Redundant command removal and simplification

### Color Processing
- **Color Formats**: Hex, RGB, ARGB, and named colors
- **Color Mapping**: Unknown color detection and mapping
- **Transparency**: Alpha channel support and processing
- **Theme Colors**: Android theme color reference resolution

## 🚀 Usage Examples

### Basic Vector Drawable Parsing
```kotlin
val parserConfig = ParserConfig(
    optimizePaths = true,
    validateColors = true
)

val result = IconParser.VectorDrawableParser.parse(
    content = vectorDrawableXml,
    iconName = "MyIcon",
    config = parserConfig
)

when (result) {
    is Result.Success -> {
        val iconData = result.getOrNull()
        val imageVectorCode = IconSourceBuilder.build(iconData)
    }
    is Result.Failure -> {
        val error = result.exceptionOrNull()
        // Handle parsing error
    }
}
```

### SVG Path Processing
```kotlin
val svgPath = "M10,10 L20,20 C30,30 40,40 50,50 Z"

val result = IconParser.SvgParser.parse(
    content = svgPath,
    iconName = "PathIcon",
    config = ParserConfig()
)

result.fold(
    onSuccess = { iconData ->
        val code = MaterialIconSourceBuilder.build(iconData)
        println(code)
    },
    onFailure = { error ->
        println("Parsing failed: ${error.message}")
    }
)
```

### Custom Code Generation
```kotlin
class CustomIconBuilder : IconSourceBuilder {
    override fun build(iconData: IconFileContents): String {
        return buildString {
            appendLine("val ${iconData.name} = ImageVector.Builder(")
            appendLine("    name = \"${iconData.name}\",")
            appendLine("    defaultWidth = ${iconData.defaultWidth}.dp,")
            appendLine("    defaultHeight = ${iconData.defaultHeight}.dp,")
            appendLine("    viewportWidth = ${iconData.viewportWidth}f,")
            appendLine("    viewportHeight = ${iconData.viewportHeight}f")
            appendLine(").apply {")
            
            iconData.nodes.forEach { node ->
                appendLine("    ${generatePathCode(node)}")
            }
            
            appendLine("}.build()")
        }
    }
}
```

## 🔍 Error Handling

### Validation Errors
- **Malformed XML**: Invalid XML structure or syntax
- **Missing Attributes**: Required attributes not present
- **Invalid Values**: Attribute values outside valid ranges
- **Unsupported Features**: Features not yet implemented

### Recovery Strategies
- **Partial Parsing**: Extract valid portions from invalid input
- **Default Values**: Use sensible defaults for missing data
- **Error Reporting**: Provide detailed diagnostic information
- **Suggestion Engine**: Suggest corrections for common errors

This module serves as the core engine that transforms various vector formats into structured data ready for Compose ImageVector generation, ensuring robust parsing, comprehensive error handling, and flexible code generation capabilities.
