# App Module

## 📋 Module Overview

The `app` module serves as the main application entry point for Svg2Compose. It orchestrates the entire desktop application, manages the application lifecycle, handles dependency injection setup, and provides the main UI shell that hosts all feature modules.

## 🔑 Key Features & Components

### Application Entry Point
- **`main.kt`** - JVM main function that initializes the application
- **`App.kt`** - Main Compose application component with window management
- **`AppState.kt`** - Central application state management
- **`AppNavigation.kt`** - Navigation controller for screen routing

### Dependency Injection
- **`AppComponent.kt`** - Root dependency injection component using Kotlin Inject
- **`AppInitializers.kt`** - Application initialization logic coordinator

### Core Application Features
- **Desktop window management** with proper sizing and icons
- **System tray integration** for background operation
- **Application menu** with exit functionality
- **Cross-platform distribution** support (Windows, macOS, Linux)

## 🔧 How It Works

### Application Startup Flow
1. **Main Entry**: `main()` function sets up Skiko rendering and creates the injection graph
2. **Dependency Injection**: `AppComponent` provides all necessary dependencies
3. **Initialization**: `AppInitializers` runs startup tasks
4. **UI Launch**: Compose application starts with proper theming and navigation
5. **Window Setup**: Main window is configured with appropriate size, title, and icon

### Architecture Components
- **MVVM Pattern**: ViewModels are injected and managed through the DI system
- **Navigation**: Uses Navigation Compose for screen transitions
- **State Management**: Centralized app state with proper lifecycle management
- **Resource Management**: Shared resources and theming through CompositionLocal

### Key Classes

#### `AppComponent`
```kotlin
@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface AppComponent {
    val vmFactory: ViewModelProvider.Factory
    val initializers: AppInitializers
    val dispatchers: AppCoroutineDispatchers
    val appScope: ApplicationCoroutineScope
}
```

#### `AppState`
```kotlin
@Stable
class AppState(private val exitApp: () -> Unit) {
    val tray = TrayState()
    fun exit() = exitApp()
    fun sendNotification(notification: Notification)
}
```

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Multiplatform**: Cross-platform code sharing
- **Compose Desktop**: Desktop UI framework
- **Kotlin Inject**: Dependency injection with compile-time safety
- **Navigation Compose**: Screen navigation management
- **Coroutines**: Asynchronous operations

### Internal Module Dependencies
- `projects.core.base` - Foundation utilities and interfaces
- `projects.core.preferences` - User preferences management
- `projects.core.logging` - Logging infrastructure
- `projects.common.ui.compose` - Shared UI components
- `projects.data` - Data layer access
- `projects.ui.converter` - Main conversion functionality
- `projects.ui.config` - Configuration screens

### External Libraries
- `compose.desktop.currentOs` - Platform-specific desktop runtime
- `compose.material` - Material Design components
- `compose.materialIconsExtended` - Extended icon set
- `androidx.navigation.compose` - Navigation framework
- `androidx.lifecycle.viewmodel.compose` - ViewModel integration
- `kotlinx.coroutines.core` - Core coroutine support
- `kotlinx.coroutines.swing` - Swing integration for JVM

## 🚀 Build Configuration

The module is configured to build native distributions for multiple platforms:

- **Windows**: MSI installer with custom icon and upgrade UUID
- **macOS**: DMG package with proper icon
- **Linux**: DEB and RPM packages

### Distribution Features
- Custom application icons for each platform
- Proper package metadata and versioning
- Menu integration on Windows
- Per-user installation support

## 🔄 Integration Points

### With UI Modules
- Hosts the converter screen for main functionality
- Integrates configuration dialogs
- Manages navigation between different features

### With Core Modules
- Uses base utilities for common operations
- Integrates logging for debugging and monitoring
- Accesses user preferences for application settings

### With Data Layer
- Connects to parsing engines through the data module
- Manages data flow between UI and business logic

This module serves as the foundation that brings together all other modules into a cohesive desktop application experience.
