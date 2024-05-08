package com.reviwh.animanga.ui.screen.anime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.reviwh.animanga.R
import com.reviwh.animanga.model.AnimeData
import com.reviwh.animanga.model.GenresItem
import com.reviwh.animanga.ui.ViewModelFactory
import com.reviwh.animanga.ui.common.UiState
import com.reviwh.animanga.ui.component.AnimangaCard
import com.reviwh.animanga.ui.component.ErrorView
import com.reviwh.animanga.ui.component.Loading
import com.reviwh.animanga.utils.getSeason
import java.util.Calendar

@Composable
fun AnimeScreen(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel: AnimeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        is UiState.Loading -> {
            Loading()
            viewModel.getAnime()
        }

        is UiState.Success -> {
            val (animeList) = (uiState.value as UiState.Success<AnimeState>).data

            AnimeScreenContent(
                animeDataList = animeList, navigateToDetail = navigateToDetail
            )
        }

        is UiState.Error -> ErrorView(message = (uiState.value as UiState.Error).errorMessage)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimeScreenContent(
    animeDataList: List<AnimeData?>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (animeDataList.isEmpty()) return ErrorView(
        message = stringResource(R.string.anime_404)
    )

    val context = LocalContext.current
    val today = Calendar.getInstance()
    val topAnimeList = animeDataList.subList(0, 10)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_anime))
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues)
        ) {
            item {
                Text(
                    text = stringResource(R.string.top_airing_anime),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyRow {
                    item { Spacer(modifier.width(8.dp)) }
                    items(topAnimeList, key = { topAnimeList.indexOf(it) }) { anime ->
                        if (anime != null) AnimangaCard(
                            title = anime.title.toString(),
                            image = anime.images?.jpg?.imageUrl.toString(),
                            onClick = { navigateToDetail(anime.malId ?: 0) },
                        )
                    }
                    item { Spacer(modifier.width(8.dp)) }
                }
                Text(
                    text = "Anime ${today.getSeason(context)}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(
                animeDataList.sortedBy { it?.title },
                key = { animeDataList.indexOf(it) }) { anime ->
                ListItem(
                    leadingContent = {
                        AsyncImage(
                            model = anime?.images?.jpg?.imageUrl,
                            contentDescription = anime?.title,
                            contentScale = ContentScale.Crop,
                            modifier = modifier.size(64.dp)
                        )
                    },
                    headlineText = {
                        Text(
                            text = anime?.title.toString(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    supportingText = {
                        val genres = StringBuilder()
                        anime?.genres?.forEach { genre ->
                            genres.append("${genre?.name}, ")
                        }

                        if (genres.length > 2) genres.deleteCharAt(genres.length - 2)

                        Text(
                            text = genres.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    },
                    modifier = modifier.clickable {
                        navigateToDetail(anime?.malId ?: 0)
                    },
                )
                Divider()
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreenPreview() {

    val dummyAnimeListData = List(30) {
        AnimeData(
            title = "Anime #$it",
            genres = listOf(
                GenresItem(name = "Action"),
                GenresItem(name = "Adventure"),
            ),
        )
    }
    AnimeScreenContent(
        animeDataList = dummyAnimeListData,
        navigateToDetail = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreenWithNoDataPreview() {
    AnimeScreenContent(
        animeDataList = emptyList(),
        navigateToDetail = {},
    )
}
