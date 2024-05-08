package com.reviwh.animanga.ui.screen.manga

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reviwh.animanga.R
import com.reviwh.animanga.model.GenresItem
import com.reviwh.animanga.model.MangaData
import com.reviwh.animanga.ui.ViewModelFactory
import com.reviwh.animanga.ui.common.UiState
import com.reviwh.animanga.ui.component.AnimangaCard
import com.reviwh.animanga.ui.component.ErrorView
import com.reviwh.animanga.ui.component.Loading

@Composable
fun MangaScreen(
    navigateToDetail: (Int) -> Unit
) {
    val viewModel: MangaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        is UiState.Loading -> {
            Loading()
            viewModel.getManga()
        }

        is UiState.Success -> {
            val (mangaList) = (uiState.value as UiState.Success<MangaState>).data

            MangaScreenContent(
                mangaDataList = mangaList,
                navigateToDetail = navigateToDetail,
            )
        }

        is UiState.Error -> ErrorView(message = (uiState.value as UiState.Error).errorMessage)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MangaScreenContent(
    mangaDataList: List<MangaData?>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (mangaDataList.isEmpty()) return ErrorView(
        message = stringResource(R.string.manga_404)
    )

    val gridState = rememberLazyStaggeredGridState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.greatest_manga_of_all_time)) })
        },
    ) { paddingValues ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalItemSpacing = 8.dp,
            state = gridState,
            modifier = modifier.padding(paddingValues),
        ) {
            items(mangaDataList, key = { mangaDataList.indexOf(it) }) { manga ->
                if (manga != null) AnimangaCard(
                    title = manga.title.toString(),
                    image = manga.images?.jpg?.imageUrl.toString(),
                    onClick = { navigateToDetail(manga.malId ?: 0) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreenPreview() {
    val dummyMangaListData = List(30) {
        MangaData(
            title = "Manga #$it",
            genres = listOf(
                GenresItem(name = "Action"),
                GenresItem(name = "Adventure"),
            ),
        )
    }
    MangaScreenContent(
        mangaDataList = dummyMangaListData,
        navigateToDetail = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun AnimeScreenWithNoDataPreview() {
    MangaScreenContent(
        mangaDataList = emptyList(),
        navigateToDetail = {},
    )
}
