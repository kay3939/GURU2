package com.example.swuvoca.study

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swuvoca.Rate

class DayViewModel: ViewModel() {
    val dayInfo = MutableLiveData<MutableList<Rate>>()
    fun setDayData(info: Rate){
        dayInfo.value?.add(info.day-1, info)
    }
    val currentDay  = MutableLiveData<Rate>()
    fun setCurrentDay(info: Rate){
        currentDay.value = info
    }
}