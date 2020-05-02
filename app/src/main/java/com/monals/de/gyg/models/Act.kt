package com.monals.de.gyg.models

import com.squareup.moshi.Json

data class Act(
    val reviews : List<Review>,
    val totalCount : Int,
    val averageRating: Double,
    val pagination : Pagination

)