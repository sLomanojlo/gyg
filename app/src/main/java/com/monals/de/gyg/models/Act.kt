package com.monals.de.gyg.models


data class Act(
    val reviews : List<Review>,
    val totalCount : Int,
    val averageRating: Double,
    val pagination : Pagination

)