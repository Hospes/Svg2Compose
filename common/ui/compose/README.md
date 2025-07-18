# Common UI Compose Module

## 📋 Module Overview

The `common:ui:compose` module provides reusable Compose UI components, utilities, and design system elements that are shared across all UI modules in the Svg2Compose application. It establishes consistent visual patterns, theming, and component behavior throughout the application.

## 🔑 Key Features & Components

### Design System
- **Theme Management** - Centralized theme configuration and switching
- **Color Palette** - Consistent color scheme across the application
- **Typography** - Standardized text styles and font management
- **Spacing System** - Consistent spacing and layout patterns

### Reusable Components
- **Custom Buttons** - Styled buttons with consistent behavior
- **Input Fields** - Text fields with validation and styling
- **Dialog Components** - Modal dialogs and popup windows
- **Layout Components** - Common layout patterns and containers

### UI Utilities
- **Compose Extensions** - Helper functions for common Compose operations
- **State Management** - Shared state handling patterns
- **Animation Utilities** - Consistent animations and transitions
- **Accessibility Support** - Accessibility helpers and patterns

## 🔧 How It Works

### Theme Architecture
The module implements a comprehensive theming system:

```kotlin
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    
    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
```

### Component Library
- **Atomic Components**: Basic building blocks (buttons, inputs, icons)
- **Molecular Components**: Combinations of atomic components
- **Organism Components**: Complex UI sections and layouts
- **Template Components**: Page-level layout templates

### Design Tokens
- **Colors**: Primary, secondary, surface, and semantic colors
- **Typography**: Heading, body, caption, and code text styles
- **Spacing**: Consistent spacing scale (4dp, 8dp, 16dp, etc.)
- **Elevation**: Shadow and elevation patterns

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Compose Runtime**: Core Compose functionality
- **Compose Foundation**: Basic Compose components
- **Compose Material**: Material Design components
- **Compose Animation**: Animation and transition support

### Integration Dependencies
- `common.ui.resources` - Shared resources (icons, strings, assets)
- `core.base` - Base utilities and patterns

### External Libraries
- `compose.material` - Material Design component library
- `compose.materialIconsExtended` - Extended Material icon set
- `compose.animation` - Animation framework

## 🔄 Integration Points

### With UI Feature Modules
- **Converter UI**: Provides conversion-specific components
- **Config UI**: Supplies settings and configuration components
- **App Module**: Supplies main application shell components

### With Resource Module
- Accesses shared icons, strings, and visual assets
- Integrates localized content and resources

### With Core Modules
- Uses base utilities for common operations
- Integrates with preferences for theme settings

## 🎨 Component Categories

### Input Components
- **TextFieldWithValidation**: Text input with built-in validation
- **FilePickerButton**: File selection with drag-and-drop support
- **ColorPicker**: Color selection and mapping interface
- **ToggleSwitch**: Styled toggle switches and checkboxes

### Display Components
- **CodeBlock**: Syntax-highlighted code display
- **PreviewPane**: Image and vector preview component
- **StatusIndicator**: Loading, success, and error states
- **InfoCard**: Information display with consistent styling

### Layout Components
- **SplitPane**: Resizable split layouts
- **TabContainer**: Tabbed interface components
- **ModalDialog**: Consistent modal dialog patterns
- **ToolbarLayout**: Application toolbar and menu components

### Navigation Components
- **NavigationRail**: Side navigation for desktop
- **BreadcrumbBar**: Navigation breadcrumb display
- **ActionButton**: Floating and fixed action buttons

## 🚀 Usage Examples

### Using Theme Components
```kotlin
@Composable
fun MyScreen() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.medium)
        ) {
            AppButton(
                text = "Convert",
                onClick = { /* action */ }
            )
        }
    }
}
```

### Custom Component Creation
```kotlin
@Composable
fun CustomCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = AppElevation.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(AppSpacing.medium)
        ) {
            Text(
                text = title,
                style = AppTypography.h6
            )
            Spacer(modifier = Modifier.height(AppSpacing.small))
            content()
        }
    }
}
```

### Responsive Design
```kotlin
@Composable
fun ResponsiveLayout(content: @Composable () -> Unit) {
    BoxWithConstraints {
        if (maxWidth > 800.dp) {
            // Desktop layout
            Row { content() }
        } else {
            // Compact layout
            Column { content() }
        }
    }
}
```

## 🎯 Design Principles

### Consistency
All components follow the same design patterns, spacing, and visual hierarchy.

### Accessibility
Components include proper semantics, keyboard navigation, and screen reader support.

### Performance
Components are optimized for recomposition and memory efficiency.

### Customization
Components accept styling parameters while maintaining design system consistency.

This module serves as the visual foundation that ensures a cohesive, professional, and user-friendly interface throughout the Svg2Compose application.
