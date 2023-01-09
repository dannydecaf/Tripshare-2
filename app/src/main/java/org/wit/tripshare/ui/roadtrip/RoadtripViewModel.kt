package org.wit.tripshare.ui.roadtrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.wit.tripshare.firebase.FirebaseDBManager
import org.wit.tripshare.firebase.FirebaseImageManager
import org.wit.tripshare.models.RoadtripModel

class RoadtripViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addRoadtrip(
        firebaseUser: MutableLiveData<FirebaseUser>,
        roadtrip: RoadtripModel
    ) {
        status.value = try {
            roadtrip.profilepic = FirebaseImageManager.imageUri.value.toString()
            FirebaseDBManager.create(firebaseUser, roadtrip)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}