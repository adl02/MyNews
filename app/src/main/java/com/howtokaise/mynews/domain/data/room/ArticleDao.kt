package com.howtokaise.mynews.domain.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insert(article: ArticleEntity)

    @Query("DELETE FROM articles WHERE link = :link")
    suspend fun delete(link: String)

    @Query("SELECT * FROM articles ORDER BY likedAt DESC")
    fun getAllLikedArticle(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE link = :link)")
    suspend fun isLiked(link: String): Boolean
}

