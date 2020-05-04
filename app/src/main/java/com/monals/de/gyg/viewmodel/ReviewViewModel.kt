package com.monals.de.gyg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.monals.de.gyg.repository.ReviewDataSourceFactory
import com.monals.de.gyg.repository.ReviewsDataSource
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.network.ReviewApi
import io.reactivex.disposables.CompositeDisposable


enum class ReviewApiStatus { LOADING, ERROR, DONE }

private const val PAGE_SIZE = 10

class ReviewViewModel : ViewModel() {

    private val reviewApiService = ReviewApi.retrofitService

    var reviewList: LiveData<PagedList<Review>>


    private val compositeDisposable = CompositeDisposable()
    private val reviewDataSourceFactory: ReviewDataSourceFactory

    init {
        reviewDataSourceFactory = ReviewDataSourceFactory(compositeDisposable, reviewApiService)

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        reviewList = LivePagedListBuilder(reviewDataSourceFactory, config).build()
    }

    fun getState(): LiveData<ReviewApiStatus> =
        Transformations.switchMap(reviewDataSourceFactory.reviewDataSourceLiveData,
            ReviewsDataSource::state)

    fun retry() {
        reviewDataSourceFactory.reviewDataSourceLiveData.value?.retry()
    }

    fun isListEmpty(): Boolean {
        return reviewList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}