# Common UI DI Module

## 📋 Module Overview

The `common:ui:di` module provides dependency injection infrastructure specifically for UI components and ViewModels in the Svg2Compose application. It implements a custom ViewModel factory system using Kotlin Inject (Metro) that enables proper dependency injection for Compose ViewModels with support for assisted injection and SavedStateHandle parameters.

## 🔑 Key Features & Components

### ViewModel Factory System
- **`MetroViewModelFactory`** - Custom ViewModelProvider.Factory implementation for Kotlin Inject
- **`ViewModelFactoryOwner`** - Interface for components that provide ViewModel factories
- **`LocalViewModelFactoryOwner`** - CompositionLocal for accessing ViewModel factories in Compose

### Dependency Injection Infrastructure
- **`ViewModelScope`** - Custom scope for ViewModel dependencies
- **`ViewModelGraph`** - Graph extension for ViewModel dependency injection
- **`ViewModelKey`** - MapKey annotation for ViewModel multibinding

### Assisted Injection Support
- **`ViewModelAssistedFactory`** - Interface for ViewModels requiring assisted dependencies
- **`injectedViewModel()`** - Compose functions for creating injected ViewModels
- **Creation callbacks** - Support for ViewModels with assisted parameters

### Extension Functions
- **`withCreationCallback()`** - Adds creation callbacks to CreationExtras
- **`addCreationCallback()`** - Helper for MutableCreationExtras manipulation

## 🔧 How It Works

### ViewModel Creation Flow
1. **Factory Registration**: ViewModels are registered in the dependency injection graph
2. **Factory Resolution**: `MetroViewModelFactory` resolves the appropriate factory
3. **Assisted Injection**: For ViewModels with assisted dependencies, creation callbacks are used
4. **Instance Creation**: ViewModels are created with proper dependency injection

### Architecture Components

#### `MetroViewModelFactory`
```kotlin
@ContributesBinding(ViewModelScope::class)
@Inject
class MetroViewModelFactory(
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
    private val viewModelAssistedProviders: Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>> = emptyMap(),
) : ViewModelProvider.Factory
```

#### `injectedViewModel()` Functions
```kotlin
@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current),
    key: String? = null,
    factory: ViewModelProvider.Factory? = LocalViewModelFactoryOwner.current.viewModelFactory,
): VM

@Composable
inline fun <reified VM : ViewModel, VMF> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current),
    key: String? = null,
    factory: ViewModelProvider.Factory? = LocalViewModelFactoryOwner.current.viewModelFactory,
    noinline creationCallback: CreationExtras.(VMF) -> VM,
): VM
```

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Multiplatform**: Cross-platform code sharing
- **Jetpack Compose**: UI framework integration
- **Androidx Lifecycle**: ViewModel and lifecycle management
- **Androidx Navigation**: Navigation component integration

### Internal Module Dependencies
- `projects.core.base` - Foundation utilities and base interfaces

### External Libraries
- `androidx.lifecycle.viewmodel.compose` - Compose ViewModel integration
- `androidx.navigation.compose` - Navigation framework
- `dev.zacsweers.metro` - Kotlin Inject (Metro) dependency injection framework

## 🔄 Integration Points

### With Application Module
- Provides ViewModel factory infrastructure for the main application
- Integrates with the application's dependency injection graph
- Supplies ViewModel creation capabilities to the app component

### With UI Modules
- **Converter UI**: Enables dependency injection for conversion screen ViewModels
- **Config UI**: Provides ViewModel factory for configuration screen ViewModels
- **Common UI Components**: Supplies ViewModel creation utilities for reusable components

### With Core Modules
- **Core Base**: Uses base dependency injection patterns and scoping
- **Core Preferences**: Enables injection of preferences into ViewModels
- **Core Logging**: Allows logging infrastructure injection into ViewModels

## 🎯 Design Principles

### Compile-Time Safety
Uses Kotlin Inject (Metro) for compile-time dependency injection, eliminating runtime reflection and improving performance.

### Assisted Injection Support
Provides full support for ViewModels that require assisted dependencies, including SavedStateHandle and other runtime parameters.

### Compose Integration
Seamlessly integrates with Jetpack Compose through CompositionLocal providers and custom Compose functions.

### Scope Management
Implements proper scoping for ViewModel dependencies to ensure correct lifecycle management.

## 🚀 Usage Examples

### Basic ViewModel Injection
```kotlin
@Composable
fun ConverterScreen(
    navigateConfig: () -> Unit,
) {
    // Inject ViewModel using the injectedViewModel() function
    val viewModel: ConverterViewModel = injectedViewModel()
    
    // Use the injected ViewModel
    ConverterScreenContent(viewModel = viewModel)
}
```

### Defining a ViewModel with Metro DI
```kotlin
@ContributesIntoMap(ViewModelScope::class)
@ViewModelKey(ConverterViewModel::class)
@Inject
class ConverterViewModel(
    private val dispatchers: AppCoroutineDispatchers,
    prefs: AppPreferences,
) : ViewModel() {
    // ViewModel implementation
}
```

### Setting up Metro DI Graph and ViewModelFactoryOwner
```kotlin
fun main() {
    // Create Metro injection graph
    val appGraph = createGraph<AppGraph>()
    appGraph.initializers.initialize()

    // Create ViewModel graph extension
    val viewModelGraph = appGraph.asContribution<ViewModelGraph.Factory>().createViewModelGraph()

    application {
        CompositionLocalProvider(
            // Provide ViewModelFactoryOwner for injectedViewModel calls
            LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                override val viewModelFactory: ViewModelProvider.Factory get() = viewModelGraph.vmFactory
            },
        ) {
            App()
        }
    }
}
```

### Defining the App Graph with Metro
```kotlin
@DependencyGraph(AppScope::class, isExtendable = true)
interface AppGraph {
    ...
}
```

### ViewModel Graph Extension
```kotlin
@ContributesGraphExtension(ViewModelScope::class)
interface ViewModelGraph {
    val vmFactory: ViewModelProvider.Factory

    @ContributesGraphExtension.Factory(AppScope::class)
    fun interface Factory {
        fun createViewModelGraph(): ViewModelGraph
    }
}
```

## 🔍 Key Interfaces

### `ViewModelFactoryOwner`
```kotlin
interface ViewModelFactoryOwner {
    val viewModelFactory: ViewModelProvider.Factory
}
```

### `ViewModelAssistedFactory`
```kotlin
interface ViewModelAssistedFactory
```

### `ViewModelGraph`
```kotlin
@ContributesGraphExtension(ViewModelScope::class)
interface ViewModelGraph {
    val vmFactory: ViewModelProvider.Factory
    
    @ContributesGraphExtension.Factory(AppScope::class)
    fun interface Factory {
        fun createViewModelGraph(): ViewModelGraph
    }
}
```

This module serves as the bridge between Kotlin Inject dependency injection and Jetpack Compose ViewModels, enabling clean, testable, and properly scoped ViewModel creation throughout the Svg2Compose application.
