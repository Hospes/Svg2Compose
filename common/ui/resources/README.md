# Common UI Resources Module

## 📋 Module Overview

The `common:ui:resources` module centralizes all shared UI resources for the Svg2Compose application, including icons, images, strings, colors, and other visual assets. It provides a unified resource management system that ensures consistency and efficient resource loading across all UI modules.

## 🔑 Key Features & Components

### Resource Management
- **Icon Library** - Centralized icon collection and management
- **Image Assets** - Application images, logos, and graphics
- **String Resources** - Localized text and messages
- **Color Definitions** - Semantic color definitions and palettes

### Asset Organization
- **Categorized Resources** - Organized by type and usage context
- **Platform-Specific Assets** - Platform-optimized resource variants
- **Scalable Assets** - Vector graphics and scalable resources
- **Accessibility Resources** - High contrast and accessibility variants

### Resource Access
- **Type-Safe Access** - Compile-time resource validation
- **Lazy Loading** - Efficient resource loading patterns
- **Caching System** - Resource caching for performance
- **Resource Providers** - Abstracted resource access patterns

## 🔧 How It Works

### Resource Architecture
The module provides centralized access to all UI resources:

```kotlin
object AppResources {
    object Icons {
        val convert = Icons.Default.Transform
        val settings = Icons.Default.Settings
        val export = Icons.Default.FileDownload
    }
    
    object Images {
        val appIcon = "icon.png"
        val placeholder = "placeholder.svg"
    }
    
    object Strings {
        val appName = "Svg2Compose"
        val convertButton = "Convert"
        val exportButton = "Export"
    }
}
```

### Resource Categories
- **System Icons**: Navigation, actions, and system operations
- **Content Icons**: File types, conversion states, and content indicators
- **Brand Assets**: Application logo, branding elements
- **UI Graphics**: Backgrounds, patterns, and decorative elements

### Localization Support
- **Multi-language Support**: String resources in multiple languages
- **Cultural Adaptation**: Region-specific icons and graphics
- **RTL Support**: Right-to-left layout resource variants

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Compose Resources**: Resource loading and management
- **Kotlin Multiplatform**: Cross-platform resource access

### Integration Dependencies
- `core.base` - Base utilities for resource management

### Resource Dependencies
- **Material Icons**: Extended Material Design icon set
- **Platform Assets**: Platform-specific resource formats

## 🔄 Integration Points

### With UI Modules
- **Common Compose**: Provides icons and assets for shared components
- **Converter UI**: Supplies conversion-specific icons and graphics
- **Config UI**: Provides settings and configuration resources

### With Application Module
- Supplies application icon and branding assets
- Provides system tray and window icons

### With Platform Integration
- Platform-specific icon formats (ICO, ICNS, PNG)
- Native resource integration for each platform

## 🎨 Resource Categories

### Icons
- **Action Icons**: Convert, export, import, save, load
- **Navigation Icons**: Back, forward, home, settings
- **Status Icons**: Success, error, warning, loading
- **File Type Icons**: SVG, XML, Kotlin, image formats

### Images
- **Application Icons**: Various sizes and formats for different platforms
- **Placeholder Graphics**: Default images and loading states
- **Brand Elements**: Logo variations and brand graphics
- **UI Illustrations**: Onboarding and help illustrations

### Colors
- **Semantic Colors**: Primary, secondary, error, success colors
- **Theme Colors**: Light and dark theme color definitions
- **Accent Colors**: Highlight and emphasis colors
- **System Colors**: Platform-specific system color integration

### Typography Resources
- **Font Definitions**: Custom font loading and definitions
- **Text Styles**: Predefined text style configurations
- **Code Fonts**: Monospace fonts for code display

## 🚀 Usage Examples

### Accessing Icons
```kotlin
@Composable
fun ConvertButton() {
    IconButton(
        onClick = { /* convert action */ }
    ) {
        Icon(
            imageVector = AppResources.Icons.convert,
            contentDescription = AppResources.Strings.convertButton
        )
    }
}
```

### Using Images
```kotlin
@Composable
fun AppLogo() {
    Image(
        painter = painterResource(AppResources.Images.appIcon),
        contentDescription = AppResources.Strings.appName,
        modifier = Modifier.size(64.dp)
    )
}
```

### String Resources
```kotlin
@Composable
fun WelcomeMessage() {
    Text(
        text = AppResources.Strings.welcomeMessage,
        style = MaterialTheme.typography.h4
    )
}
```

### Color Usage
```kotlin
@Composable
fun StatusIndicator(isSuccess: Boolean) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(
                color = if (isSuccess) 
                    AppResources.Colors.success 
                else 
                    AppResources.Colors.error,
                shape = CircleShape
            )
    )
}
```

## 🌐 Localization

### Supported Languages
- **English (Default)**: Primary language support
- **Extensible Framework**: Ready for additional language support
- **Pluralization**: Proper plural form handling
- **Context-Aware**: Context-sensitive translations

### Resource Organization
```
resources/
├── strings/
│   ├── strings.xml (English)
│   ├── strings_es.xml (Spanish)
│   └── strings_fr.xml (French)
├── icons/
│   ├── common/
│   ├── platform/
│   └── themed/
└── images/
    ├── app_icons/
    ├── ui_graphics/
    └── illustrations/
```

## 🔧 Resource Optimization

### Performance
- **Lazy Loading**: Resources loaded only when needed
- **Caching**: Intelligent resource caching strategies
- **Compression**: Optimized resource formats and compression
- **Bundling**: Efficient resource bundling for distribution

### Platform Optimization
- **Vector Graphics**: Scalable SVG and vector resources
- **Density-Specific**: Platform-appropriate resource densities
- **Format Selection**: Optimal formats for each platform
- **Size Variants**: Multiple sizes for different use cases

This module ensures efficient, consistent, and well-organized resource management throughout the Svg2Compose application, supporting internationalization, accessibility, and platform-specific optimizations.
