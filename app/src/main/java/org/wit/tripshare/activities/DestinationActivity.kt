package org.wit.tripshare.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.ActivityDestinationBinding
import org.wit.tripshare.helpers.showImagePicker
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.DestinationLocation
import org.wit.tripshare.models.DestinationModel
import org.wit.tripshare.ui.map.MapActivity
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class DestinationActivity : AppCompatActivity() {

    private lateinit var arrivedDatePicker : TextView
    private lateinit var btnDateArrived : MaterialButton

    private lateinit var binding: ActivityDestinationBinding
    var destination = DestinationModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        arrivedDatePicker = findViewById(R.id.dateArrived)
        btnDateArrived = findViewById(R.id.btnDateArrived)

        btnDateArrived.setOnClickListener {
            val datePickerRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date")
                .setSelection(
                    androidx.core.util.Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()
            datePickerRange.show(supportFragmentManager, "date_picker")

            datePickerRange.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
                arrivedDatePicker.text = "${simpleDateFormat.format(it.first)} to ${simpleDateFormat.format(it.second)}"
            }
        }

        app = application as MainApp

        i("Destination Activity started...")

        if (intent.hasExtra("destination_edit")) {
            edit = true
            destination = intent.extras?.getParcelable("destination_edit")!!
            binding.destinationTitle.setText(destination.title)
            binding.destinationDescription.setText(destination.description)
            binding.destinationPros.setText(destination.pros)
            binding.destinationCons.setText(destination.cons)
            binding.btnDateArrived.setText(destination.dateArrived)
            binding.destinationRatingBarInput.setOnRatingBarChangeListener {ratingBar, rating, fromUser -> destination.rating = rating;}
            binding.btnDestinationAdd.setText(R.string.save_destination)
            Picasso.get()
                .load(destination.image)
                .into(binding.destinationImage)
            if (destination.image != Uri.EMPTY) {
                binding.chooseDestinationImage.setText(R.string.change_destination_image)
            }
        }

        binding.btnDestinationAdd.setOnClickListener() {
            destination.title = binding.destinationTitle.text.toString()
            destination.description = binding.destinationDescription.text.toString()
            destination.pros = binding.destinationPros.text.toString()
            destination.cons = binding.destinationCons.text.toString()
            destination.dateArrived = binding.dateArrived.text.toString()
            destination.rating = binding.destinationRatingBarInput.rating
            if (destination.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_destination_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.destinationsStore.update(destination.copy())
                } else {
                    app.destinationsStore.create(destination.copy())
                }
            }
            i("add Button Pressed: $destination")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseDestinationImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.destinationLocation.setOnClickListener {
            val location = DestinationLocation(52.245696, -7.139102, 15f)
            if (destination.zoom != 0f) {
                location.lat =  destination.lat
                location.lng = destination.lng
                location.zoom = destination.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_destination, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.destinationsStore.delete(destination)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            destination.image = result.data!!.data!!
                            Picasso.get()
                                .load(destination.image)
                                .into(binding.destinationImage)
                            binding.chooseDestinationImage.setText(R.string.change_destination_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<DestinationLocation>("location")!!
                            i("Location == $location")
                            destination.lat = location.lat
                            destination.lng = location.lng
                            destination.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}