package dam.sequeros.klassroom.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    fun pickProfileImage() {
        viewModelScope.launch {
            val imageFile = FileKit.openFilePicker(type = FileKitType.Image)
            if (imageFile != null) {
                val bytes: ByteArray = imageFile.readBytes()
            }
        }
    }
}