package com.example.musicapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicapp.R
import com.example.musicapp.data.RepositoryImpl
import com.example.musicapp.databinding.DetailAdminActivityBinding
import com.example.musicapp.domain.model.User
import com.example.musicapp.ui.detail.adapter.UsersAdapter
import com.example.musicapp.utils.viewBindings
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class DetailAdminActivity : AppCompatActivity(R.layout.detail_admin_activity), DetailView,
    SwipeRefreshLayout.OnRefreshListener {
    private val mBinding by viewBindings(DetailAdminActivityBinding::bind)
    private lateinit var mAdapter: UsersAdapter
    private lateinit var mPresenter: DetailPresenter
    private lateinit var mOnScrollListener: RecyclerView.OnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        setupPresenter()
        mPresenter.getFirstListUsers()
        mBinding.refreshLayout.setOnRefreshListener(this)
    }

    override fun onDestroy() {
        mBinding.refreshLayout.setOnRefreshListener(null)
        mBinding.usersRecyclerview.removeOnScrollListener(mOnScrollListener)
        super.onDestroy()
    }

    override fun onGetFirstListUsersSuccess(users: List<User>) {
        mBinding.refreshLayout.isRefreshing = false
        mAdapter.submitList(users)
    }

    override fun onGetFirstListUsersError(throwable: Throwable) {
        mBinding.refreshLayout.isRefreshing = false
        mAdapter.submitList(emptyList())
        Snackbar.make(mBinding.root, "Get users error", 2000).show()
    }

    override fun onLoadMoreSuccess(users: List<User>) {
        mAdapter.isLoading = false
        mAdapter.submitList(mAdapter.currentList.dropLast(1) + users)
    }

    override fun onLoadMoreError(throwable: Throwable) {
        mAdapter.isLoading = false
        mAdapter.submitList(mAdapter.currentList.dropLast(1))
        Snackbar.make(mBinding.root, "Loadmore error", 2000).show()
    }

    override fun showError(throwable: Throwable) {
        Snackbar.make(mBinding.root, throwable.message.toString(), 2000).show()
    }

    override fun onRefresh() {
        mPresenter.getFirstListUsers()
    }

    private fun setupRecyclerView() {
        mAdapter = UsersAdapter()
        val layoutManager = LinearLayoutManager(this)
        mOnScrollListener = getOnScrollListener(layoutManager)
        mBinding.usersRecyclerview.layoutManager = layoutManager
        mBinding.usersRecyclerview.adapter = mAdapter
        mBinding.usersRecyclerview.hasFixedSize()
        mBinding.usersRecyclerview.addOnScrollListener(mOnScrollListener)
        mAdapter.submitList(emptyList())
    }

    private fun setupPresenter() {
        mPresenter = DetailPresenterImpl(WeakReference(this), RepositoryImpl())
    }

    private fun getOnScrollListener(layoutManager: LinearLayoutManager) =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!mAdapter.isLoading && dy > 0 && layoutManager.findLastVisibleItemPosition() + 5 > layoutManager.itemCount) {
                    mPresenter.loadMoreUser(layoutManager.itemCount)
                    mAdapter.isLoading = true
                    mAdapter.submitList(mAdapter.currentList.plus(User()))
                }
            }
        }
}
