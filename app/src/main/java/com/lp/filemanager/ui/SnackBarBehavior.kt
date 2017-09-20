package com.lp.filemanager.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.View
import com.github.clans.fab.FloatingActionMenu

/**
 * Created by LiPin on 2017/9/20 9:27.
 * 描述：FloatingActionMenuBehavior,当Snackbar出现时FloatingActionMenu隐藏
 */
class SnackBarBehavior : CoordinatorLayout.Behavior<FloatingActionMenu> {

    constructor()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionMenu?, dependency: View?): Boolean =
            dependency is Snackbar.SnackbarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionMenu, dependency: View): Boolean {
        val translationY: Float = Math.min(0f, dependency.translationY - dependency.height)
        child.animate().translationY(translationY).scaleX(0f).scaleY(0f).start()
        return true
    }
}