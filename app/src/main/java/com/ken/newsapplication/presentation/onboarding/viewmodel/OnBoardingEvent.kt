package com.ken.newsapplication.presentation.onboarding.viewmodel

sealed class OnBoardingEvent {
    object SaveAppEntry : OnBoardingEvent()
}