package com.example.musicapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random

class LocalService : Service() {
    private val mBinder: IBinder = LocalBinder()

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@LocalService
    }

    fun generator(): Int = Random.nextInt()
}

