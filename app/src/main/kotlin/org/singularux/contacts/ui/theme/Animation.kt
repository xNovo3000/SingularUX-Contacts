package org.singularux.contacts.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val Duration = 250
private const val SplitOffset = 8

private val AcceleratedEasing = CubicBezierEasing(0.0F, 0.0F, 0.2F, 1.0F)

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        ),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        )
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        ),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        )
    )
}

@ExperimentalAnimationApi
val PopEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        ),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        )
    )
}

@ExperimentalAnimationApi
val PopExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        ),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = Duration,
            easing = AcceleratedEasing
        )
    )
}
