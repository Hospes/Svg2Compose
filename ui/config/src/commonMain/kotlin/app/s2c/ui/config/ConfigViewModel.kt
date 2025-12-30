package app.s2c.ui.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.s2c.core.base.util.AppCoroutineDispatchers
import app.s2c.core.logs.Log
import app.s2c.preferences.AppPreferences
import app.s2c.ui.common.input.DefaultTextInputStateHelper
import app.s2c.ui.di.ViewModelAssistedFactory
import app.s2c.ui.di.ViewModelKey
import app.s2c.ui.di.ViewModelScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AssistedInject
class ConfigViewModel(
    @Assisted private val id: Int,
    dispatchers: AppCoroutineDispatchers,
    prefs: AppPreferences,
) : ViewModel() {

    @ContributesIntoMap(ViewModelScope::class)
    @ViewModelKey(ConfigViewModel::class)
    @AssistedFactory
    interface Factory : ViewModelAssistedFactory {
        fun create(id: Int): ConfigViewModel
    }

    private val initConfig = prefs.parserConfig.getNotSuspended()

    private val _package = DefaultTextInputStateHelper(initialText = initConfig.pkg ?: "")
    val packageInputState = _package.state
    private val configOptimize = MutableStateFlow(initConfig.optimize)
    private val _receiverType = DefaultTextInputStateHelper(initialText = initConfig.receiverType ?: "")
    val receiverTypeInputState = _receiverType.state
    private val configAddToMaterial = MutableStateFlow(initConfig.addToMaterial)
    private val configNoPreview = MutableStateFlow(initConfig.noPreview)
    private val configMakeInternal = MutableStateFlow(initConfig.makeInternal)
    private val configMinified = MutableStateFlow(initConfig.minified)


    val state: StateFlow<ConfigViewState> = combine(
        configOptimize, configAddToMaterial, configNoPreview, configMakeInternal, configMinified,
    ) { optimize, addToMaterial, noPreview, makeInternal, minified ->
        ConfigViewState(
            optimize = optimize,
            addToMaterial = addToMaterial,
            noPreview = noPreview,
            makeInternal = makeInternal,
            minified = minified,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConfigViewState(
            optimize = initConfig.optimize,
            addToMaterial = initConfig.addToMaterial,
            noPreview = initConfig.noPreview,
            makeInternal = initConfig.makeInternal,
            minified = initConfig.minified,
        ),
    )


    init {
        Log.warn { "ConfigViewModel init id: $id" }
        viewModelScope.launch { _package.clearErrorOnInputUpdate() }
        viewModelScope.launch { _receiverType.clearErrorOnInputUpdate() }

        // Update prefs with package updates
        viewModelScope.launch {
            _package.textFlow
                .debounce(500L).distinctUntilChanged().map { it.ifBlank { null } }
                .collectLatest { value -> prefs.parserConfig.update { config -> config.copy(pkg = value) } }
        }

        // Update prefs with receiver type updates
        viewModelScope.launch {
            _receiverType.textFlow
                .debounce(500L).distinctUntilChanged().map { it.ifBlank { null } }
                .collectLatest { value -> prefs.parserConfig.update { config -> config.copy(receiverType = value) } }
        }

        viewModelScope.launch { configOptimize.collectLatest { prefs.parserConfig.update { config -> config.copy(optimize = it) } } }
        viewModelScope.launch { configAddToMaterial.collectLatest { prefs.parserConfig.update { config -> config.copy(addToMaterial = it) } } }
        viewModelScope.launch { configNoPreview.collectLatest { prefs.parserConfig.update { config -> config.copy(noPreview = it) } } }
        viewModelScope.launch { configMakeInternal.collectLatest { prefs.parserConfig.update { config -> config.copy(makeInternal = it) } } }
        viewModelScope.launch { configMinified.collectLatest { prefs.parserConfig.update { config -> config.copy(minified = it) } } }
    }

    fun onOptimizeChanged(bool: Boolean) = with(configOptimize) { value = bool }
    fun onAddToMaterialChanged(bool: Boolean) = with(configAddToMaterial) { value = bool }
    fun onNoPreviewChanged(bool: Boolean) = with(configNoPreview) { value = bool }
    fun onMakeInternalChanged(bool: Boolean) = with(configMakeInternal) { value = bool }
    fun onMinifiedChanged(bool: Boolean) = with(configMinified) { value = bool }
}