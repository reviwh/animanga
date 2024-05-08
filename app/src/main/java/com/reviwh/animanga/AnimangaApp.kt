package com.reviwh.animanga

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.reviwh.animanga.ui.navigation.NavigationItem
import com.reviwh.animanga.ui.navigation.Screen
import com.reviwh.animanga.ui.screen.about.AboutScreen
import com.reviwh.animanga.ui.screen.anime.AnimeScreen
import com.reviwh.animanga.ui.screen.animedetail.AnimeDetailScreen
import com.reviwh.animanga.ui.screen.manga.MangaScreen
import com.reviwh.animanga.ui.screen.mangadetail.MangaDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimangaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "anime",
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Anime.route) {
                AnimeScreen(
                    navigateToDetail = {
                        navController.navigate(
                            Screen.AnimeDetail.createRoute(it)
                        )
                    },
                )
            }
            composable(Screen.Manga.route) {
                MangaScreen(
                    navigateToDetail = {
                        navController.navigate(
                            Screen.MangaDetail.createRoute(it)
                        )
                    },
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.AnimeDetail.route,
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                AnimeDetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.MangaDetail.route,
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                MangaDetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }


}

@Composable
private fun BottomBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier, windowInsets = NavigationBarDefaults.windowInsets
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(R.string.title_anime),
                icon = painterResource(id = R.drawable.live_tv_24px),
                activeIcon = painterResource(id = R.drawable.live_tv_filled_24px),
                screen = Screen.Anime,
            ),
            NavigationItem(
                title = stringResource(R.string.title_manga),
                icon = painterResource(id = R.drawable.manga_24px),
                activeIcon = painterResource(id = R.drawable.manga_filled_24px),
                screen = Screen.Manga,
            ),
            NavigationItem(
                title = stringResource(R.string.title_about),
                icon = painterResource(id = R.drawable.info_24px),
                activeIcon = painterResource(id = R.drawable.info_filled_24px),
                screen = Screen.About,
            ),
        )
        navigationItem.map { item ->
            val selected = currentRoute == item.screen.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        painter = if (selected) item.activeIcon else item.icon,
                        contentDescription = item.title,
                    )
                },
            )
        }
    }
}