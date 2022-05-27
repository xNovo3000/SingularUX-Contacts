package org.singularux.contacts.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val Millis = 150
private const val SplitOffset = 6

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(
            durationMillis = Millis,
            delayMillis = Millis
        ),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = Millis,
            delayMillis = Millis
        )
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(durationMillis = Millis),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(durationMillis = Millis)
    )
}

@ExperimentalAnimationApi
val PopEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(
            durationMillis = Millis,
            delayMillis = Millis
        ),
        initialOffset = { it / SplitOffset }
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = Millis,
            delayMillis = Millis
        )
    )
}

@ExperimentalAnimationApi
val PopExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(durationMillis = Millis),
        targetOffset = { it / SplitOffset }
    ) + fadeOut(
        animationSpec = tween(durationMillis = Millis)
    )
}