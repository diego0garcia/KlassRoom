package dam.sequeros.klassroom.infraestructure.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FirestoreResponse(val documents: List<FirestoreDocument>? = null)

@Serializable
data class FirestoreDocument(val name: String, val fields: Map<String, FirestoreValue>)

@Serializable
data class FirestoreValue(
    val stringValue: String? = null,
    val integerValue: String? = null,
    val booleanValue: Boolean? = null
)

@Serializable
data class FirestoreQueryResponse(
    val document: FirestoreDocument? = null
)

@Serializable
data class RunQueryRequest(
    val structuredQuery: StructuredQuery
)

@Serializable
data class StructuredQuery(
    val from: List<CollectionSelector>,
    val where: Filter
)

@Serializable
data class CollectionSelector(
    val collectionId: String
)

@Serializable
data class Filter(
    val fieldFilter: FieldFilter
)

@Serializable
data class FieldFilter(
    val field: FieldReference,
    val op: String,
    val value: FirestoreValue // Reutilizamos tu clase FirestoreValue
)

@Serializable
data class FieldReference(
    val fieldPath: String
)