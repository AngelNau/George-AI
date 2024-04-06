package si.eestec.challenge.georgeai.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(
    @SerialName("Title") val title: String,
    @SerialName("Description") val description: String,
    @SerialName("Image") val image: String?,
    @SerialName("TimeFrom") val from: String,
    @SerialName("TimeTo") val to: String
    )
