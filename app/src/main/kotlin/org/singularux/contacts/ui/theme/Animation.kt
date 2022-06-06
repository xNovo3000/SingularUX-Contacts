package org.singularux.contacts.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val Millis = 500
private const val SplitOffset = 4

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideInHorizontally(
        animationSpec = tween(durationMillis = Millis),
        initialOffsetX = { it }
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutHorizontally(
        animationSpec = tween(durationMillis = Millis),
        targetOffsetX = { - it / SplitOffset }
    )
}

@ExperimentalAnimationApi
val PopEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideInHorizontally(
        animationSpec = tween(durationMillis = Millis),
        initialOffsetX = { - it / SplitOffset }
    )
}

@ExperimentalAnimationApi
val PopExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutHorizontally(
        animationSpec = tween(durationMillis = Millis),
        targetOffsetX = { it }
    )
}