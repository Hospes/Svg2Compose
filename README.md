# Svg2Compose

## 📋 Project Overview

Svg2Compose is a desktop application that transforms Vector Drawable files and SVG paths into Jetpack Compose ImageVector code. This tool bridges the gap between traditional Android vector graphics and modern Compose UI development, enabling developers to easily convert their existing vector assets for use in Compose applications.

**Key Features:**
- Convert Android Vector Drawable XML files to Compose ImageVector code
- Transform SVG paths directly to ImageVector format
- Real-time preview with three-column layout (paths, code, rendered image)
- Color mapping for unknown color values
- Cross-platform desktop application (Windows, macOS, Linux)
- Copy-to-clipboard functionality for generated code

## 🛠️ Technology Stack

| Technology | Purpose |
|------------|----------|
| **Kotlin Multiplatform** | Core language and cross-platform development |
| **Jetpack Compose Desktop** | UI framework for desktop application |
| **Gradle** | Build system and dependency management |
| **Kotlin Inject** | Dependency injection framework |
| **Coroutines** | Asynchronous programming |
| **Navigation Compose** | In-app navigation |
| **Material Design** | UI components and theming |

## 📦 Key Libraries & Dependencies

| Library | Version | Purpose |
|---------|---------|----------|
| `compose.desktop.currentOs` | 1.9.0-alpha03 | Desktop Compose runtime |
| `compose.material` | 1.9.0-alpha03 | Material Design components |
| `compose.materialIconsExtended` | 1.9.0-alpha03 | Extended Material icons |
| `androidx.navigation.compose` | 2.9.0-beta03 | Navigation between screens |
| `androidx.lifecycle.viewmodel.compose` | 2.9.1 | ViewModel integration |
| `kotlinx.coroutines.core` | 1.10.2 | Coroutine support |
| `kotlinx.coroutines.swing` | 1.10.2 | Swing integration for JVM |
| `metro` (Kotlin Inject) | 0.5.1 | Dependency injection framework |
| `kotlin` | 2.2.0 | Kotlin language and compiler |

## 📁 Project Structure

```
Svg2Compose/
├── app/                          # Main application module
│   ├── src/
│   │   ├── commonMain/          # Shared application code
│   │   └── jvmMain/             # JVM-specific code (main entry point)
│   └── build.gradle.kts         # App module build configuration
├── core/                         # Core functionality modules
│   ├── base/                    # Base utilities and common code
│   ├── logging/                 # Logging infrastructure
│   └── preferences/             # User preferences management
├── common/                       # Shared UI components
│   └── ui/
│       ├── compose/             # Reusable Compose components
│       └── resources/           # Shared resources
├── data/                         # Data layer (parsing, models)
├── models/                       # Data models and entities
├── ui/                          # Feature-specific UI modules
│   ├── converter/               # Main conversion screen
│   └── config/                  # Configuration/settings screen
├── gradle/                       # Gradle build logic
│   └── build-logic/             # Custom Gradle plugins
├── build.gradle.kts             # Root build configuration
├── settings.gradle.kts          # Project structure definition
└── gradle.properties            # Gradle properties and settings
```

## 📖 Module Documentation

### Core Modules
- [**Core Base**](core/base/README.md) - Foundation utilities, dependency injection setup, and common interfaces
- [**Core Logging**](core/logging/README.md) - Centralized logging infrastructure
- [**Core Preferences**](core/preferences/README.md) - User preferences and settings management

### UI Modules
- [**Common UI Compose**](common/ui/compose/README.md) - Reusable Compose components and utilities
- [**Common UI DI**](common/ui/di/README.md) - Dependency injection infrastructure for UI components and ViewModels
- [**Common UI Resources**](common/ui/resources/README.md) - Shared UI resources (icons, strings, themes)
- [**UI Converter**](ui/converter/README.md) - Main conversion interface and functionality
- [**UI Config**](ui/config/README.md) - Application configuration and settings UI

### Data & Models
- [**Data**](data/README.md) - Data parsing, processing, and repository layer
- [**Models**](models/README.md) - Core data models and entities

### Application
- [**App**](app/README.md) - Main application module, entry point, and application-level configuration

## 🚀 Getting Started

### Download
Download the latest version (Windows, macOS, or Linux) here: [Releases](https://github.com/DenisMondon/Svg2Compose/releases).

### Development Setup
1. Clone the repository
2. Open in IntelliJ IDEA or Android Studio
3. Run the application: `./gradlew :app:run`
4. Build distributions: `./gradlew :app:packageDistributionForCurrentOS`

## 📱 How to Use

### Transform a Vector Drawable
1. Switch to **VECTOR** mode using the toggle buttons at the top
2. Enter your Vector Drawable XML content in the left input panel, or use the **Browse** button to load a file
3. The conversion happens automatically as you type
4. View the two-column output:
   - **Left**: Your input XML/SVG content
   - **Right**: Generated ImageVector code with a preview icon overlay in the top-right corner
5. Edit the icon name in the "Icon Name" field if desired
6. Copy the generated ImageVector code from the right panel
7. Paste it into your Compose project

### Transform SVG Content
1. Switch to **SVG** mode using the toggle buttons at the top (default mode)
2. Enter your SVG path data or SVG content in the left input panel, or use the **Browse** button to load a file
3. The conversion happens automatically in real-time as you type
4. View the results in the right panel with the generated ImageVector code
5. The preview icon appears as an overlay in the top-right corner of the output panel
6. Copy the generated code to your clipboard

## 🏗️ Architecture

The application follows a modular architecture with clear separation of concerns:

- **Presentation Layer**: Compose UI with MVVM pattern
- **Domain Layer**: Business logic and use cases
- **Data Layer**: Parsing engines and data models
- **Infrastructure**: Dependency injection, logging, and preferences

The project uses Kotlin Multiplatform to share code between different platforms while maintaining platform-specific optimizations for desktop environments.

## 🤝 Contributing

Contributions are welcome! Please read the contributing guidelines and ensure all tests pass before submitting a pull request.

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
