package org.wit.tripshare.views.roadtriplist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.tripshare.R
import org.wit.tripshare.adapters.RoadtripAdapter
import org.wit.tripshare.adapters.RoadtripListener
import org.wit.tripshare.databinding.ActivityRoadtripListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel
import org.wit.tripshare.views.destinationlist.DestinationListView

class RoadtripListView : AppCompatActivity(), RoadtripListener/*, MultiplePermissionsListener*/ {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRoadtripListBinding
    lateinit var presenter: RoadtripListPresenter
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoadtripListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = RoadtripListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadRoadtrips()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_roadtrip, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddRoadtrip() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRoadtripClick(roadtrip: RoadtripModel) {
        presenter.doEditRoadtrip(roadtrip)
    }


    override fun onRoadtripButtonClick(roadtrip: RoadtripModel) {
        val launcherIntent = Intent(this, DestinationListView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }


    private fun loadRoadtrips() {
        binding.recyclerView.adapter = RoadtripAdapter(presenter.getRoadtrips(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}