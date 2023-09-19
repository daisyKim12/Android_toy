package org.texchtown.cgv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScrollViewModel: ViewModel() {

    private val selectedPosition : MutableLiveData<Int> = MutableLiveData<Int>()

    public fun setData(pos: Int) {
        selectedPosition.value = pos
    }

    public fun getSelectedScrollPosition():LiveData<Int> {
        return selectedPosition
    }

}
