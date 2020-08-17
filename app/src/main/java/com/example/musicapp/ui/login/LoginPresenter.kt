package com.example.musicapp.ui.login

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.musicapp.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class LoginPresenterImpl(
    viewWeakReference: WeakReference<LoginView>,
    private val repository: Repository
) : LoginPresenter {

    override val view: LoginView = viewWeakReference.get() ?: throw Throwable("View Null")

    override fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { view.onLoading() }
            try {
                val user = repository.login(email, password)
                Log.d("#####", "token $user")
                withContext(Dispatchers.Main) { view.onLoginSuccess(user) }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) { view.showError(throwable) }
            }
        }
    }
}