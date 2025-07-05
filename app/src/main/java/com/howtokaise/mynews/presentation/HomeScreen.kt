package com.howtokaise.mynews.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.howtokaise.mynews.R
import com.howtokaise.mynews.domain.data.room.formatDateTime
import com.howtokaise.mynews.domain.data.room.limitWords
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(
    viewModal: NewViewModel,
    scrollToTopTrigger : MutableState<Boolean>,
    refreshTrigger : MutableState<Boolean>
) {

    val articles by viewModal.articles.collectAsState()
    val pagerState = rememberPagerState(pageCount = { articles.size })

    val isRefreshing by remember { derivedStateOf { articles.isEmpty() } }
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }

    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(scrollToTopTrigger.value) {
        if (scrollToTopTrigger.value){

            scrollToTopTrigger.value = false

            coroutineScope.launch{
                pagerState.animateScrollToPage(0)
                swipeRefreshState.isRefreshing = true
                delay(600)
                viewModal.refreshNews()
                delay(300)
                swipeRefreshState.isRefreshing = false
            }

        }
    }

    var showFullScreen by remember { mutableStateOf(false) }
    var currentImageUrl by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("General") }

    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.Black else Color.White)
    ) {
        TopBar(
            viewModal,
            selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
                viewModal.fetchNewsByCategory(category)
            }
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                swipeRefreshState.isRefreshing = true
                viewModal.refreshNews()
                swipeRefreshState.isRefreshing = false
            }
        ) {
            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    VerticalPager(
                        state = pagerState,
                        pageSpacing = 8.dp,
                        flingBehavior = PagerDefaults.flingBehavior(
                            state = pagerState,
                            snapPositionalThreshold = 0.1f
                        )
                    ) { page ->
                        if (page >= articles.size - 3) {
                            viewModal.loadMoreNews()
                        }
                        val article = articles[page]
                        val scrollState = rememberScrollState()

                        val isVideo = article.image.endsWith(".mp4", ignoreCase = true)
                                || article.image.endsWith(".webm", ignoreCase = true)
                                || article.image.endsWith(".mkv", ignoreCase = true)

                        var isImageLoading by remember(page) { mutableStateOf(true) }


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(1.dp)
                                .verticalScroll(scrollState)
                        ) {
                            if (isVideo) {
                                VideoPlayer(videoUrl = article.image)
                            } else {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(article.image)
                                        .crossfade(true)
                                        .diskCachePolicy(CachePolicy.ENABLED)
                                        .listener(
                                            onSuccess = { _, _ -> isImageLoading = false },
                                            onError = { _, _ -> isImageLoading = false }
                                        )
                                        .build(),
                                    contentDescription = null,
                                    error = painterResource(id = R.drawable.error),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(270.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            currentImageUrl = article.image
                                            showFullScreen = true
                                        }
                                        .placeholder(
                                            visible = isImageLoading,
                                            highlight = PlaceholderHighlight.shimmer()
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = article.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.link))
                                        context.startActivity(intent)
                                    }

                            )

                            Text(
                                text = article.body,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.link))
                                        context.startActivity(intent)
                                    }

                            )

                            Spacer(modifier = Modifier.height(15.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (article.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Like",
                                    modifier = Modifier.clickable {
                                        viewModal.toggleLike(article)
                                    }
                                )

                                Spacer(modifier = Modifier.width(20.dp))

                                ShareImageButton(article)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = formatDateTime(article.time) + " |",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = limitWords(article.author) + " |",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                )
                                Text(
                                    text = article.source,
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    if (showFullScreen) {
                        BackHandler {
                            showFullScreen = false
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                                .clickable { showFullScreen = false }
                        ) {
                            AsyncImage(
                                model = currentImageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }
        }
    }
}
