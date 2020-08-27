package com.pindemo.pin.viewmodel

import androidx.lifecycle.*
import com.pindemo.pin.utils.PinBlockUtils
import com.pindemo.pin.utils.nibsToString
import kotlinx.coroutines.launch


class PinViewModel : ViewModel() {

    private val pinBlockUtil by lazy {
        PinBlockUtils()
    }
    private val pinEntryLiveData = MutableLiveData<String>()
    private val errorLiveData = MutableLiveData<Int>()
    val pinBlockEntryLiveData: LiveData<String> = pinEntryLiveData
    val pinErrorLiveData: LiveData<Int> = errorLiveData
    val progressLiveData = MutableLiveData<Boolean>()

    fun computeBlock(pin: String) {
        progressLiveData.value = true
        viewModelScope.launch {
            try {
                val block = pinBlockUtil.generatePinBlock(pin)
                pinEntryLiveData.value = block.nibsToString()
                errorLiveData.value = 0
            } catch (ex: PinBlockUtils.InvalidPinException) {
                errorLiveData.value = ex.resourceId
            }
        }
        progressLiveData.value = false
    }

}