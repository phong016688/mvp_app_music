package com.example.musicapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

@ExperimentalCoroutinesApi
fun Flow<Any>.launchInMain() {
    this.launchIn(CoroutineScope(Dispatchers.Main))
}