package com.netology.marker.viewModel

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.netology.marker.R
import com.netology.marker.dto.Place
import com.netology.marker.repository.PlaceRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private val empty = Place(
    0,
    LatLng(0.0, 0.0),
    "",
    ""
)

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : ViewModel() {
//class PlaceViewModel @AssistedInject constructor(
//    private val repository: PlaceRepository
//) : ViewModel() {
//
//    @AssistedFactory
//    interface PlaceViewModelFactory {
//        fun create(): PlaceViewModel
//    }
//
//    companion object {
//        fun providesFactory(
//            assistedFactory: PlaceViewModelFactory,
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return assistedFactory.create() as T
//            }
//        }
//    }

    val data = repository.data.asLiveData()
    private val _dataState = MutableLiveData<Place>()
    val edited = MutableLiveData(empty)


    fun edit(place: Place) {
        edited.value = place
        println("edited value edit view model")

    }

    fun removeById(id: Long) {
        viewModelScope.launch {
            try {
                repository.removeById(id)
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun save() {
        edited.value?.let {
            viewModelScope.launch {
                println("edited value save view model")

                repository.save(it)
            }
        }
        println("edited value save empty view model")

        edited.value = empty
    }

    fun changePlace(coords: LatLng, name: String, description: String) {

        println("edited value change view model")

        val nameText = name.trim()
        val descText = description.trim()

        if (edited.value?.name == nameText && edited.value?.description == descText) {
            return
        }

      //  if (edited.value?.id == 0L) {


        edited.value = edited.value?.copy(
                coordinates = coords,
                name = nameText,
                description = descText
            )

        println("edited value change after coping view model")
//        } else {
//            edited.value = edited.value?.copy(
//                name = nameText,
//                description = descText
//            )
//        }
    }
}