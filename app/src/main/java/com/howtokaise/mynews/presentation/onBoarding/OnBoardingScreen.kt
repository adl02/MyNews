//package com.howtokaise.mynews.presentation
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.howtokaise.mynews.R
//import com.howtokaise.mynews.data.local.DataStoreManager
//import kotlinx.coroutines.launch
//
//@Composable
//fun OnBoardingScreen(onFinish :() -> Unit) {
//
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val dataStore = remember { DataStoreManager(context) }
//
//    val pages = listOf(
//        OnboardingPage(
//            title = "Welcome to MyNews",
//            description = "Stay informed with latest headlines in 50 words.",
//            imageRes = R.drawable.onboarding1
//        ),
//        OnboardingPage(
//            title = "Like What You Read",
//            description = "Save your favorite news articles easily.",
//            imageRes = R.drawable.onboarding2
//        ),
//        OnboardingPage(
//            title = "Share with Friends",
//            description = "Let others know what matters to you.",
//            imageRes = R.drawable.onboarding3
//        )
//    )
//
//    val pagerState = rememberPagerState(initialPage = 0){
//        pages.size
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        HorizontalPager(state = pagerState) { page ->
//            val current = pages[page]
//            Column {
//                Image(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.7f),
//
//                    painter = painterResource(id = current.imageRes),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop
//
//                )
//                Spacer(modifier = Modifier.height(20.dp))
//                Text(
//                    text = current.title,
//                    modifier = Modifier
//                        .padding(horizontal = 20.dp),
//                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
//                )
//                Spacer(modifier = Modifier.height(20.dp))
//                Text(
//                    text = current.description,
//                    fontSize = 17.sp,
//                    modifier = Modifier.padding(horizontal = 30.dp),
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//            }
//        }
//
//        Row (
//            modifier = Modifier
//                .padding(bottom = 35.dp, end = 10.dp),
//        ){
//            DotsIndicator(
//                totalDots = pages.size,
//                selectedIndex = pagerState.currentPage
//            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            if (pagerState.currentPage == pages.lastIndex) {
//                Button(
//                    onClick = {
//                        scope.launch {
//                            dataStore.saveOnboardingState(true)
//                             onFinish()
//                        }
//                    },
//
//                ) {
//                    Text("Get Started")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DotsIndicator(
//    modifier: Modifier = Modifier,
//    totalDots: Int,
//    selectedIndex: Int,
//    dotSize: Dp = 8.dp,
//    selectedDotSize: Dp = 10.dp,
//    dotSpacing: Dp = 8.dp,
//    selectedColor: Color = Color.Green,
//    unSelectedColor: Color = Color.LightGray
//) {
//    Row(
//        modifier = modifier.padding(16.dp),
//       // horizontalArrangement = Arrangement.SpaceBetween,
//    ) {
//        repeat(totalDots) { index ->
//            val color = if (index == selectedIndex) selectedColor else unSelectedColor
//            val size = if (index == selectedIndex) selectedDotSize else dotSize
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = dotSpacing / 2)
//                    .size(size)
//                    .background(color = color, shape = CircleShape)
//            )
//        }
//    }
//}
//
//data class OnboardingPage(
//    val title: String,
//    val description: String,
//    val imageRes: Int
//)