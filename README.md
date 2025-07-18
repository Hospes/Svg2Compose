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
| `compose.desktop.currentOs` | Latest | Desktop Compose runtime |
| `compose.material` | Latest | Material Design components |
| `compose.materialIconsExtended` | Latest | Extended Material icons |
| `androidx.navigation.compose` | Latest | Navigation between screens |
| `androidx.lifecycle.viewmodel.compose` | Latest | ViewModel integration |
| `kotlinx.coroutines.core` | Latest | Coroutine support |
| `kotlinx.coroutines.swing` | Latest | Swing integration for JVM |
| `kotlininject.viewmodel.runtime` | Latest | ViewModel dependency injection |
| `kotlininject.compiler` | Latest | Compile-time dependency injection |

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
1. Select the **Vector Drawable file** tab
2. Enter the content of your Vector Drawable XML
3. Click **CONVERT**
4. If unknown colors are detected, map them to hex values (e.g., #FFFFFF)
5. View the three-column output:
   - **Left**: All extracted paths
   - **Middle**: Generated ImageVector code
   - **Right**: Rendered preview
6. Copy the ImageVector code to your clipboard
7. Paste it into your Compose project

### Transform a SVG Path
1. Select the **SVG path** tab
2. Enter your SVG path data
3. Click **CONVERT**
4. View the generated ImageVector code and preview
5. Copy the code to your clipboard

### Transform a SVG File
*Coming soon - Full SVG file support*

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
