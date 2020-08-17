package com.example.musicapp.ui.detail

import com.example.musicapp.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class DetailPresenterImpl(
    viewWeakReference: WeakReference<DetailView>,
    private val repository: Repository
) : DetailPresenter {
    override val view: DetailView = viewWeakReference.get() ?: throw Throwable("View Null")

    override fun getFirstListUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = repository.getUsers(1, 10)
                withContext(Dispatchers.Main) { view.onGetFirstListUsersSuccess(users) }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) { view.onGetFirstListUsersError(throwable) }
            }
        }
    }

    override fun loadMoreUser(currentPosition: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = repository.getUsers(currentPosition, currentPosition + 5)
                withContext(Dispatchers.Main) { view.onLoadMoreSuccess(users) }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) { view.onLoadMoreError(throwable) }
            }
        }
    }

}