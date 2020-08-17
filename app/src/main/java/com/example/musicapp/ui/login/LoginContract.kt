package com.example.musicapp.ui.login

import com.example.musicapp.domain.model.User
import com.example.musicapp.ui.BasePresenter
import com.example.musicapp.ui.BaseView

interface LoginView : BaseView {
    fun onLoading()
    fun onLoginSuccess(user: User)
}

interface LoginPresenter : BasePresenter<LoginView> {
    fun login(email: String, password: String)
}
