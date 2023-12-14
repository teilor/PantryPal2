package br.com.teilorsoares.pantrypal.presentation.screens.main

import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import br.com.teilorsoares.pantrypal.presentation.screens.main.navigation.MainNavigationEvent
import br.com.teilorsoares.pantrypal.presentation.screens.test.deleteditems.DeletedItemsActivity
import br.com.teilorsoares.pantrypal.presentation.screens.test.Activity02
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent
import kotlinx.coroutines.launch
import logcat.logcat
import java.io.File
import java.util.*

fun MainActivity.handleAction(action: MainUiAction) {
    when (action) {
        is MainUiAction.NavigateTo -> navigateTo(action.route)
        is MainUiAction.TakePicture -> takePicture(action.source)
        is MainUiAction.NavigateToActivity -> navigateToActivity(action.activities)
        is MainUiAction.FinishScreen -> finish()
    }
}

private fun MainActivity.navigateTo(route: String) {
    logcat("Route") { "navigateTo: $route" }
    navController.navigate(route)
}

private fun MainActivity.takePicture(source: PictureEvent.PictureSource) {
    when (source) {
        PictureEvent.PictureSource.CAMERA -> {
            lifecycleScope.launch {
                getFileUri().let { uri ->
                    latestUri = uri
                    takeImageResult.launch(uri)
                }
            }
        }

        PictureEvent.PictureSource.GALLERY -> {
            selectImageFromGalleryResult.launch("image/*")
        }
    }
}

private fun MainActivity.navigateToActivity(activities: MainNavigationEvent.Activities) {
    val intent = when (activities) {
        MainNavigationEvent.Activities.Activity01 -> {
            Intent(this, DeletedItemsActivity::class.java)
        }
        MainNavigationEvent.Activities.Activity02 -> {
            Intent(this, Activity02::class.java)
        }
    }

    startActivity(intent)
}

private fun MainActivity.getFileUri(): Uri {
    return FileProvider.getUriForFile(
        applicationContext,
        "br.com.teilorsoares.pantrypal.provider",
        File(applicationContext.filesDir, UUID.randomUUID().toString())
    )
}