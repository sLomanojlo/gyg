package com.monals.de.gyg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.monals.de.gyg.repository.ReviewDataSourceFactory
import com.monals.de.gyg.repository.ReviewsDataSource
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.network.ReviewApi
import io.reactivex.disposables.CompositeDisposable

/**Enum class in charge of setting the state of the network call.*/
enum class ReviewApiStatus { LOADING, ERROR, DONE }

private const val PAGE_SIZE = 10 /**Initial page size.*/

/**Core of the app, where all the magic happens!*/
class ReviewViewModel : ViewModel() {

    private val reviewApiService = ReviewApi.retrofitService
    private val reviewDataSourceFactory: ReviewDataSourceFactory
    private val compositeDisposable = CompositeDisposable()

    val reviewList: LiveData<PagedList<Review>>

    /**Init block in charge of fetching the data and populating the reviewList.*/
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