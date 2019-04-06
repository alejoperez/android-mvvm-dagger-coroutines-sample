package com.mvvm.dagger.coroutines.sample.utils

import android.text.InputFilter

object EditTextUtils {

    private val noWhiteSpaces: InputFilter = InputFilter { string, start, end, _, _, _ ->
        (start until end)
                .filter { Character.isSpaceChar(string[it]) }
                .forEach { _ -> return@InputFilter "" }
        null
    }

    private val onlyNumbers: InputFilter = InputFilter { string, start, end, _, _, _ ->
        (start until end)
                .filterNot { Character.isDigit(string[it]) }
                .forEach { _ -> return@InputFilter "" }
        null
    }

    fun getWhiteSpaceFilters(): Array<InputFilter> = arrayOf(noWhiteSpaces)

    fun getOnlyNumbersFilters(): Array<InputFilter> = arrayOf(onlyNumbers, noWhiteSpaces)

}
