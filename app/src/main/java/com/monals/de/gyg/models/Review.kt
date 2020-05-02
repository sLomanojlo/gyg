package com.monals.de.gyg.models

data class Review(
    val id : Int,
    val author : Author,
    val title : String,
    val message : String,
    val enjoyment : String,
    val isAnonymous : Boolean,
    val rating: Int,
    val created: String,
    val language: String,
    val travelerType: String
)