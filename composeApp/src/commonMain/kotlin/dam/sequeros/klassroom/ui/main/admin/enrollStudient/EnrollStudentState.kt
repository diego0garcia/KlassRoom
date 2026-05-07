package dam.sequeros.klassroom.ui.main.admin.enrollStudient

data class EnrollStudentState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dni: String = "",
    val curseId: String = "",

    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val isValid:Boolean = false,

    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val dniError: String? = null,
    val curseIdError: String? = null,

    val errorMessage: String? = null
)
