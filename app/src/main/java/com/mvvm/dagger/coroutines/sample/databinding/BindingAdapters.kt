package com.mvvm.dagger.coroutines.sample.databinding

import androidx.databinding.BindingAdapter
import android.text.InputFilter
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapters {

    const val EMPTY = 0

    @JvmStatic
    @BindingAdapter("errorText")
    fun EditText.setErrorText(errorResource: Int) {
        error = when (errorResource) {
            EMPTY -> null
            else -> context.getString(errorResource)
        }
    }

    @JvmStatic
    @BindingAdapter("filters")
    fun EditText.setInputFilters(filterArray: Array<InputFilter>) {
        filters = filterArray
    }

    @JvmStatic
    @BindingAdapter("url")
    fun ImageView.setImageUrl(url: String) {
        Glide.with(this).load(url).into(this)
    }


}