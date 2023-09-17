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
    val sourceBase: MutableStateFlow<String?> = MutableStateFlow(null)
    val result: MutableStateFlow<String?> = MutableStateFlow(null)


    fun toDecimal() {
        try {
            if (sourceNumber.value.isNullOrBlank() || sourceBase.value.isNullOrBlank()) {
                emptyNumber.tryEmit("Введите число")
            } else {
                val base = sourceBase.value?.toIntOrNull() ?: 10
                val decimalResult = sourceNumber.value?.toInt(base)
                result.value = decimalResult.toString()
            }
        } catch (e: NumberFormatException) {
            invalid.tryEmit("Неверное число или основание числа")
        }
    }


    private fun fromDecimal(number: Int, base: Int): String {
        return number.toString(base)
    }

    fun resultFromDecimal() {
        try {
            if (sourceNumber.value.isNullOrBlank()
                || sourceBase.value.isNullOrBlank()
            ) {
                emptyNumber.tryEmit("Введите число")
                return
            } else
                if (sourceBase.value.toString().toInt() !in 2..16) {
                    invalid.tryEmit("Введите основание числа от 2 до 16")
                } else {
                    val number = sourceNumber.value.toString().toInt()
                    val base = sourceBase.value.toString().toInt()
                    result.value = fromDecimal(number, base)
                }
        } catch (e: NumberFormatException) {
            invalid.tryEmit("Неверное число или основание числа")
            1
        }
    }

    fun clearOnClicked() {
        sourceNumber.value = null
        sourceBase.value = null
        result.value = null
    }
}