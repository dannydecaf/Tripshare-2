package org.wit.tripshare.views.roadtrip

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.util.Pair
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.ActivityRoadtripBinding
import org.wit.tripshare.models.DestinationModel
import org.wit.tripshare.models.RoadtripModel
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class RoadtripView : AppCompatActivity() {

    private lateinit var startDatePicker: TextView
    private lateinit var btnStartDate: MaterialButton

    private lateinit var binding: ActivityRoadtripBinding
    private lateinit var presenter: RoadtripPresenter
    var roadtrip = RoadtripModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoadtripBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        startDatePicker = findViewById(R.id.roadtripDates)
        btnStartDate = findViewById(R.id.btnStartDate)

        presenter = RoadtripPresenter(this)

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
                startDatePicker.text =
                    "${simpleDateFormat.format(it.first)} to ${simpleDateFormat.format(it.second)}"
            }
        }

        binding.chooseRoadtripImage.setOnClickListener {
            presenter.cacheRoadtrip(
                binding.roadtripTitle.text.toString(),
                binding.roadtripDescription.text.toString(),
                binding.roadtripHighlights.text.toString(),
                binding.roadtripLowlights.text.toString(),
                binding.roadtripDates.text.toString(),
                binding.roadtripRatingBarInput.rating
            )
            presenter.doSelectImage()
        }

    }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_roadtrip, menu)
            val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
            if (presenter.edit) {
                deleteMenu.setVisible(true)
            } else {
                deleteMenu.setVisible(false)
            }
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.btnRoadtripAdd -> {
                    if (binding.roadtripTitle.text.toString().isEmpty()) {
                        Snackbar.make(
                            binding.root,
                            R.string.enter_roadtrip_title,
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                    } else {
                        presenter.doAddOrSave(
                            binding.roadtripTitle.text.toString(),
                            binding.roadtripDescription.text.toString(),
                            binding.roadtripHighlights.text.toString(),
                            binding.roadtripLowlights.text.toString(),
                            binding.roadtripDates.text.toString(),
                            binding.roadtripRatingBarInput.rating
                        )
                    }
                }
                R.id.item_delete -> {
                    presenter.doDelete()
                }
                R.id.item_cancel -> {
                    presenter.doCancel()
                }

            }
            return super.onOptionsItemSelected(item)
        }

        fun showRoadtrip(roadtrip: RoadtripModel) {
            binding.roadtripTitle.setText(roadtrip.roadtripTitle)
            binding.roadtripDescription.setText(roadtrip.roadtripDescription)
            binding.roadtripHighlights.setText(roadtrip.roadtripHighlights)
            binding.roadtripLowlights.setText(roadtrip.roadtripLowlights)
            binding.roadtripDates.setText(roadtrip.roadtripDates)
            binding.roadtripRatingBarInput.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                roadtrip.roadtripRating = rating;
            }
            binding.btnRoadtripAdd.setText(R.string.save_roadtrip)

            Picasso.get()
                .load(roadtrip.roadtripImage)
                .into(binding.roadtripImage)
            if (roadtrip.roadtripImage != Uri.EMPTY) {
                binding.chooseRoadtripImage.setText(R.string.change_destination_image)
            }
        }

        fun updateImage(image: Uri) {
            i("Image Updated")
            Picasso.get()
                .load(image)
                .into(binding.roadtripImage)
            binding.chooseRoadtripImage.setText(R.string.change_destination_image)
        }
    }