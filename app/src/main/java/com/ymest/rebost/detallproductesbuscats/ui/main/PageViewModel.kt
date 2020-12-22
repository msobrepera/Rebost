package com.ymest.rebost.detallproductesbuscats.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ymest.rebost.json.Producto

class PageViewModel : ViewModel() {

       private val _index = MutableLiveData<Int>()

   /* val text: LiveData<String> = Transformations.map(_index) {
        when(it){
            1 -> "Hello world from section: $it"
            2 -> "Hola que tal: $it"
            3 -> "Doncs la tercer $it"
            else -> "no hi ha pestanya"
        }
    }*/

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getIndex(): Int? {
        return _index.value
    }



}