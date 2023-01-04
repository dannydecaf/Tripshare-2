package org.wit.tripshare.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RoadtripModel(var id: Long = 0,
                         val paymentmethod: String = "N/A",
                         val amount: Int = 0) : Parcelable








