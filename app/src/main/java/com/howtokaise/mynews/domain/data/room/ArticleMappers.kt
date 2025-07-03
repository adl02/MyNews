import com.howtokaise.mynews.domain.data.room.ArticleEntity

import com.howtokaise.mynews.data.remote.Article

fun ArticleEntity.toArticle(): Article = Article(
    title = title,
    body = body,
    image = image,
    link = link,
    isLiked = true,
    source = source,
    author = author,
    time = time
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    title = title,
    body = body,
    image = image,
    link = link,
    source = source,
    author = author,
    time = time

)
