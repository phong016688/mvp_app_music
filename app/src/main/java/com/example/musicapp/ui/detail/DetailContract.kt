package com.example.musicapp.ui.detail

import com.example.musicapp.domain.model.User
import com.example.musicapp.ui.BasePresenter
import com.example.musicapp.ui.BaseView

interface DetailPresenter : BasePresenter<DetailView> {
    fun getFirstListUsers()
    fun loadMoreUser(currentPosition: Int)
}

interface DetailView : BaseView {
    fun onGetFirstListUsersSuccess(users: List<User>)
    fun onGetFirstListUsersError(throwable: Throwable)
    fun onLoadMoreSuccess(users: List<User>)
    fun onLoadMoreError(throwable: Throwable)
}