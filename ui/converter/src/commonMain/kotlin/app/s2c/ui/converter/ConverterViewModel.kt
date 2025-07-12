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

    private val inputState = combine(
        flowOf(12),
        parser.map {
            when (it) {
                is IconParser.SvgParser -> ConverterViewState.Input.Parser.SVG
                is IconParser.AndroidVectorParser -> ConverterViewState.Input.Parser.VECTOR
            }
        }.flowOn(dispatchers.computation),
    ) { _, parser ->
        ConverterViewState.Input(
            parser = parser,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConverterViewState.Input.Init,
    )

    private val result = combine(
        sourceCodeInputHelper.textFlow.debounce(500L).distinctUntilChanged().filterNot { it.isBlank() },
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

    private val outputState = combine(
        result, flowOf(12),
    ) { result, _ ->
        result?.fold(
            onSuccess = {
                // TODO: Refactor, ot have switcher between Preview and Code
                ConverterViewState.Output.Preview(icon = it.toImageVector())
            },
            onFailure = { ConverterViewState.Output.Error(it.message ?: "Unknown error") },
        ) ?: ConverterViewState.Output.Placeholder
    }.flowOn(dispatchers.computation).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConverterViewState.Output.Init,
    )

    val state: StateFlow<ConverterViewState> = combine(
        inputState, outputState,
    ) { input, output ->
        ConverterViewState(
            input = input,
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


    fun onSelectParser(parser: ConverterViewState.Input.Parser) = with(this.parser) { value = parser.p }
}