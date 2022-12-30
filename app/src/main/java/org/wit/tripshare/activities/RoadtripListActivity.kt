package org.wit.tripshare.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.tripshare.R
import org.wit.tripshare.adapters.RoadtripAdapter
import org.wit.tripshare.adapters.RoadtripListener
import org.wit.tripshare.databinding.ActivityRoadtripListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel

class RoadtripListActivity : AppCompatActivity(), RoadtripListener/*, MultiplePermissionsListener*/ {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRoadtripListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoadtripListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        loadRoadtrips()
        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_roadtrip, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RoadtripActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRoadtripClick(roadtrip: RoadtripModel) {
        val launcherIntent = Intent(this, RoadtripActivity::class.java)
        launcherIntent.putExtra("roadtrip_edit", roadtrip)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onRoadtripButtonClick(roadtrip: RoadtripModel) {
        val launcherIntent = Intent(this, DestinationListActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadRoadtrips() }
    }

    private fun loadRoadtrips() {
        showRoadtrips(app.roadtrips.findAll())
    }

    fun showRoadtrips (roadtrips: List<RoadtripModel>) {
        binding.recyclerView.adapter = RoadtripAdapter(roadtrips, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
    //test
}