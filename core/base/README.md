# Core Base Module

## 📋 Module Overview

The `core:base` module provides the foundational infrastructure for the entire Svg2Compose application. It contains essential utilities, base classes, interfaces, and dependency injection setup that are shared across all other modules. This module establishes the architectural patterns and common functionality that other modules depend on.

## 🔑 Key Features & Components

### Dependency Injection Infrastructure
- **Application Scope Management** - Defines scoping for singleton dependencies
- **Coroutine Dispatchers** - Centralized coroutine dispatcher configuration
- **Application Initializers** - Framework for startup initialization tasks
- **Component Integration** - Base interfaces for dependency injection components

### Utility Classes
- **Base Extensions** - Common Kotlin extension functions
- **Error Handling** - Standardized error handling patterns
- **Resource Management** - Common resource access patterns
- **Threading Utilities** - Thread-safe operations and utilities

### Application Lifecycle
- **App Initializer Interface** - Contract for application startup tasks
- **Coroutine Scope Management** - Application-wide coroutine scope handling
- **Lifecycle Observers** - Base classes for lifecycle-aware components

## 🔧 How It Works

### Dependency Injection Architecture
The module uses Kotlin Inject with Anvil for compile-time dependency injection:

```kotlin
// Application scope definition
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

// Coroutine dispatchers configuration
data class AppCoroutineDispatchers(
    val io: CoroutineDispatcher,
    val databaseWrite: CoroutineDispatcher,
    val databaseRead: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher,
)
```

### Initialization Framework
- **Sequential Initialization**: Ensures proper startup order
- **Lazy Loading**: Dependencies are created only when needed
- **Error Recovery**: Graceful handling of initialization failures

### Core Patterns
- **Repository Pattern**: Base interfaces for data access
- **Use Case Pattern**: Base classes for business logic encapsulation
- **Observer Pattern**: Event handling and state observation utilities

## 🏗️ Module-Specific Dependencies

### External Dependencies
- **Kotlin Inject**: Compile-time dependency injection
- **Kotlin Coroutines**: Asynchronous programming support
- **Kotlin Standard Library**: Core Kotlin functionality

### Key Interfaces

#### `AppInitializer`
```kotlin
interface AppInitializer {
    fun initialize()
}
```

#### `ApplicationCoroutineScope`
```kotlin
typealias ApplicationCoroutineScope = CoroutineScope
```

## 🔄 Integration Points

### With Application Module
- Provides the foundation for the main application component
- Supplies coroutine dispatchers and scope management
- Enables application initialization framework

### With Other Core Modules
- **Logging**: Provides base interfaces for logging infrastructure
- **Preferences**: Supplies common patterns for preference management
- **Data Layer**: Establishes repository and use case patterns

### With UI Modules
- Provides base classes for ViewModels and UI state management
- Supplies common utilities for UI operations
- Enables dependency injection in Compose components

## 🎯 Design Principles

### Single Responsibility
Each component in this module has a focused, well-defined purpose that serves the entire application.

### Dependency Inversion
High-level modules depend on abstractions defined here, not on concrete implementations.

### Open/Closed Principle
Base classes and interfaces are designed for extension while being closed for modification.

### Composition over Inheritance
Favors composition patterns and dependency injection over deep inheritance hierarchies.

## 🚀 Usage Examples

### Setting up Dependency Injection
```kotlin
@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
interface AppComponent {
    val dispatchers: AppCoroutineDispatchers
    val appScope: ApplicationCoroutineScope
}
```

### Creating an Initializer
```kotlin
@Inject
@SingleIn(AppScope::class)
class MyInitializer : AppInitializer {
    override fun initialize() {
        // Initialization logic
    }
}
```

This module serves as the architectural foundation that enables consistent patterns, proper dependency management, and reliable application lifecycle handling throughout the entire Svg2Compose project.
