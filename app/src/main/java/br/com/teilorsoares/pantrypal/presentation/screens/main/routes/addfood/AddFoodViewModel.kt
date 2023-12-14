package br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewModelScope
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.domain.model.Location
import br.com.teilorsoares.pantrypal.domain.usecase.food.InsertFoodUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.location.GetLocationsUseCase
import br.com.teilorsoares.pantrypal.presentation.arch.ViewModel
import br.com.teilorsoares.pantrypal.presentation.util.PictureEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import logcat.asLog
import logcat.logcat
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

private const val TAG = "AddFoodViewModel"

@OptIn(ExperimentalMaterial3Api::class)
class AddFoodViewModel(
    private val insertFoodUseCase: InsertFoodUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<AddFoodUiState, AddFoodUiAction>(AddFoodUiState()) {
    private var job: Job? = null
    private var source: PictureEvent.PictureSource? = null

    private val locations: MutableList<Location> = mutableListOf()

    init {
        PictureEvent.onSelectPicture += ::onSelectPicture
    }

    private fun onSelectPicture(data : Pair<PictureEvent.PictureSource, Uri>) {
        viewModelScope.launch {
            val (source, uri) = data
            this@AddFoodViewModel.source = source

            setState { state ->
                state.copy(photo =  uri)
            }
        }
    }


    fun started() {
        job?.cancel()
        job = viewModelScope.launch {
            getLocationsUseCase()
                .flowOn(ioDispatcher)
                .collect { locations -> handleLocations(locations) }
        }
    }

    private suspend fun handleLocations(locations: List<Location>) {
        logcat(TAG) { "handleLocations: $locations" }

        this.locations.clear()
        this.locations.addAll(locations)

        val mappedLocations = locations.map { it.name }.toMutableList()
        mappedLocations.add("Adicionar")

        setState { state -> state.copy(locations = mappedLocations) }
    }

    fun clickedTakePhoto() {
        viewModelScope.launch {
            setState { state -> state.copy(showTakePhotoBottomSheet = true) }
        }
    }

    fun clickedDismissTakePhotoBottomSheet() {
        viewModelScope.launch {
            setState { state -> state.copy(showTakePhotoBottomSheet = false) }
        }
    }

    fun clickedTakePhotoWithCamera() {
        viewModelScope.launch {
            setState { state -> state.copy(showTakePhotoBottomSheet = false) }
            sendAction { AddFoodUiAction.TakePhoto(PictureEvent.PictureSource.CAMERA) }
        }
    }

    fun clickedTakePhotoWithGallery() {
        viewModelScope.launch {
            setState { state -> state.copy(showTakePhotoBottomSheet = false) }
            sendAction { AddFoodUiAction.TakePhoto(PictureEvent.PictureSource.GALLERY) }
        }
    }

    fun clickedBack() {
        viewModelScope.launch {
            sendAction { AddFoodUiAction.NavigateToBack }
            setState { _ -> AddFoodUiState() }
        }
    }

    fun changedName(name: String) {
        viewModelScope.launch {
            setState { state -> state.copy(name = name) }
        }
    }

    fun changedExpirationDate(expirationDate: Long?) {
        viewModelScope.launch {
            setState { state -> state.copy(expirationDate = expirationDate) }
        }
    }

    fun changedQuantity(quantity: String) {
        viewModelScope.launch {
            setState { state -> state.copy(quantity = quantity) }
        }
    }

    fun changedLocation(location: String) {
        viewModelScope.launch {
            if (location == "Adicionar") {
                sendAction { AddFoodUiAction.NavigateToAddLocationScreen }
            } else {
                setState { state -> state.copy(location = location) }
            }
        }
    }

    fun changedMeal(meal: Food.Meal) {
        viewModelScope.launch {
            setState { state -> state.copy(meal = meal) }
        }
    }

    fun clickedSave(context: Context) {
        viewModelScope.launch {
            try {
                val state = state.value

                if (
                    state.name.isEmpty() ||
                    state.expirationDate == null ||
                    state.quantity.isEmpty() ||
                    state.location.isNullOrBlank() ||
                    state.meal == null ||
                    state.photo == null
                ) {
                    sendAction { AddFoodUiAction.MissingFields }
                    return@launch
                }

                val photo = when (source) {
                    PictureEvent.PictureSource.CAMERA -> {
                        state.photo?.toString() ?: throw Exception("Photo is null")
                    }
                    PictureEvent.PictureSource.GALLERY -> {
                        readContentToFile(context, state.photo) ?.absolutePath ?: throw Exception("Photo is null")
                    }
                    else -> {
                        throw Exception("Photo is null")
                    }
                }

                insertFoodUseCase(
                    Food(
                        id = UUID.randomUUID().toString(),
                        name = state.name,
                        photo = photo,
                        createdDate = System.currentTimeMillis(),
                        expiryDate = state.expirationDate + 12 * 60 * 60 * 1000,
                        deletedDate = null,
                        location = locations.first { it.name == state.location },
                        meal = state.meal,
                        quantity = state.quantity.toLong(),
                        consumedOrDiscarded = false,
                        status = Food.Status.OK
                    )
                )
                sendAction { AddFoodUiAction.NavigateToBack }
                setState { _ -> AddFoodUiState() }
            } catch (e: Exception) {
                logcat(TAG) { e.asLog() }
                sendAction { AddFoodUiAction.ShowError }
            }
        }
    }

    private fun readContentToFile(context: Context, uri: Uri): File? {
        val file: File? = getDisplayName(context, uri)?.let { File(context.cacheDir, it) }
        context.contentResolver.openInputStream(uri).use { `in` ->
            if (`in` == null) {
                return null
            }

            FileOutputStream(file, false).use { out ->
                val buffer = ByteArray(1024)
                var len: Int
                while (`in`.read(buffer).also { len = it } != -1) {
                    out.write(buffer, 0, len)
                }
                return file
            }
        }
    }

    private fun getDisplayName(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        context.contentResolver.query(uri, projection, null, null, null).use { cursor ->
            if (cursor == null) return null
            val columnIndex: Int =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
        // If the display name is not found for any reason, use the Uri path as a fallback.
        return uri.path
    }

    override fun onCleared() {
        super.onCleared()
        PictureEvent.onSelectPicture -= ::onSelectPicture
    }
}