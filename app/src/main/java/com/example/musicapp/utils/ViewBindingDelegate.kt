package com.example.musicapp.utils

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> FragmentActivity.viewBindings(noinline bind: (View) -> T) =
    object : Lazy<T> {
        private var _value: T? = null
        private var _bind: ((View) -> T)? = bind

        override val value: T
            get() {
                if (_value == null) {
                    val view = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
                    _value = _bind!!(view)
                    _bind = null
                }
                return _value as T
            }

        override fun isInitialized(): Boolean = _value != null
    }

inline fun <reified T : ViewBinding> Fragment.viewBindings(
    noinline bind: (View) -> T
) = object : Lazy<T> {
    private var _value: T? = null
    private var _bind: ((View) -> T)? = bind

    override val value: T
        get() {
            if (_value == null) {
                _value = _bind!!(requireView())
                _bind = null
            }
            return _value as T
        }

    override fun isInitialized(): Boolean = _value != null
}