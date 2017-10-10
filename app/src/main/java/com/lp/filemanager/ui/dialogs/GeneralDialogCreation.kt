package com.lp.filemanager.ui.dialogs

import com.afollestad.materialdialogs.MaterialDialog
import com.lp.filemanager.R
import com.lp.filemanager.activities.superclasses.BasicActivity

/**
 * Created by LiPin on 2017/9/21 15:44.
 * 描述：
 */
object GeneralDialogCreation {

    fun showBasicDialog(m: BasicActivity, texts: Array<String>): MaterialDialog {
//        val accentColor = m.getColorPreference().getColor(ColorUsage.ACCENT)
        val a = MaterialDialog.Builder(m)
                .content(texts[0])
                .widgetColor(m.resources.getColor(R.color.primary_pink))
//                .theme(m.getAppTheme().getMaterialDialogTheme())
                .title(texts[1])
                .positiveText(texts[2])
                .positiveColor(m.resources.getColor(R.color.primary_pink))
                .negativeText(texts[3])
                .negativeColor(m.resources.getColor(R.color.primary_pink))
                .neutralText(texts[4])
                .neutralColor(m.resources.getColor(R.color.primary_pink))
        return a.build()
    }
}