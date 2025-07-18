# Core Logging Module

## 📋 Module Overview

The `core:logging` module provides centralized logging infrastructure for the Svg2Compose application. It establishes consistent logging patterns, configures log levels, and provides utilities for debugging, monitoring, and troubleshooting across all application modules.

## 🔑 Key Features & Components

### Logging Infrastructure
- **Centralized Logger Configuration** - Single point for logging setup
- **Log Level Management** - Configurable logging levels for different environments
- **Structured Logging** - Consistent log format and structure
- **Performance Logging** - Timing and performance measurement utilities

### Debug Support
- **Development Logging** - Enhanced logging for development builds
- **Error Tracking** - Comprehensive error logging and stack traces
- **Component Tracing** - Module-specific logging categories
- **Log Filtering** - Configurable log filtering by component or level

## 🔧 How It Works

### Logging Architecture
The module provides a unified logging interface that can be configured for different environments:

- **Development**: Verbose logging with detailed debug information
- **Production**: Optimized logging with essential information only
- **Testing**: Minimal logging to avoid test output pollution

### Key Components
- **Logger Factory**: Creates configured logger instances
- **Log Formatters**: Standardized log message formatting
- **Log Appenders**: Output destinations (console, file, etc.)
- **Performance Monitors**: Timing and metrics collection

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Standard Library**: Basic logging functionality
- **Coroutines Support**: Async logging capabilities

### Integration Dependencies
- `core.base` - Base utilities and dependency injection support

## 🔄 Integration Points

### With Application Module
- Provides logging for application startup and lifecycle events
- Enables debugging of dependency injection and initialization

### With Data Module
- Logs parsing operations and data transformations
- Tracks performance of SVG and Vector Drawable processing

### With UI Modules
- Logs user interactions and UI state changes
- Provides debugging information for Compose components

### With Core Modules
- **Base**: Uses foundation utilities for logger configuration
- **Preferences**: May log preference changes and access patterns

## 🎯 Usage Patterns

### Component Logging
Each module can create its own logger instance with appropriate categorization:

```kotlin
class MyComponent {
    private val logger = Logger.getLogger("MyComponent")
    
    fun performOperation() {
        logger.debug("Starting operation")
        // ... operation logic
        logger.info("Operation completed successfully")
    }
}
```

### Error Logging
Standardized error logging with context:

```kotlin
try {
    // risky operation
} catch (e: Exception) {
    logger.error("Operation failed", e)
    // handle error
}
```

### Performance Logging
Timing critical operations:

```kotlin
val startTime = System.currentTimeMillis()
// ... operation
val duration = System.currentTimeMillis() - startTime
logger.debug("Operation took ${duration}ms")
```

## 🚀 Configuration

### Log Levels
- **ERROR**: Critical errors that may cause application failure
- **WARN**: Warning conditions that should be addressed
- **INFO**: General information about application flow
- **DEBUG**: Detailed information for debugging
- **TRACE**: Very detailed tracing information

### Environment-Specific Settings
- **Development**: DEBUG level with console output
- **Production**: INFO level with structured output
- **Testing**: WARN level with minimal output

This module ensures consistent, configurable, and efficient logging throughout the Svg2Compose application, enabling effective debugging, monitoring, and maintenance.
