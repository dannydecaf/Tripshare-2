package org.wit.tripshare.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.wit.tripshare.models.RoadtripModel
import org.wit.tripshare.models.RoadtripStore
import timber.log.Timber

object FirebaseDBManager : RoadtripStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(roadtripsList: MutableLiveData<List<RoadtripModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, roadtripsList: MutableLiveData<List<RoadtripModel>>) {
        database.child("user-roadtrips").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Roadtrtip error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<RoadtripModel>()
                    val children = snapshot.children
                    children.forEach {
                        val roadtrip = it.getValue(RoadtripModel::class.java)
                        localList.add(roadtrip!!)
                    }
                    database.child("user-roadtrips").child(userid)
                        .removeEventListener(this)

                    roadtripsList.value = localList
                }
            })
    }

    override fun findById(userid: String, roadtripid: String, roadtrip: MutableLiveData<RoadtripModel>) {
        database.child("user-roadtrips").child(userid)
            .child(roadtripid).get().addOnSuccessListener {
                roadtrip.value = it.getValue(RoadtripModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, roadtrip: RoadtripModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("roadtrips").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        roadtrip.uid = key
        val roadtripValues = roadtrip.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/roadtrips/$key"] = roadtripValues
        childAdd["/user-roadtrips/$uid/$key"] = roadtripValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, roadtripid: String) {
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/roadtrips/$roadtripid"] = null
        childDelete["/user-roadtrips/$userid/$roadtripid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, roadtripid: String, roadtrip: RoadtripModel) {
        val roadtripValues = roadtrip.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["roadtrips/$roadtripid"] = roadtripValues
        childUpdate["user-roadtrips/$userid/$roadtripid"] = roadtripValues

        database.updateChildren(childUpdate)
    }
}
