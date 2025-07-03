package com.howtokaise.mynews.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.howtokaise.mynews.data.remote.Article
import kotlinx.coroutines.launch
import shareImageWithText

@Composable
fun ShareImageButton(article: Article) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = "Share",
        modifier = Modifier
            .clickable {
                coroutineScope.launch {
                    shareImageWithText(
                        context = context,
                        imageUrl = article.image,
                        articleLink = article.link
                    )
                }
            }
    )
}