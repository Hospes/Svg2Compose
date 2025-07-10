package app.s2c.ui.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.s2c.core.base.util.AppCoroutineDispatchers
import app.s2c.data.parser.IconParser
import app.s2c.data.parser.ParserConfig
import app.s2c.preferences.AppPreferences
import app.s2c.ui.common.input.DefaultTextInputStateHelper
import app.s2c.ui.converter.utils.toImageVector
import com.teobaranga.kotlin.inject.viewmodel.runtime.ContributesViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope

@OptIn(FlowPreview::class)
@Inject
@ContributesViewModel(scope = AppScope::class)
class ConverterViewModel(
    dispatchers: AppCoroutineDispatchers,
    prefs: AppPreferences,
) : ViewModel() {

    private val sourceCodeInputHelper = DefaultTextInputStateHelper()
    val sourceCodeInputState = sourceCodeInputHelper.state
    private val outputCodeInputHelper = DefaultTextInputStateHelper()
    val outputCodeInputState = outputCodeInputHelper.state

    private val parser = MutableStateFlow<IconParser>(IconParser.SvgParser)
    private val parserConfig = MutableStateFlow<ParserConfig>(
        ParserConfig(
            optimize = false,
            addToMaterial = false,
            noPreview = false,
            makeInternal = false,
            minified = true
        )
    )

    private val result = combine(
        sourceCodeInputHelper.textFlow.debounce(500L).distinctUntilChanged(),
        parser, parserConfig,
    ) { text, parser, config ->
        IconParser.SvgParser.parse(
            content = text, iconName = "TestIcon",
            config = ParserConfig(
                optimize = false,
                addToMaterial = false,
                noPreview = false,
                makeInternal = false,
                minified = true
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val resultPreview = result.map { it?.getOrNull()?.toImageVector() }

    private val outputState = combine(
        flowOf(12), resultPreview,
    ) { _, preview ->
        ConverterViewState.Output(
            preview = preview,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    val state: StateFlow<ConverterViewState> = combine(
        flowOf(12), outputState,
    ) { _, output ->
        ConverterViewState(
            output = output,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConverterViewState.Init,
    )


    init {
//        viewModelScope.launch {
//            result.mapNotNull { it?. }.collectLatest { }
//        }
    }
}