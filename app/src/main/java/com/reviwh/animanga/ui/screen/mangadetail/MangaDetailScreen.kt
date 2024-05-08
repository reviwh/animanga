package com.reviwh.animanga.ui.screen.mangadetail

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reviwh.animanga.R
import com.reviwh.animanga.model.MangaData
import com.reviwh.animanga.ui.ViewModelFactory
import com.reviwh.animanga.ui.common.UiState
import com.reviwh.animanga.ui.component.AnimangaHeader
import com.reviwh.animanga.ui.component.DataField
import com.reviwh.animanga.ui.component.ErrorView
import com.reviwh.animanga.ui.component.Loading

@Composable
fun MangaDetailScreen(
    id: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel: MangaDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        is UiState.Loading -> {
            Loading()
            viewModel.getManga(id)
        }

        is UiState.Success -> {
            val (manga) = (uiState.value as UiState.Success<MangaDetailState>).data
            MangaDetailScreenContent(
                mangaData = manga, navigateBack = navigateBack, modifier = modifier
            )
        }

        is UiState.Error -> ErrorView(message = (uiState.value as UiState.Error).errorMessage)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MangaDetailScreenContent(
    mangaData: MangaData?,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (mangaData == null) return ErrorView(
        message = stringResource(R.string.manga_404)
    )

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                stringResource(R.string.title_manga_details),
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
                    image = mangaData.images?.jpg?.imageUrl,
                    score = mangaData.score,
                    rank = mangaData.rank,
                    popularity = mangaData.popularity,
                    members = mangaData.members,
                    favorites = mangaData.favorites,
                )
                Text(
                    text = mangaData.title ?: "??",
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
                        text = mangaData.type ?: "?",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = mangaData.status ?: "?",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "${mangaData.volumes ?: "?"} vol, ${mangaData.chapters ?: "?"} chp",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            if (mangaData.genres?.isNotEmpty() == true) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        mangaData.genres.forEachIndexed { index, genre ->
                            if (index < 4) Text(
                                text = genre?.name ?: "?",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    text = mangaData.synopsis ?: "?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                )
            }
            item {
                DataField(
                    title = "English",
                    value = mangaData.titleEnglish ?: mangaData.title,
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                gridInfo(mangaData = mangaData, modifier = modifier)
            }
        }
    }
}

@Composable
private fun gridInfo(
    mangaData: MangaData,
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
            DataField(title = "Published", value = mangaData.published?.string)
            if (mangaData.serializations?.isNotEmpty() == true) {
                DataField(title = "Serialization", value = mangaData.serializations.first()?.name)
            }
            Spacer(modifier.height(32.dp))
        }
        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (mangaData.authors?.isNotEmpty() == true) {
                Text(
                    text = "Authors",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
                mangaData.authors.forEach { author ->
                    Text(
                        text = author?.name ?: "?",
                        style = MaterialTheme.typography.labelLarge,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
        Spacer(modifier.height(32.dp))

    }
}