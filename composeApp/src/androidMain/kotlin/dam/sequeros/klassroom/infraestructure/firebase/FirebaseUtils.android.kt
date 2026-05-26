package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.domain.repository.IUtilsRepository
import dev.gitlive.firebase.firestore.where
import io.ktor.client.*

actual class FirebaseUtilsRepository actual constructor(
    private val sessionManager: SessionManager,
    client: HttpClient
) : IUtilsRepository {
    actual override suspend fun getTeachers(): List<UserAccount> {
        val result = sessionManager.db.collection("teachers").get()
        val list = result.documents.map { it.data<UserAccount>() }.filter { it.role == UserRole.TEACHER }
        return list
    }

    actual override suspend fun getCourseByTeacher(id: String): List<Course> {
        var result = sessionManager.db.collection("subjects").where("teacherId", equalTo = id).get()
        val subjectList = result.documents.map { it.data<Subject>() }

        result = sessionManager.db.collection("courses").get()
        val courseList = result.documents.map { course ->
            val course = course.data<Course>()
            Course(
                id = course.id,
                name = course.name,
                subjects = subjectList.filter {
                    it.curseId == course.id
                },
            )
        }

        return courseList
    }
}