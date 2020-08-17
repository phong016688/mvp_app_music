package com.example.musicapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R
import com.example.musicapp.data.RepositoryImpl
import com.example.musicapp.databinding.LoginActivityBinding
import com.example.musicapp.domain.model.User
import com.example.musicapp.ui.detail.DetailActivity
import com.example.musicapp.ui.detail.DetailAdminActivity
import com.example.musicapp.ui.signup.SignUpActivity
import com.example.musicapp.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.lang.ref.WeakReference

@FlowPreview
@ExperimentalCoroutinesApi
class LoginActivity : AppCompatActivity(R.layout.login_activity), LoginView {
    private val mBinding by viewBindings(LoginActivityBinding::bind)
    private lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPresenter()
        handleEvent()
    }

    override fun onLoading() {
        mBinding.progressBar.show()
    }

    override fun onLoginSuccess(user: User) {
        mBinding.progressBar.gone()
        val intent = Intent(this, getToActivity(user))
        startActivity(intent)
    }

    override fun showError(throwable: Throwable) {
        mBinding.progressBar.gone()
        Snackbar.make(mBinding.root, throwable.message.toString(), 2000).show()
    }

    private fun setupPresenter() {
        mPresenter = LoginPresenterImpl(WeakReference(this), RepositoryImpl.instance)
    }

    private fun handleEvent() {
        mBinding.loginButton.clicks()
            .debounce(200)
            .map { mBinding.email.text.toString() to mBinding.password.text.toString() }
            .onEach { mPresenter.login(it.first, it.second) }
            .launchInMain()
        mBinding.signupText.clicks()
            .map { Intent(this@LoginActivity, SignUpActivity::class.java) }
            .onEach { startActivity(it) }
            .launchInMain()
    }

    private fun getToActivity(user: User) =
        if (user.isAdmin()) DetailAdminActivity::class.java else DetailActivity::class.java
}