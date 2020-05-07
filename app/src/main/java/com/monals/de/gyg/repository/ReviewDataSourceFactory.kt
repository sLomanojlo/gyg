package com.monals.de.gyg.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.network.ReviewApiService
import io.reactivex.disposables.CompositeDisposable

/**ReviewDataSourceFactory in charge of creating a [ReviewsDataSource] */
class ReviewDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val reviewApiService: ReviewApiService)
    : DataSource.Factory<Int, Review>() {

    val reviewDataSourceLiveData = MutableLiveData<ReviewsDataSource>()

    override fun create(): DataSource<Int, Review> {
        val reviewsDataSource = ReviewsDataSource(
            reviewApiService,
            compositeDisposable
        )
        reviewDataSourceLiveData.postValue(reviewsDataSource)
        return reviewsDataSource
    }
}