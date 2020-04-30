package com.monals.de.gyg.models

data class Act constructor(
    val reviewList : List<Review>,
    val totalCount : Int,
    val averageRating: Double,
    val pagination : Pagination

)