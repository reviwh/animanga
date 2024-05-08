package com.reviwh.animanga.ui.screen.animedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reviwh.animanga.R
import com.reviwh.animanga.model.AnimeData
import com.reviwh.animanga.ui.ViewModelFactory
import com.reviwh.animanga.ui.common.UiState
import com.reviwh.animanga.ui.component.AnimangaHeader
import com.reviwh.animanga.ui.component.DataField
import com.reviwh.animanga.ui.component.ErrorView
import com.reviwh.animanga.ui.component.Loading

@Composable
fun AnimeDetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel: AnimeDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        is UiState.Loading -> {
            Loading()
            viewModel.getAnime(id)
        }

        is UiState.Success -> {
            val (anime) = (uiState.value as UiState.Success<AnimeDetailState>).data
            AnimeDetailScreenContent(
                animeData = anime, navigateBack = navigateBack, modifier = modifier
            )
        }

        is UiState.Error -> ErrorView(message = (uiState.value as UiState.Error).errorMessage)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimeDetailScreenContent(
    animeData: AnimeData?,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (animeData == null) return ErrorView(
        message = stringResource(R.string.anime_404)
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                stringResource(R.string.title_anime_details),
            )
        }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.desc_back),
                )
            }
        })
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier.padding(paddingValues),
        ) {
            item {
                AnimangaHeader(
                    image = animeData.images?.jpg?.imageUrl,
                    score = animeData.score,
                    rank = animeData.rank,
                    popularity = animeData.popularity,
                    members = animeData.members,
                    favorites = animeData.favorites,
                )
                Text(
                    text = animeData.title ?: "??",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${animeData.type ?: "?"}, ${animeData.year ?: "??"}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = animeData.status ?: "?",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = animeData.duration ?: "?",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            if (animeData.genres?.isNotEmpty() == true) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        animeData.genres.forEachIndexed { index, genre ->
                            if (index < 4) {
                                Text(
                                    text = genre?.name ?: "?",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelLarge,
                                )
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    text = animeData.synopsis ?: "??",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                )
            }
            item {
                DataField(
                    title = "English",
                    value = animeData.titleEnglish ?: animeData.title,
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                gridInfo(animeData = animeData, modifier = modifier)
            }
        }
    }
}

@Composable
private fun gridInfo(
    animeData: AnimeData,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp)
    ) {
        Column {
            DataField(title = "Source", value = animeData.source)
            if (animeData.studios?.isNotEmpty() == true) {
                DataField(title = "Studios", value = animeData.studios.first()?.name)
            }
            DataField(title = "Rating", value = animeData.rating)
            Spacer(modifier.height(32.dp))
        }
        Column {
            DataField(
                title = "Season",
                value = "${animeData.season?.replaceFirstChar { it.uppercase() } ?: "?"} ${animeData.year ?: "?"}",
            )
            DataField(
                title = "Aired",
                value = animeData.aired?.string ?: "?",
            )
            if (animeData.licensors?.isNotEmpty() == true) {
                DataField(title = "Licensor", value = animeData.licensors.first()?.name)
            }
            Spacer(modifier.height(32.dp))
        }

    }
}