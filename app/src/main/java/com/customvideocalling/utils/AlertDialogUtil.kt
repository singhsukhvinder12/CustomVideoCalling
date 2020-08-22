package com.customvideocalling.utils

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.customvideocalling.Interfaces.CallBackResult

object AlertDialogUtil {

    fun showAddChapterDialog(
        activity: Activity,
        callback: CallBackResult.AddQuestionnaireCallBack
    ) {
        val builder = AlertDialog.Builder(activity, R.style.ThemeOverlay_Material_Dialog_Alert)


        val inflater = activity.layoutInflater
        val dialoglayout = inflater.inflate(com.customvideocalling.R.layout.chapter_dialog_layout, null)
        builder.setView(dialoglayout)
        builder.setCancelable(true)
        val tvSubjectOption = dialoglayout.findViewById(com.customvideocalling.R.id.tv_title) as TextView

        val info = dialoglayout.findViewById(com.customvideocalling.R.id.ed_info) as EditText
        val grade = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_grade) as EditText
        val need = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_is_needed) as EditText
        val help = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_need_help) as EditText
        val superhero = dialoglayout.findViewById(com.customvideocalling.R.id.ed_superhero) as EditText
        val tvSubmit = dialoglayout.findViewById(com.customvideocalling.R.id.tvSubmit) as TextView
        val tvCancel = dialoglayout.findViewById(com.customvideocalling.R.id.tvCancel) as TextView
        val alertDialog = builder.show()
        alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                alertDialog.dismiss()
                callback.onAddQuestionnaire(info.text.toString(), grade.text.toString(),
                    need.text.toString(), help.text.toString(), superhero.text.toString())

            }

        })
        tvCancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                alertDialog.dismiss()
            }

        })


    }
}