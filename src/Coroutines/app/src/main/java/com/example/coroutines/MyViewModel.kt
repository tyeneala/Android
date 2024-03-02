package com.example.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Calc { FACTORIAL, LOGARITHM, ROOT, POWER, ALL }

enum class Mode(name: String) {
    RUN("Run"),
    CANCEL("Cancel"),
    RUN_ALL("Run all")
}

data class MyUiState(
    val modeOfFactorialButton: Mode = Mode.RUN,
    val resultFactorial: String = "",
    val modeOfLogarithmButton: Mode = Mode.RUN,
    val resultLn: String = "",
    val resultLg: String = "",
    val modeOfPowerButton: Mode = Mode.RUN,
    val resultSquaring: String = "",
    val resultCubing: String = "",
    val modeOfRootButton: Mode = Mode.RUN,
    val resultSqrtRoot: String = "",
    val resultCubeRoot: String = "",
    val modeOfCommonButton: Mode = Mode.RUN_ALL,
)

class MyViewModel: ViewModel() {
    private val scope = viewModelScope
    private val _state: MutableStateFlow<MyUiState> = MutableStateFlow(MyUiState())
    val state: StateFlow<MyUiState> = _state
    private val jobMap = HashMap<Calc, Job?>()

    init {
        Calc.values().forEach { jobMap[it] = null }
    }

    fun doAction(number: String, action: Calc) {
        if (!checkNumber(number)) return

        if (jobMap[action]?.isActive == true) {
            scope.launch{ jobMap[action]?.cancelAndJoin() }
            return
        }

        if (action == Calc.ALL) {
            jobMap[Calc.ALL] = scope.launch(Dispatchers.Default) {
                jobMap.forEach {
                    if (it.key != Calc.ALL) {
                        it.value?.cancelAndJoin()
                        selectFunction(number, it.key)
                    }
                }
            }

        } else {
            selectFunction(number, action)
        }
    }

    private fun selectFunction(number: String, action: Calc) = scope.launch(Dispatchers.Default) {
        when(action) {
            Calc.FACTORIAL -> jobMap[Calc.FACTORIAL] = runCalcFactorial(number.toInt())
            Calc.LOGARITHM -> jobMap[Calc.LOGARITHM] = runCalcLogarithm(number.toInt())
            Calc.ROOT -> jobMap[Calc.ROOT] = runCalcRoot(number.toDouble())
            Calc.POWER -> jobMap[Calc.POWER] = runCalcPower(number.toInt())
            Calc.ALL -> {}
        }
    }

    private fun runCalcFactorial(number: Int) = scope.launch(Dispatchers.Default) {
        _state.update { it.copy(modeOfFactorialButton = Mode.CANCEL) }
        _state.update { it.copy(resultFactorial = "") }
        _state.update { it.copy(resultFactorial = calculateFactorial(number)) }
    }.apply {
        invokeOnCompletion {
            _state.update { it.copy(modeOfFactorialButton = Mode.RUN) }
        }
    }

    private fun runCalcLogarithm(number: Int) = scope.launch {
        _state.update { it.copy(modeOfLogarithmButton = Mode.CANCEL) }
        _state.update { it.copy(resultLg = "") }
        _state.update { it.copy(resultLn = "") }

        _state.update { it.copy(resultLg = async { calculateLogarithm(number) }.await()) }
        _state.update { it.copy(resultLn = async {
//            delay(1000L)
            calculateLogarithm(number, 10.0)
        }.await()) }
    }.apply {
        invokeOnCompletion {
            _state.update { it.copy(modeOfLogarithmButton = Mode.RUN) }
        }
    }

    private fun runCalcRoot(number: Double) = scope.launch {
        _state.update { it.copy(modeOfRootButton = Mode.CANCEL) }
        _state.update { it.copy(resultSqrtRoot = "") }
        _state.update { it.copy(resultCubeRoot = "") }

        _state.update { it.copy(resultSqrtRoot = async { calculateRoot(number) }.await()) }
        _state.update { it.copy(resultCubeRoot = async {
//            delay(1000L)
            calculateRoot(number,3)
        }.await()) }
    }.apply {
        invokeOnCompletion {
            _state.update { it.copy(modeOfRootButton = Mode.RUN) }
        }
    }

    private fun runCalcPower(number: Int) = scope.launch {
        _state.update { it.copy(modeOfPowerButton = Mode.CANCEL) }
        _state.update { it.copy(resultSquaring = "") }
        _state.update { it.copy(resultCubing = "") }

        _state.update { it.copy(resultSquaring = async { calculatePower(number) }.await()) }
        _state.update { it.copy(resultCubing = async {
            delay(1000L)
            calculatePower(number,3)
        }.await()) }
    }.apply {
        invokeOnCompletion {
            _state.update { it.copy(modeOfPowerButton = Mode.RUN) }
        }
    }

    private fun checkNumber(number: String) =
        number.matches("^[0-9]+$".toRegex()) && number.length < 10

}
