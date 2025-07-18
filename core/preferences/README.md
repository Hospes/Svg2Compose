# Core Preferences Module

## 📋 Module Overview

The `core:preferences` module manages user preferences and application settings for Svg2Compose. It provides a centralized system for storing, retrieving, and managing user configurations, application state, and customizable settings that persist across application sessions.

## 🔑 Key Features & Components

### Preference Management
- **User Settings Storage** - Persistent storage of user preferences
- **Application Configuration** - Runtime configuration management
- **Default Values** - Sensible defaults for all preferences
- **Type-Safe Access** - Strongly-typed preference access patterns

### Settings Categories
- **UI Preferences** - Theme, layout, and display settings
- **Conversion Settings** - Default conversion parameters and options
- **File Handling** - Recent files, default directories, and file associations
- **Performance Settings** - Optimization and resource usage preferences

### Data Persistence
- **Cross-Platform Storage** - Platform-appropriate preference storage
- **Atomic Updates** - Thread-safe preference modifications
- **Migration Support** - Handling preference schema changes
- **Backup and Restore** - Preference export/import capabilities

## 🔧 How It Works

### Preference Architecture
The module uses a repository pattern with type-safe preference access:

```kotlin
interface PreferenceRepository {
    suspend fun getString(key: String, defaultValue: String): String
    suspend fun setString(key: String, value: String)
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean
    suspend fun setBoolean(key: String, value: Boolean)
    // ... other type-specific methods
}
```

### Key Components
- **Preference Keys**: Centralized definition of all preference keys
- **Default Values**: Type-safe default value definitions
- **Preference Observers**: Reactive preference change notifications
- **Migration Manager**: Handles preference schema evolution

### Storage Implementation
- **Platform-Specific**: Uses appropriate storage mechanism per platform
- **Encrypted Storage**: Sensitive preferences are encrypted
- **Backup Integration**: Automatic backup of critical preferences

## 🏗️ Module-Specific Dependencies

### Core Dependencies
- **Kotlin Coroutines**: Asynchronous preference operations
- **Kotlin Serialization**: Preference serialization and deserialization

### Integration Dependencies
- `core.base` - Base utilities and dependency injection support

### Platform Dependencies
- **JVM**: Uses Java Preferences API or file-based storage
- **Cross-Platform**: Multiplatform storage abstractions

## 🔄 Integration Points

### With Application Module
- Provides application-wide settings and configuration
- Manages window state, size, and position preferences
- Stores user interface preferences and themes

### With UI Modules
- **Converter UI**: Stores conversion history and default settings
- **Config UI**: Provides settings management interface
- **Common UI**: Theme and appearance preferences

### With Data Module
- Stores parsing preferences and default conversion options
- Manages file format preferences and output settings

## 🎯 Preference Categories

### User Interface
- **Theme Selection**: Light/Dark/System theme preference
- **Window Settings**: Size, position, and layout preferences
- **Display Options**: Font size, scaling, and visual preferences

### Conversion Settings
- **Default Options**: Default conversion parameters
- **Output Format**: Preferred output format and styling
- **Color Handling**: Default color mapping preferences

### File Management
- **Recent Files**: Recently opened files and directories
- **Default Paths**: Default input and output directories
- **File Associations**: Preferred file handling options

### Performance
- **Memory Settings**: Memory usage and caching preferences
- **Processing Options**: Multi-threading and performance settings
- **Logging Level**: Debug and logging preferences

## 🚀 Usage Examples

### Reading Preferences
```kotlin
class MyComponent @Inject constructor(
    private val preferences: PreferenceRepository
) {
    suspend fun loadSettings() {
        val theme = preferences.getString("ui.theme", "system")
        val autoSave = preferences.getBoolean("conversion.auto_save", true)
    }
}
```

### Observing Preference Changes
```kotlin
preferences.observe("ui.theme")
    .collect { newTheme ->
        // React to theme changes
        updateTheme(newTheme)
    }
```

### Batch Updates
```kotlin
preferences.transaction {
    setString("ui.theme", "dark")
    setBoolean("ui.animations", true)
    setInt("ui.font_size", 14)
}
```

## 🔒 Security Considerations

### Sensitive Data
- API keys and tokens are encrypted before storage
- User credentials are handled with appropriate security measures
- Preference access is logged for security auditing

### Data Privacy
- User preferences remain local to the device
- No automatic cloud synchronization without explicit user consent
- Clear data deletion options for privacy compliance

This module ensures consistent, secure, and efficient management of user preferences and application settings throughout the Svg2Compose application.
