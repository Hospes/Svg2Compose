# Svg2Compose

## 📋 Project Overview

**Svg2Compose** is a powerful cross-platform tool (Desktop & Android) that transforms **Vector Drawable** XML files and **SVG** paths into **Jetpack Compose ImageVector** code.

This tool bridges the gap between traditional vector graphics and modern Compose UI development, enabling developers to effortlessly convert their existing assets for use in Kotlin Multiplatform and Android Compose applications.

**Key Features:**
- **Dual Mode Support:** Convert both Android Vector Drawables and standard SVG paths.
- **Real-time Conversion:** Instant code generation as you type or load files.
- **Responsive UI:** Adaptive layout that switches between a two-panel landscape view and a vertical portrait view based on screen size.
- **Integrated Preview:** Visual preview of the generated icon directly within the interface.
- **File Support:** Load `.xml` or `.svg` files directly via the built-in file picker.
- **Cross-Platform:** Available as a native Desktop application (Windows, macOS, Linux) and an Android app.
- **Customization:** Configurable icon naming and generation options.

## 🛠️ Technology Stack

This project allows you to explore the bleeding edge of the Kotlin ecosystem, utilizing the latest pre-release versions of key libraries:

| Technology | Version | Purpose |
|------------|---------|----------|
| **Kotlin** | 2.3.20-Beta2 | Core language and compiler |
| **Compose Multiplatform** | 1.10.0 | UI framework for Desktop & Android |
| **Material 3** | 1.10.0-alpha05 | Modern UI components and theming |
| **Navigation 3** | 1.0.0-alpha06 | Type-safe navigation for Compose |
| **Metro** | 0.10.2 | Compile-time dependency injection |
| **Coroutines** | 1.10.2 | Asynchronous programming |
| **FileKit** | 0.12.0 | Cross-platform file picking |
| **KSoup** | 0.2.5 | HTML/XML parsing |

## 📁 Project Structure

```text
Svg2Compose/
├── android-app/                  # Android application entry point
├── desktop-app/                  # Desktop (JVM) application entry point
├── app/                          # Shared Application logic (Compose UI entry)
├── common/                       # Shared UI and Logic
│   └── ui/
│       ├── compose/             # Reusable Design System components
│       ├── navigation/          # Navigation logic
│       ├── resources/           # Shared resources (strings, icons)
│       └── metrox/              # DI extensions
├── core/                         # Core infrastructure
│   ├── base/                    # Base utilities & Common Interfaces
│   ├── logging/                 # Logging (Log4k)
│   └── preferences/             # Settings management
├── data/                         # Data layer (Parsing logic, Repositories)
├── models/                       # Domain Models
├── ui/                           # Feature Modules
│   ├── converter/               # Main Conversion Screen
│   └── config/                  # Settings/Configuration Screen
└── gradle/                       # Build logic and version catalog
```

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Android Studio (for Android deployment)

### Running the Application

**Desktop (Windows/macOS/Linux):**
```bash
./gradlew :desktop-app:run
```

**Android:**
```bash
./gradlew :android-app:installDebug
```

## 📱 How to Use

The application features a responsive interface divided into an **Input Panel** and an **Output Panel**.

### 1. Provide Input
*   **Select Mode:** Use the toggle to switch between **SVG** (default) and **VECTOR** modes.
*   **Load File:** Click **"Load from file..."** to open a system file picker and select your `.svg` or `.xml` file.
*   **Paste Code:** Alternatively, paste your raw SVG path data or Vector Drawable XML directly into the input text area.

### 2. Get Result
*   **View Code:** The generated Kotlin code appears immediately in the Output Panel.
*   **Preview:** Toggle the **PREVIEW** switch to overlay a visual rendering of the icon on top of the code.
*   **Name Icon:** Enter a custom name in the "Icon Name" field to update the generated variable/object name.
*   **Copy:** Click the **Copy** button to copy the entire code block to your clipboard.
*   **Config:** Click the **Config** button (Settings icon) to tweak generation settings.

## 🏗️ Architecture

The project follows a clean, modular architecture:

*   **UI Layer:** Built with **Jetpack Compose** and **Material 3**. Uses **MVVM** pattern with generic ViewModels.
*   **Navigation:** Uses **Navigation 3** (Type-safe, component-based navigation).
*   **DI:** Uses **Metro** for compile-time dependency injection, ensuring type safety and performance.
*   **Multiplatform:** Code is shared via the `app` and `common` modules, while `android-app` and `desktop-app` serve as platform-specific launchers.

## 🤝 Contributing

Contributions are welcome!
1.  Fork the repository.
2.  Create a feature branch (`git checkout -b feature/amazing-feature`).
3.  Commit your changes.
4.  Push to the branch.
5.  Open a Pull Request.

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.