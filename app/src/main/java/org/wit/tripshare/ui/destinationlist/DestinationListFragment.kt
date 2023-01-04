package org.wit.tripshare.ui.destinationlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.tripshare.R
import org.wit.tripshare.activities.DestinationActivity
import org.wit.tripshare.activities.DestinationMapsActivity
import org.wit.tripshare.adapters.DestinationAdapter
import org.wit.tripshare.adapters.DestinationListener
import org.wit.tripshare.databinding.FragmentDestinationListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationModel

class DestinationListFragment : Fragment(), DestinationListener/*, MultiplePermissionsListener*/ {

    lateinit var app: MainApp
    private lateinit var binding: FragmentDestinationListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDestinationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadDestinations()

        registerRefreshCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_destination, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, DestinationActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, DestinationMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestinationClick(destination: DestinationModel) {
        val launcherIntent = Intent(this, DestinationActivity::class.java)
        launcherIntent.putExtra("destination_edit", destination)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadDestinations() }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    private fun loadDestinations() {
        showDestinations(app.destinations.findAll())
    }

    fun showDestinations (destinations: List<DestinationModel>) {
        binding.recyclerView.adapter = DestinationAdapter(destinations, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}