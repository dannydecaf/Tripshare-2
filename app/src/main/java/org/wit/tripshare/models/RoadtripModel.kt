package org.wit.tripshare.models

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RoadtripModel(var uid: String? = "",
                         @SerializedName("roadtripname")
                         var roadtripTitle: String = "",
                         var roadtripDescription: String = "",
                         var roadtripHighlights: String = "",
                         var roadtripLowlights: String = "",
                         var roadtripDates: String = "",
                         var roadtripRating: Float = 0.0f,
                         var roadtripImage: Uri = Uri.EMPTY) : Parcelable








