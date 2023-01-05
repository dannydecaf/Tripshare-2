package org.wit.tripshare.ui.roadtriplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.tripshare.models.RoadtripManager
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber

class RoadtripListViewModel : ViewModel() {

    private val roadtripsList = MutableLiveData<List<RoadtripModel>>()

    val observableRoadtripsList: LiveData<List<RoadtripModel>>
        get() = roadtripsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    init {
        load()
    }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, roadtripsList)
            Timber.i("Roadtrip List Load Success : ${roadtripsList.value.toString()}")
        } catch (e: Exception) {
            Timber.i("Roadtrip List Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid, id)
            Timber.i("Roadtrip List Delete Success")
        } catch (e: Exception) {
            Timber.i("Roadtrip List Delete Error : $e.message")
        }
    }
    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(roadtripsList)
            Timber.i("List Roadtrips LoadAll Success : ${roadtripsList.value.toString()}")
        } catch (e: Exception) {
            Timber.i("List Roadtrips LoadAll Error : $e.message")
        }
    }
}