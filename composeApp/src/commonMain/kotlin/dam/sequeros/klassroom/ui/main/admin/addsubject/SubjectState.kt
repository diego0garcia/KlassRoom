package dam.sequeros.klassroom.ui.main.admin.addsubject

data class SubjectState(
    val name: String = "",
    val teacherId: String = "",
    val weekDay: String = "",
    val startHour: String = "",
    val endHour: String = "",

    val isLoading: Boolean = false,
    val isAddedSuccess: Boolean = false,
    val isValid: Boolean = false,

    val nameError: String? = null,
    val teacherIdError: String? = null,
    val weekDayError: String? = null,
    val startHourError: String? = null,
    val endHourError: String? = null,
    val errorMessage: String? = null
)
