package si.eestec.challenge.georgeai.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TaskModel(
    @SerialName("Title") val title: String,
    @SerialName("Description") val description: String,
    @SerialName("Date") val date: String,
    @SerialName("Time") val time: String
    ): Parcelable
