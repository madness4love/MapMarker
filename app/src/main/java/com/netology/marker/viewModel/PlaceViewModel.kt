package com.netology.marker.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.netology.marker.dto.Place
import com.netology.marker.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    val data = repository.data.asLiveData()
    private val _dataState = MutableLiveData<Place>()
    val edited = MutableLiveData(empty)


    fun edit(place: Place) {
        edited.value = place
        println("editedvalue " + edited.value + " " + edited)
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
                repository.save(it)
            }
        }

        edited.value = empty
    }

    fun changePlace(coords: LatLng, name: String, description: String) {
        val nameText = name.trim()
        val descText = description.trim()

        if (edited.value?.name == nameText && edited.value?.description == descText) {
            return
        }

        if (edited.value?.id == 0L) {
            edited.value = edited.value?.copy(
                coordinates = coords,
                name = nameText,
                description = descText
            )
        } else {
            edited.value = edited.value?.copy(
                name = nameText,
                description = descText
            )
        }
    }
}