package com.monals.de.gyg.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** Custom Factory to retrieve or create (only when needed) the [ViewModel]  */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory () :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ReviewViewModel::class.java) ->
                    ReviewViewModel()
                isAssignableFrom(DetailsViewModel::class.java) ->
                    DetailsViewModel()
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    return ViewModelProvider(this, ViewModelFactory()).get(viewModelClass)

}