package com.lp.filemanager.ui.views.appbar

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.lp.filemanager.R
import com.lp.filemanager.activities.MainActivity
import kotlinx.android.synthetic.main.layout_appbar.*
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by LiPin on 2017/9/20 14:03.
 * 描述：layout_appbar.xml contains the layout for AppBar and BottomBar
 * 它在Toolbar下面，用来显示文件的路径信息
 */
class BottomBar : View.OnTouchListener {
    private val PATH_ANIM_START_DELAY = 0
    private val PATH_ANIM_END_DELAY = 0

    private var mainActivity: WeakReference<MainActivity>? = null
    private var appbar: AppBar? = null
    private var newPath: String? = null

    private var frame: FrameLayout? = null
    private var pathLayout: LinearLayout? = null
    private var buttons: LinearLayout? = null
    private var scroll: HorizontalScrollView? = null
    private var pathScroll: HorizontalScrollView? = null
    private var pathText: TextView? = null
    private var fullPathText: TextView? = null
    private var fullPathAnim: TextView? = null

    private var buttonParams: LinearLayout.LayoutParams? = null
    private var buttonRoot: ImageButton? = null
    private var buttonStorage: ImageButton? = null
    private val arrowButtons = ArrayList<ImageView>()
    private var lastUsedArrowButton = 0
    private val folderButtons = ArrayList<Button>()
    private var lastUsedFolderButton = 0
    private var arrow: Drawable? = null

    private var timer: CountDownTimer? = null
    private var allowChangePaths: Boolean = false
    private var gestureDetector: GestureDetector? = null

    constructor(appBar: AppBar, activity: MainActivity) {
        mainActivity = WeakReference(activity)
        this.appbar = appBar

        frame = activity.buttonbarframe

        scroll = activity.scroll
        buttons = activity.buttons

        pathLayout = activity.pathbar
        pathScroll = activity.scroll1
        fullPathText = activity.fullpath
        fullPathAnim = activity.fullpath_anim

        pathText = activity.pathname

        buttonParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        buttonParams?.gravity = Gravity.CENTER_VERTICAL

        buttonRoot = ImageButton(activity)
        buttonRoot?.setBackgroundColor(Color.TRANSPARENT)
        buttonRoot?.layoutParams = buttonParams

        buttonStorage = ImageButton(activity)
        buttonStorage?.setImageDrawable(activity.resources.getDrawable(R.drawable.ic_sd_storage_white_56dp))
        buttonStorage?.setBackgroundColor(Color.TRANSPARENT)
        buttonStorage?.layoutParams = buttonParams

        arrow = mainActivity?.get()?.resources?.getDrawable(R.drawable.abc_ic_ab_back_holo_dark)

        timer = object :CountDownTimer(5000,1000){
            override fun onFinish() {
            }

            override fun onTick(millisUntilFinished: Long) {
                //TODO:
            }

        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }
}