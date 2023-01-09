package org.wit.tripshare.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class RoadtripModel(var uid: String? = "",
                         var roadtripTitle: String = "",
                         var roadtripDescription: String = "",
                         var roadtripHighlights: String = "",
                         var roadtripLowlights: String = "",
                         var roadtripDates: String = "",
                         var roadtripRating: Float = 0.0f,
                         var roadtripImage: Uri = Uri.EMPTY,
                         val email: String = "joe@bloggs.com") : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "roadtripTitle" to roadtripTitle,
            "roadtripDescription" to roadtripDescription,
            "roadtripHighlights" to roadtripHighlights,
            "roadtripLowlights" to roadtripLowlights,
            "roadtripDates" to roadtripDates,
            "roadtripRating" to roadtripRating,
            "roadtripImage" to roadtripImage,
            "email" to email,
        )
    }
}
















