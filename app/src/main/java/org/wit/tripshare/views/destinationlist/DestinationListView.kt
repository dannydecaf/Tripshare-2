package org.wit.tripshare.views.destinationlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.tripshare.R
import org.wit.tripshare.adapters.DestinationAdapter
import org.wit.tripshare.adapters.DestinationListener
import org.wit.tripshare.databinding.ActivityDestinationListBinding
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationModel

class DestinationListView : AppCompatActivity(), DestinationListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityDestinationListBinding
    lateinit var presenter: DestinationListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = DestinationListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadDestinations()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_destination, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddDestination() }
            R.id.item_map -> { presenter.doShowDestinationsMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestinationClick(destination: DestinationModel) {
        presenter.doEditDestination(destination)

    }

    private fun loadDestinations() {
        binding.recyclerView.adapter = DestinationAdapter(presenter.getDestinations(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
