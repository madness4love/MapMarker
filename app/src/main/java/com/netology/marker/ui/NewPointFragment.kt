package com.netology.marker.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.netology.marker.R
import com.netology.marker.databinding.NewPointBinding
import com.netology.marker.dto.Place
import com.netology.marker.ui.AboutPlaceFragment.Companion.name
import com.netology.marker.ui.MapFragment.Companion.KEY_MARKER_LATLNG
import com.netology.marker.ui.PlacesListFragment.Companion.KEY_PLACE
import com.netology.marker.utils.ParcelableArg
import com.netology.marker.utils.StringArg
import com.netology.marker.viewModel.PlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewPointFragment : Fragment() {

    companion object {
        var Bundle.latLng: Parcelable? by ParcelableArg

        val KEY_CANCEL = "cancel"
    }

    private val viewModel: PlaceViewModel by viewModels()

    private lateinit var binding: NewPointBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println("fragment is created" + this)
        binding = NewPointBinding.inflate(inflater, container, false)
//
//        arguments?.latLng?.let {
//            it.toString()
//        }
//
        val coords = arguments?.getParcelable<LatLng>(KEY_MARKER_LATLNG)!!
//
//
        binding.placeName.requestFocus()


        binding.placeCoordinates.text = coords.toString()



        binding.cancelBtn.setOnClickListener {
            //    val bundle = Bundle()
            //    bundle.putString(KEY_CANCEL, "cancel")
            findNavController().navigateUp()
        }

        binding.saveBtn.setOnClickListener {
            val name = binding.placeName.text.toString()
            val description = binding.placeDescription.text.toString()

            if (binding.placeName.text.isNullOrBlank()) {
                Toast.makeText(
                    activity,
                    this.getString(R.string.error_blank_name),
                    Toast.LENGTH_LONG
                ).show()
            } else {

                viewModel.changePlace(
                    coords,
                    name,
                    description
                )

                viewModel.save()


                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}

