package com.softeer.cartalog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.cartalog.data.model.trim.Trim
import com.softeer.cartalog.data.repository.CarRepository
import kotlinx.coroutines.launch

class TrimViewModel(private val repository: CarRepository) : ViewModel() {

    private val _trimList: MutableLiveData<List<Trim>> = MutableLiveData(emptyList())
    val trimList: LiveData<List<Trim>> = _trimList
    private val _selectedTrim = MutableLiveData(0)
    val selectedTrim: LiveData<Int> = _selectedTrim

    private lateinit var modelName: String

    init {
        setTrimData()
    }

    private fun setTrimData() {
        viewModelScope.launch {
            val response = repository.getTrims()
            _trimList.value = response.trims
            modelName = response.modelName
        }
    }

    fun changeSelectedTrim(idx: Int) {
        _selectedTrim.value = idx
    }

    suspend fun setInitialMyCarData() {
        if(repository.getMyCarData() == null){
            repository.setInitialMyCarData(modelName, _trimList.value!![_selectedTrim.value!!])
        }
    }

    suspend fun getPriceData(): List<Int> {
        val selected = trimList.value!![selectedTrim.value!!]
        val myCarFromDB = repository.getMyCarData()
        val priceFromDB = myCarFromDB!!.totalPrice
        var initTotalPrice: Int
        if (priceFromDB == 0) {
            initTotalPrice = selected.minPrice + selected.defaultInfo!!.modelTypes.sumOf { it.option.price }
            repository.saveUserCarData(myCarFromDB.copy(totalPrice = initTotalPrice))
        } else {
            initTotalPrice = priceFromDB
        }
        return listOf(selected.minPrice, selected.maxPrice, initTotalPrice)
    }
}