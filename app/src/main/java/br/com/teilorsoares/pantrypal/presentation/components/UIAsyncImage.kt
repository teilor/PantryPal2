package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import logcat.asLog
import logcat.logcat

@Composable
fun UIAsyncImage(
    model: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    size: Int = 200
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .size(size)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        error = { logcat("Coil") { "model: $model " + it.result.throwable.asLog() } },
        loading = { Box { CircularProgressIndicator() } },
        modifier = modifier
    )
}