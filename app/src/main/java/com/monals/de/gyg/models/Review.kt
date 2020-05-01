package com.monals.de.gyg.models

import com.squareup.moshi.Json

data class Review constructor(
    val id : Int,
    @Json(name = "author")
    val author : Author,
    val title : String,
    val message : String,
    val enjoyment : String,
    val isAnonymous : Boolean,
    val rating: Int,
    val created: String,
    val language: String ="",
    var travelerType: String = ""
)