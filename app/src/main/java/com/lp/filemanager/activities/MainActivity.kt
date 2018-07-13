package com.lp.filemanager.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.Menu
import android.view.MenuItem
import com.lp.filemanager.R
import com.lp.filemanager.activities.superclasses.ThemedActivity
import com.lp.filemanager.adapters.MainAdapter
import com.lp.filemanager.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_search.*


class MainActivity : ThemedActivity() {

    private val TAG = "MainActivity"
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createViewModelAndObserveLiveData() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.liveData.observe(this, Observer {
            it?.let {
                mainAdapter.list = it
                mainAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun init() {
        setSupportActionBar(toolbar)
        setListener()
        initRecyclerView()
        mainViewModel.getMainData()
    }

    private fun initRecyclerView() {
        mainAdapter = MainAdapter(this, ArrayList())
        rvMain.adapter = mainAdapter
    }

    private fun setListener() {
        searchView.setListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                searchView.show()
            }
            R.id.menu_history -> {
            }
            R.id.menu_setting -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchView.isShowing()) {
            searchView.hide()
        } else {
            super.onBackPressed()
        }
    }
}
