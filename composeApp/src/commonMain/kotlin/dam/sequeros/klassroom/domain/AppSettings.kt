package dam.sequeros.klassroom.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppSettings() {
    //TAMAÑO DE LA PANTALLA PARA DISEÑO RESPONSIVE
    private val _screenSize = MutableStateFlow(true)
    val screenSize = _screenSize.asStateFlow()

    fun setScreenSize(value: Boolean){
        _screenSize.update { value }
        println(if (value) "PC LAYOUT" else "MOBIL LAYOUT")
    }

    //TEMA DE LA APLICACIÓN
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode = _isDarkMode.asStateFlow()

    //LENGUAJE
    fun setTheme(value: Boolean) {
        _isDarkMode.update { value }
    }
}
