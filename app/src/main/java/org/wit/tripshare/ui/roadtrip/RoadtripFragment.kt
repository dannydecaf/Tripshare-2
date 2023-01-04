package org.wit.tripshare.ui.roadtrip

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.util.Pair
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.FragmentRoadtripBinding
import org.wit.tripshare.helpers.showImagePicker
import org.wit.tripshare.main.MainApp
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class RoadtripFragment : Fragment() {

    private lateinit var startDatePicker : TextView
    private lateinit var btnStartDate : MaterialButton

    private lateinit var binding: FragmentRoadtripBinding
    var roadtrip = RoadtripModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentRoadtripBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        startDatePicker = findViewById(R.id.roadtripDates)
        btnStartDate = findViewById(R.id.btnStartDate)

        btnStartDate.setOnClickListener {
            val datePickerRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()
            datePickerRange.show(supportFragmentManager, "date_picker")

            datePickerRange.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
                startDatePicker.text = "${simpleDateFormat.format(it.first)} to ${simpleDateFormat.format(it.second)}"
            }
        }

        app = application as MainApp

        i("Roadtrip Activity started...")

        if (intent.hasExtra("roadtrip_edit")) {
            edit = true
            roadtrip = intent.extras?.getParcelable("roadtrip_edit")!!
            binding.roadtripTitle.setText(roadtrip.roadtripTitle)
            binding.roadtripDescription.setText(roadtrip.roadtripDescription)
            binding.roadtripHighlights.setText(roadtrip.roadtripHighlights)
            binding.roadtripLowlights.setText(roadtrip.roadtripLowlights)
            binding.btnStartDate.setText(roadtrip.roadtripDates)
            binding.roadtripRatingBarInput.setOnRatingBarChangeListener {roadtripRatingBar, roadtripRating, fromUser -> roadtrip.roadtripRating = roadtripRating;}
            binding.btnRoadtripAdd.setText(R.string.save_roadtrip)
            Picasso.get()
                .load(roadtrip.roadtripImage)
                .into(binding.roadtripImage)
            if (roadtrip.roadtripImage != Uri.EMPTY) {
                binding.chooseRoadtripImage.setText(R.string.change_roadtrip_image)
            }
        }

        binding.btnRoadtripAdd.setOnClickListener() {
            roadtrip.roadtripTitle = binding.roadtripTitle.text.toString()
            roadtrip.roadtripDescription = binding.roadtripDescription.text.toString()
            roadtrip.roadtripHighlights = binding.roadtripHighlights.text.toString()
            roadtrip.roadtripLowlights = binding.roadtripLowlights.text.toString()
            roadtrip.roadtripDates = binding.roadtripDates.text.toString()
            roadtrip.roadtripRating = binding.roadtripRatingBarInput.rating
            if (roadtrip.roadtripTitle.isEmpty()) {
                Snackbar.make(it,R.string.enter_roadtrip_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.roadtrips.update(roadtrip.copy())
                } else {
                    app.roadtrips.create(roadtrip.copy())
                }
            }
            i("add Button Pressed: $roadtrip")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseRoadtripImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.chooseRoadtripImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }


        registerImagePickerCallback()
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        startDatePicker.setText(sdf.format(myCalendar.time))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_roadtrip, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.roadtrips.delete(roadtrip)
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
                            roadtrip.roadtripImage = result.data!!.data!!
                            Picasso.get()
                                .load(roadtrip.roadtripImage)
                                .into(binding.roadtripImage)
                            binding.chooseRoadtripImage.setText(R.string.change_roadtrip_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}