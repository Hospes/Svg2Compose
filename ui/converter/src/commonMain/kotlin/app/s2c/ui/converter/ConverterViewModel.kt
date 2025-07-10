package app.s2c.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.s2c.core.base.util.AppCoroutineDispatchers
import app.s2c.preferences.AppPreferences
import com.teobaranga.kotlin.inject.viewmodel.runtime.ContributesViewModel
import kotlinx.coroutines.flow.*
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope

@Inject
@ContributesViewModel(scope = AppScope::class)
class ConverterViewModel(
    dispatchers: AppCoroutineDispatchers,
    prefs: AppPreferences,
) : ViewModel() {

    val state: StateFlow<ConverterViewState> = combine(
        flowOf(12), flowOf(12)
    ) { _, _ ->
        ConverterViewState()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConverterViewState.Init,
    )

}