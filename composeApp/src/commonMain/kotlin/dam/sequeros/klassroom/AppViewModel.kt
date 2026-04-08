package dam.sequeros.klassroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.domain.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class AppViewModel(
    private val sessionManager: SessionManager,
) : ViewModel() {

}