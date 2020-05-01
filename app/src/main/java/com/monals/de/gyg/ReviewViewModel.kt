package com.monals.de.gyg

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.monals.de.gyg.models.Act
import com.monals.de.gyg.network.ReviewApi
import com.monals.de.gyg.network.ReviewApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


enum class ReviewApiStatus{LOADING, ERROR, DONE}

class ReviewViewModel : ViewModel() {

    private val _status = MutableLiveData<ReviewApiStatus>()

    val status: LiveData<ReviewApiStatus>
    get() = _status

    private val _act = MutableLiveData<Act>()

    val act: LiveData<Act>
    get() = _act


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getAct()
    }

    private fun getAct() {
        coroutineScope.launch {
            var getActDeferred = ReviewApi.retrofitService.getReviews(100, 1300)
            try {
                _status.value = ReviewApiStatus.LOADING
                val actResult = getActDeferred.await()
                _status.value = ReviewApiStatus.DONE
                _act.value = actResult
            } catch (e: Exception) {
                Log.d("Slotest", "Slo" + e.message)
                _status.value = ReviewApiStatus.ERROR
//                _act.value = Act()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}