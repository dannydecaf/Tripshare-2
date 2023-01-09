package org.wit.tripshare.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface RoadtripStore {
    fun findAll(roadtripsList:
                MutableLiveData<List<RoadtripModel>>)
    fun findAll(userid:String,
                roadtripsList: MutableLiveData<List<RoadtripModel>>)
    fun findById(userid:String, roadtripid: String, roadtrip: MutableLiveData<RoadtripModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, roadtrip: RoadtripModel)
    fun delete(userid:String, roadtripid: String)
    fun update(userid: String, roadtripid: String, roadtrip: RoadtripModel)
}