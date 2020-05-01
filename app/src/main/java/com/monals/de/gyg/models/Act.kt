package com.monals.de.gyg.models

import com.squareup.moshi.Json

data class Act constructor(
    @Json(name = "reviews")
    val reviewList : List<Review>,
    val totalCount : Int,
    val averageRating: Double,
    @Json(name = "pagination")
    val pagination : Pagination

)