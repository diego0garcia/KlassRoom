package dam.sequeros.klassroom.ui.main.admin.addcurse

import dam.sequeros.klassroom.domain.model.Subject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CurseState(
    val id: String = Uuid.random().toString(),
    val name: String = "",
    val subjects: List<Subject> = emptyList(),

    val isLoading: Boolean = false,
    val isAddedSuccess: Boolean = false,
    val isValid: Boolean = false,

    val nameError: String? = null,

    val errorMessage: String? = null
)
