package com.howtokaise.mynews.domain.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import okio.Source

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val link : String,
    val title : String,
    val body : String,
    val image : String,
    val source: String = "",
    val author : String = "",
    val time : String = "",
    @ColumnInfo(defaultValue = "0")
    val likedAt : Long = System.currentTimeMillis()
)
