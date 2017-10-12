package com.lp.filemanager.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lp.filemanager.R
import com.lp.filemanager.activities.superclasses.ThemedActivity
import com.lp.filemanager.ui.drawer.Item
import com.lp.filemanager.ui.views.appbar.AppBar
import com.lp.filemanager.ui.views.appbar.SearchView
import com.lp.filemanager.utils.Constant
import com.lp.filemanager.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : ThemedActivity() {

    private val IMAGE_SELECTOR_REQUEST_CODE = 31

    private var appbar: AppBar? = null
    private var appBarLayout: AppBarLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var drawerHeaderLayout: View? = null
    private var drawerHeaderView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAppBar()
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        initDrawerList()
        drawerHeaderLayout = layoutInflater.inflate(R.layout.drawerheader, null)
        drawerHeaderView = drawerHeaderLayout!!.findViewById(R.id.drawer_header) as ImageView?
        drawerHeaderView?.setImageResource(R.drawable.amaze_header)
        drawerHeaderView?.setOnLongClickListener {
            var intent: Intent? = null
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
            } else {
                Intent(Intent.ACTION_OPEN_DOCUMENT)
            }
            intent?.addCategory(Intent.CATEGORY_OPENABLE)
            intent?.type = "image/*"
            startActivityForResult(intent, IMAGE_SELECTOR_REQUEST_CODE)
            false
        }
        menu_drawer.addHeaderView(drawerHeaderLayout)
    }

    private fun initDrawerList() {
        var sectionItems = ArrayList<Item>()
        var storageDirectories = FileUtils.getStorageDirectories(this)
        menu_drawer.setBackgroundColor(ContextCompat.getColor(this, R.color.holo_dark_background))
        menu_drawer.divider = null
    }

    private fun initAppBar() {
        appbar = AppBar(this, getPrefs(), object : SearchView.SearchListener {
            override fun onSearch(queue: String) {

            }

        })
    }

    fun hideSmokeScreen() {

    }

    fun showSmokeScreen() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_extra, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_SELECTOR_REQUEST_CODE) {
            if (data != null && data.data != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    contentResolver.takePersistableUriPermission(data.data,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                getPrefs().edit().putString(Constant.DRAWER_HEADER_PATH, data.data.toString()).apply()
                setDrawerHeader()
            }
        }
    }

    private fun setDrawerHeader() {
        val path = getPrefs().getString(Constant.DRAWER_HEADER_PATH, "")
        if (TextUtils.isEmpty(path)) {

        } else {
            Glide.with(this).load(path).into(drawerHeaderView)
        }
    }

}
