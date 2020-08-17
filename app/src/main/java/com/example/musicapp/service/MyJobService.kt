package com.example.musicapp.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("#####", "1")
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("#####", "1")
        return true
    }

}
