package com.monals.de.gyg.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.monals.de.gyg.models.Act
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://travelers-api.getyourguide.com"
//get /activities/23776/reviews

private val moshi = Moshi.Builder()
    .add(NULL_TO_EMPTY_STRING_ADAPTER)
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ReviewApiService {
    @GET("/activities/23776/reviews")
    fun getReviews(@Query ("limit" ) limit :Int,
    @Query("offset") offset : Int) :
            Deferred<Act>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ReviewApi {
    val retrofitService : ReviewApiService by lazy { retrofit.create(ReviewApiService::class.java) }
}

object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }

        reader.nextNull<Unit>()
        return ""
    }
}