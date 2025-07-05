package com.howtokaise.mynews.presentation.onBoarding

import androidx.annotation.DrawableRes
import com.howtokaise.mynews.R

sealed class OnboardingModel(
    val title: String,
    val description: String,
    @DrawableRes
    val image: Int
) {
    data object FirstPages : OnboardingModel(
        title = "Welcome to MyNews",
        description = "Stay informed with latest headlines in 50 words.",
        image = R.drawable.onboarding1
    )

    data object SecondPages : OnboardingModel(
        title = "Like What You Read",
        description = "Save your favorite news articles easily.",
        image = R.drawable.onboarding2
    )

    data object ThirdPages : OnboardingModel(
        title = "Share with Friends",
        description = "Let others know what matters to you.",
        image = R.drawable.onboarding3
    )
}