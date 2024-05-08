package com.reviwh.animanga.ui.navigation

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem(
    val title: String,
    val icon: Painter,
    val activeIcon: Painter,
    val screen: Screen
)