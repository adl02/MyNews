package com.howtokaise.mynews.data.remote

data class Article(
    val title: String,
    val body: String,
    val image: String,
    val link: String,
    var isLiked : Boolean = false,
    val source : String = "",
    val author : String = "",
    val time : String = "",
    val likedAt : Long = 0L
)

