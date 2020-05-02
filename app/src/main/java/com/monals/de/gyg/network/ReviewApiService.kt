package com.monals.de.gyg.network

import com.monals.de.gyg.BuildConfig
import com.monals.de.gyg.models.Act
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://travelers-api.getyourguide.com"
//get /activities/23776/reviews

val client = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    })
    .build()


private val retrofit = Retrofit.Builder()
    .client(client)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ReviewApiService {
    @GET("/activities/23776/reviews")
    fun getReviews(@Query("offset") offset : Int,
                   @Query ("limit" ) limit :Int) :
            Single<Act>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ReviewApi {
    val retrofitService : ReviewApiService by lazy { retrofit.create(ReviewApiService::class.java) }
}

