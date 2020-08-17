package com.example.musicapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.R
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.utils.viewBindings

class MainFragment : Fragment(R.layout.main_fragment) {
    private val mBinding by viewBindings(MainFragmentBinding::bind)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}