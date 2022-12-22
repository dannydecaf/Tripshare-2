package org.wit.tripshare.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DestinationModel(var id: Long = 0,
                            var title: String = "",
                            var description: String = "",
                            var pros: String = "",
                            var cons: String = "",
                            var dateArrived: String = "",
                            var rating: Float = 0.0f,
                            var image: Uri = Uri.EMPTY,
                            var lat : Double = 0.0,
                            var lng: Double = 0.0,
                            var zoom: Float = 0f)  : Parcelable

@Parcelize
data class DestinationLocation(var lat: Double = 0.0,
                               var lng: Double = 0.0,
                               var zoom: Float = 0f) : Parcelable