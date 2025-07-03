package com.howtokaise.mynews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howtokaise.mynews.data.api.Constant
import com.howtokaise.mynews.data.remote.Article
import com.howtokaise.mynews.domain.data.room.ArticleDao
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import toArticle
import toEntity

class NewViewModel(private val articleDao: ArticleDao) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles.asStateFlow()

    val likedArticles: StateFlow<List<Article>> = articleDao.getAllLikedArticle()
        .map { list -> list.map { it.toArticle() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val apiClient = NewsApiClient(Constant.apiKey)
    private var currentPage = 1
    private var hasMore = true
    private var isLoading = false

    private val allFetchedArticles = mutableListOf<Article>()
    private val shownArticles = mutableListOf<Article>()
    private val pageSize = 10

    init {
        fetchNews(1)
    }

    fun fetchNews(page: Int) {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            val likedFromDb = articleDao.getAllLikedArticle().first()

            apiClient.getTopHeadlines(
                TopHeadlinesRequest.Builder()
                    .q("trump")
                    .page(page)
                    .pageSize(pageSize)
                    .build(),
                object : NewsApiClient.ArticlesResponseCallback {
                    override fun onSuccess(response: ArticleResponse?) {
                        viewModelScope.launch {
                            val fetched = response?.articles?.map {
                                val link = it.url.orEmpty()
                                Article(
                                    title = it.title.orEmpty(),
                                    body = it.content.orEmpty(),
                                    image = it.urlToImage.orEmpty(),
                                    link = link,
                                    isLiked = likedFromDb.any { db -> db.link == link },
                                    source = it.source?.name.orEmpty(),
                                    author = it.author.orEmpty(),
                                    time = it.publishedAt.orEmpty()
                                )
                            } ?: emptyList()

                            // Add to full pool
                            if (page == 1) {
                                allFetchedArticles.clear()
                                shownArticles.clear()
                            }
                            allFetchedArticles.addAll(fetched)

                            // Determine next batch without repeats
                            val remaining = allFetchedArticles.filterNot { art -> shownArticles.any { it.link == art.link } }
                            val nextBatch = remaining.shuffled().take(pageSize)

                            // Update shown and UI
                            shownArticles.addAll(nextBatch)
                            _articles.value = shownArticles.toList()

                            // Update flags
                            currentPage = page
                            hasMore = remaining.isNotEmpty()
                            isLoading = false
                        }
                    }

                    override fun onFailure(throwable: Throwable?) {
                        viewModelScope.launch {
                            isLoading = false
                        }
                    }
                }
            )
        }
    }

    fun loadMoreNews(){
        if (!hasMore) return
        fetchNews(currentPage + 1)
    }

    fun toggleLike(article: Article) {
        val updated = article.copy(isLiked = !article.isLiked)
        viewModelScope.launch {
            if (updated.isLiked) {
                articleDao.insert(updated.toEntity())
            } else {
                articleDao.delete(updated.link)
            }
        }

        _articles.value = _articles.value.map {
            if (it.link == article.link) updated else it
        }
    }

    fun refreshNews(){
        currentPage = 1
        hasMore = true
        _articles.value = emptyList()
        fetchNews(1)
    }
}
