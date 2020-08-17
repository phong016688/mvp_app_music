package com.example.musicapp.ui.main

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.musicapp.R
import com.example.musicapp.databinding.MainActivityBinding
import com.example.musicapp.utils.viewBindings
import com.example.musicapp.service.LocalService
import com.example.musicapp.service.MyJobService
import com.example.musicapp.ui.login.LoginActivity
import com.example.musicapp.utils.Constants

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    private val binding by viewBindings(MainActivityBinding::bind)
    private var mLocalService: LocalService? = null
    private var mBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }

        binding.clickButton.setOnClickListener {
            if (mLocalService == null) {
                Log.d("#####", "localservice null")
            }
            val numberString = mLocalService?.generator()?.toString()
            Toast.makeText(this, numberString, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, LocalService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        scheduleJob(this)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onStop() {
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
        finishJob(this)
        super.onStop()
    }

    private val mConnection = object : ServiceConnection {
        // not call when unbind
        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as? LocalService.LocalBinder
            mLocalService = binder?.getService()
            Log.d("######", "${binder == null}, ${mLocalService == null}")
            mBound = true
        }

    }


    fun scheduleJob(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val job = JobInfo.Builder(
            Constants.MY_BACKGROUND_JOB,
            ComponentName(context, MyJobService::class.java)
        )
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setRequiresCharging(true)
            .build()
        jobScheduler.schedule(job)
    }

    fun finishJob(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancelAll()
    }
}
