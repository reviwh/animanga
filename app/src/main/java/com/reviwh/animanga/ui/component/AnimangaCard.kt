package com.reviwh.animanga.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reviwh.animanga.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimangaCard(
    title: String,
    image: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(8.dp)
    ) {
        ElevatedCard(
            onClick = onClick,
            modifier = modifier.width(128.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = title,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier.height(128.dp),
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimangaCardPreview() {
    AnimangaCard(
        title = "Lorem ipsum",
        image = "",
        onClick = {}
    )
}