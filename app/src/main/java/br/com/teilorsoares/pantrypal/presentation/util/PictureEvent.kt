package br.com.teilorsoares.pantrypal.presentation.util

import android.net.Uri

object PictureEvent {
    val onTakePicture = Event<PictureSource>()
    val onSelectPicture = Event<Pair<PictureSource, Uri>>()

    fun takePicture(source: PictureSource) {
        onTakePicture(source)
    }

    fun selectPicture(source: PictureSource, uri: Uri) {
        onSelectPicture(Pair(source, uri))
    }

    enum class PictureSource {
        CAMERA,
        GALLERY
    }
}