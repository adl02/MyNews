package com.howtokaise.mynews.data.remote

data class NewsResponse(
    val articleCount: Int,
    val articles: List<Article>,
    val fetchedAt: String,
    val source: String
)