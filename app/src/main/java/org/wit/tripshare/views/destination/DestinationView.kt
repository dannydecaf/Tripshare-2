package org.wit.tripshare.views.destination

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.tripshare.R
import org.wit.tripshare.databinding.ActivityDestinationBinding
import org.wit.tripshare.models.DestinationModel
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.util.*

class DestinationView : AppCompatActivity() {

    private lateinit var arrivedDatePicker : TextView
    private lateinit var btnDateArrived : MaterialButton

    private lateinit var binding: ActivityDestinationBinding
    private lateinit var presenter: DestinationPresenter
    var destination = DestinationModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        arrivedDatePicker = findViewById(R.id.dateArrived)
        btnDateArrived = findViewById(R.id.btnDateArrived)

        presenter = DestinationPresenter(this)

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


        binding.chooseDestinationImage.setOnClickListener {
            presenter.cacheDestination(binding.destinationTitle.text.toString(), binding.destinationDescription.text.toString(),
                binding.destinationPros.text.toString(), binding.destinationCons.text.toString(), binding.dateArrived.text.toString(),
            binding.destinationRatingBarInput.rating)
            presenter.doSelectImage()
        }

        binding.destinationLocation.setOnClickListener {
            presenter.cacheDestination(binding.destinationTitle.text.toString(), binding.destinationDescription.text.toString(),
                binding.destinationPros.text.toString(), binding.destinationCons.text.toString(), binding.dateArrived.text.toString(),
                binding.destinationRatingBarInput.rating)
            presenter.doSetLocation()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_destination, menu)
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
            R.id.btnDestinationAdd -> {
                if (binding.destinationTitle.text.toString().isEmpty()) {
                    Snackbar.make(
                        binding.root,
                        R.string.enter_destination_title,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else {
                    presenter.doAddOrSave(
                        binding.destinationTitle.text.toString(),
                        binding.destinationDescription.text.toString(),
                        binding.destinationPros.text.toString(),
                        binding.destinationCons.text.toString(),
                        binding.dateArrived.text.toString(),
                        binding.destinationRatingBarInput.rating)
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

    fun showDestination(destination: DestinationModel) {
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

    fun updateImage(image: Uri) {
        i("Image Updated")
        Picasso.get()
            .load(image)
            .into(binding.destinationImage)
        binding.chooseDestinationImage.setText(R.string.change_destination_image)
    }
}