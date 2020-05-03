package com.monals.de.gyg.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.network.ReviewApiService
import io.reactivex.disposables.CompositeDisposable

class ReviewDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val reviewApiService: ReviewApiService)
    : DataSource.Factory<Int, Review>() {

    val reviewDataSourceLiveData = MutableLiveData<ReviewsDataSource>()

    override fun create(): DataSource<Int, Review> {
        val newsDataSource = ReviewsDataSource(
            reviewApiService,
            compositeDisposable
        )
        reviewDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}