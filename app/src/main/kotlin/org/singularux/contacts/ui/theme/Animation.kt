package org.singularux.contacts.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val Duration = 250
private const val SplitOffset = 8

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = Duration),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(durationMillis = Duration)
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = Duration),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(durationMillis = Duration)
    )
}

@ExperimentalAnimationApi
val PopEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = Duration),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(durationMillis = Duration)
    )
}

@ExperimentalAnimationApi
val PopExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = Duration),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(durationMillis = Duration)
    )
}
