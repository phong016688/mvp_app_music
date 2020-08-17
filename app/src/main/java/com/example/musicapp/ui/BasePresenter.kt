package com.example.musicapp.ui

interface BasePresenter<View: BaseView> {
    val view : View
}