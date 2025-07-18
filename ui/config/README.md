# UI Config Module

## 📋 Module Overview

The `ui:config` module provides the configuration and settings interface for Svg2Compose. It implements user preference management screens, application settings, and configuration dialogs that allow users to customize their experience and adjust application behavior.

## 🔑 Key Features & Components

### Settings Interface
- **Configuration Dialog** - Modal settings interface
- **Preference Categories** - Organized settings sections
- **Real-time Updates** - Immediate application of settings changes
- **Reset Options** - Restore default settings functionality

### Configuration Categories
- **UI Preferences** - Theme, layout, and display settings
- **Conversion Settings** - Default conversion parameters and behavior
- **File Handling** - Default directories and file associations
- **Performance Options** - Memory usage and optimization settings

### User Experience
- **Intuitive Layout** - Well-organized settings categories
- **Validation** - Input validation for configuration values
- **Help Text** - Contextual help and explanations
- **Preview Mode** - Live preview of setting changes

## 🔧 How It Works

### Configuration Architecture
The module provides a structured approach to settings management:

```kotlin
@Composable
fun ConfigDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        ConfigContent()
    }
}
```

### Key Components

#### `ConfigDialog`
Main configuration dialog that hosts all settings:
- Modal presentation with proper backdrop
- Organized tabs or sections for different setting categories
- Apply/Cancel/Reset action buttons

#### Settings Sections
- **Appearance**: Theme selection, font size, layout preferences
- **Behavior**: Default actions, auto-save options, confirmation dialogs
- **Advanced**: Debug options, performance tuning, experimental features

#### Preference Binding
- **Two-way Data Binding**: Settings changes reflect immediately
- **Validation**: Input validation with error feedback
- **Persistence**: Automatic saving of preference changes

## 🏗️ Module-Specific Dependencies

### UI Dependencies
- **Compose Navigation**: Dialog navigation and routing
- **Compose Material**: UI components and form elements
- **Common UI Compose**: Shared components and styling

### Core Dependencies
- `core.preferences` - Preference storage and management
- `core.base` - Base utilities and dependency injection

### Integration Dependencies
- `common.ui.resources` - Icons, strings, and visual resources

## 🔄 Integration Points

### With Preferences Module
- **Settings Storage**: Reads and writes user preferences
- **Default Values**: Uses preference defaults and validation
- **Change Notifications**: Observes preference changes

### With Application Module
- **Theme Integration**: Applies theme changes application-wide
- **Window Settings**: Manages window size and position preferences
- **Startup Configuration**: Applies settings on application launch

### With Converter Module
- **Default Settings**: Provides conversion default preferences
- **Behavior Configuration**: Configures conversion behavior options
- **Output Preferences**: Manages code generation preferences

## 🎨 Configuration Categories

### Appearance Settings
- **Theme Selection**: Light, Dark, or System theme
- **Font Size**: Adjustable text size for accessibility
- **Layout Density**: Compact or comfortable layout options
- **Color Scheme**: Custom color preferences

### Conversion Settings
- **Default Format**: Preferred output format and style
- **Auto-conversion**: Automatic conversion on input change
- **Color Handling**: Default color mapping behavior
- **Code Style**: Generated code formatting preferences

### File Management
- **Default Directories**: Input and output folder preferences
- **Recent Files**: Number of recent files to remember
- **Auto-save**: Automatic saving of conversion results
- **File Associations**: Default file type handling

### Advanced Options
- **Performance**: Memory usage and processing options
- **Debugging**: Logging level and debug information
- **Experimental**: Beta features and experimental options
- **Reset**: Factory reset and preference clearing

## 🚀 Usage Examples

### Basic Configuration Dialog
```kotlin
@Composable
fun SettingsExample() {
    var showConfig by remember { mutableStateOf(false) }
    
    Button(onClick = { showConfig = true }) {
        Text("Settings")
    }
    
    if (showConfig) {
        ConfigDialog(
            onDismiss = { showConfig = false }
        )
    }
}
```

### Preference Binding
```kotlin
@Composable
fun ThemeSelector(
    currentTheme: String,
    onThemeChange: (String) -> Unit
) {
    Column {
        Text("Theme Selection")
        
        RadioButton(
            selected = currentTheme == "light",
            onClick = { onThemeChange("light") }
        )
        Text("Light Theme")
        
        RadioButton(
            selected = currentTheme == "dark",
            onClick = { onThemeChange("dark") }
        )
        Text("Dark Theme")
        
        RadioButton(
            selected = currentTheme == "system",
            onClick = { onThemeChange("system") }
        )
        Text("System Theme")
    }
}
```

### Settings Validation
```kotlin
@Composable
fun NumberSetting(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange
) {
    var textValue by remember { mutableStateOf(value.toString()) }
    var isValid by remember { mutableStateOf(true) }
    
    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            textValue = newValue
            val intValue = newValue.toIntOrNull()
            isValid = intValue != null && intValue in range
            if (isValid && intValue != null) {
                onValueChange(intValue)
            }
        },
        label = { Text(label) },
        isError = !isValid,
        supportingText = if (!isValid) {
            { Text("Value must be between ${range.first} and ${range.last}") }
        } else null
    )
}
```

## 🎯 Design Principles

### User-Friendly Interface
- **Clear Organization**: Logical grouping of related settings
- **Intuitive Controls**: Familiar UI patterns and controls
- **Immediate Feedback**: Real-time validation and preview
- **Help Integration**: Contextual help and explanations

### Accessibility
- **Keyboard Navigation**: Full keyboard accessibility
- **Screen Reader Support**: Proper semantic markup
- **High Contrast**: Support for accessibility themes
- **Focus Management**: Clear focus indicators and navigation

### Performance
- **Lazy Loading**: Settings loaded as needed
- **Efficient Updates**: Minimal recomposition on changes
- **Background Saving**: Non-blocking preference persistence
- **Memory Efficiency**: Proper cleanup and resource management

This module provides a comprehensive and user-friendly interface for customizing the Svg2Compose application experience, ensuring users can tailor the application to their specific needs and preferences.
