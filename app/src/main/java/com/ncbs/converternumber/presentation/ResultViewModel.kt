package com.ncbs.converternumber.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow


class ResultViewModel : ViewModel() {

    val invalid: MutableSharedFlow<String> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val emptyNumber: MutableSharedFlow<String> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sourceNumber: MutableStateFlow<String?> = MutableStateFlow(null)
    private val sourceNumber2: MutableStateFlow<String?> = MutableStateFlow(null)
    val sourceBase1: MutableStateFlow<String?> = MutableStateFlow(null)
    val sourceBase2: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: MutableStateFlow<String?> = MutableStateFlow(null)

    fun resultMixSystem() {
        try {
            when {
                sourceNumber.value.isNullOrBlank() || sourceBase1.value.isNullOrBlank()
                        || sourceBase2.value.isNullOrBlank() -> emptyNumber.tryEmit("Введите число")

                sourceBase2.value.toString().toInt() !in 2..16
                        || sourceBase1.value.toString()
                    .toInt() !in 2..16 -> invalid.tryEmit("Введите основание числа от 2 до 16")

                else -> {
                    toDecimal()
                    resultFromDecimal()
                }
            }
        } catch (e: NumberFormatException) {
            invalid.tryEmit("Неверное число или основание числа")
        }
    }

    private fun toDecimal() {
        val base = sourceBase1.value?.toInt() ?: 10
        val decimalResult = sourceNumber.value?.toInt(base)
        sourceNumber2.value = decimalResult.toString()
    }

    private fun fromDecimal(number: Int, base: Int): String {
        return number.toString(base)
    }

    private fun resultFromDecimal() {
        val number = sourceNumber2.value.toString().toInt()
        val base = sourceBase2.value.toString().toInt()
        result.value = fromDecimal(number, base)
    }

    fun clearOnClicked() {
        sourceNumber.value = null
        sourceBase1.value = null
        result.value = null
    }
}