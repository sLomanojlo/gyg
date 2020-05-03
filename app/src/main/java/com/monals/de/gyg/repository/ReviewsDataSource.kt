package com.monals.de.gyg.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.network.ReviewApiService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ReviewsDataSource(private val reviewApiService: ReviewApiService, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Review>() {

    val state: MutableLiveData<ReviewApiStatus> = MutableLiveData()
    private var retryCompletable: Completable? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Review>) {
        updateState(ReviewApiStatus.LOADING)
        compositeDisposable.add(
            reviewApiService.getReviews(0, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(ReviewApiStatus.DONE)
                        callback.onResult(response.reviews, null, params.requestedLoadSize + 1) },
                    {
                        updateState(ReviewApiStatus.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {
        updateState(ReviewApiStatus.LOADING)
        compositeDisposable.add(
            reviewApiService.getReviews(params.key, params.requestedLoadSize)
                .subscribe(
                    { response ->
                        updateState(ReviewApiStatus.DONE)
                        callback.onResult(response.reviews,
                            params.requestedLoadSize + params.key
                        )
                    },
                    {
                        updateState(ReviewApiStatus.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {
    }

    private fun updateState(state: ReviewApiStatus) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}