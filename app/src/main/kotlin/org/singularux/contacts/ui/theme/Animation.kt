package org.singularux.contacts.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val Duration = 250
private const val Delay = 150
private const val SplitOffset = 8

private val ExitEasing = CubicBezierEasing(0.4F, 0.0F, 1.0F, 1.0F)
private val EnterEasing = CubicBezierEasing(0.0F, 0.0F, 0.2F, 1.0F)

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(
            durationMillis = Duration,
            delayMillis = Delay,
            easing = EnterEasing
        ),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = Duration,
            delayMillis = Delay,
            easing = EnterEasing
        )
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(
            durationMillis = Duration,
            easing = ExitEasing
        ),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = Duration,
            easing = ExitEasing
        )
    )
}
