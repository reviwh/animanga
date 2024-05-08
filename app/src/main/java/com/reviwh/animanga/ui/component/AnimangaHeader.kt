package com.reviwh.animanga.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reviwh.animanga.R
import com.reviwh.animanga.utils.getFormattedInt

@Composable
fun AnimangaHeader(
    image: String?,
    score: Any?,
    rank: Int?,
    popularity: Int?,
    members: Int?,
    favorites: Int?,
    modifier: Modifier = Modifier
) {
    val rightAlign = Alignment.End

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = image,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = modifier
                .size(184.dp, 240.dp)
                .padding(start = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
        Column(
            horizontalAlignment = rightAlign,
        ) {
            Text(
                text = "Score",
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.padding(end = 16.dp),
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(end = 16.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.grade_filled_24px),
                    contentDescription = score.toString(),
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            DataField(
                title = "Rank",
                value = "#${rank ?: " ?"}",
                alignment = rightAlign
            )
            DataField(
                title = "Popularity",
                value = "#${popularity ?: " ?"}",
                alignment = rightAlign
            )
            DataField(
                title = "Members",
                value = members?.getFormattedInt(),
                alignment = rightAlign
            )
            DataField(
                title = "Favorites",
                value = favorites?.getFormattedInt(),
                alignment = rightAlign,
            )
        }
    }
}