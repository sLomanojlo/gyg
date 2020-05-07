package com.monals.de.gyg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monals.de.gyg.models.Review

/**Simple ViewModel in charge of handling the selected Review.*/
class DetailsViewModel : ViewModel() {

    var selectedReview: LiveData<Review> = MutableLiveData()

    fun setReview(review: Review) {
        selectedReview = MutableLiveData(review)
    }
}