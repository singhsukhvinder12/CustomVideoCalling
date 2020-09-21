package com.customvideocalling.utils

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.*
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
        var selectedAge = ""
        var selectedGrade = ""
        var selectedNeed = ""
        var selectedHelp = ""
        var selectedSuperHero = ""
        val age: ArrayList<String> = ArrayList()
        age.add("Please Select Age")
        age.add("4-10")
        age.add("10-15")
        age.add("15-20")
        val gradeList: ArrayList<String> = ArrayList()
        gradeList.add("Please Select Grade")
        gradeList.add("First Grade")
        gradeList.add("Second Grade")
        gradeList.add("Third Grade")
        gradeList.add("Fourth Grade")
        gradeList.add("Fifth Grade")
        gradeList.add("Sixth Grade")
        gradeList.add("Seventh Grade")
        gradeList.add("Eighth Grade")
        gradeList.add("Ninth Grade")
        gradeList.add("Tenth Grade")
        gradeList.add("Eleventh Grade")
        gradeList.add("Twelfth Grade")
        val neededList: ArrayList<String> = ArrayList()
        neededList.add("What Is Needed")
        neededList.add("Help In Homework")
        neededList.add("Subject Help")
        neededList.add("Extra Learning")
        val helpList: ArrayList<String> = ArrayList()
        helpList.add("What Do You Need Help")
        helpList.add("New Learner")
        helpList.add("Struggling With Subject")
        helpList.add("Give Specific Instructions")
        val superHeroList: ArrayList<String> = ArrayList()
        superHeroList.add("Pick Your Superpower")
        superHeroList.add("Invisibility")
        superHeroList.add("Can Fly")
        superHeroList.add("Super-Fast Running")
        superHeroList.add("Super Strong")
        superHeroList.add("Control The Weather")
        superHeroList.add("Read Minds")
        superHeroList.add("Walk Through Walls")
        superHeroList.add("Super Hearing")
        superHeroList.add("Super Eyesight")
        superHeroList.add("Mega Swimming")

        val info = dialoglayout.findViewById(com.customvideocalling.R.id.ed_info) as Spinner
        val grade = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_grade) as Spinner
        val need = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_is_needed) as Spinner
        val help = dialoglayout.findViewById(com.customvideocalling.R.id.ed_what_need_help) as Spinner
        val superhero = dialoglayout.findViewById(com.customvideocalling.R.id.ed_superhero) as Spinner
        val tvSubmit = dialoglayout.findViewById(com.customvideocalling.R.id.tvSubmit) as TextView
        val tvCancel = dialoglayout.findViewById(com.customvideocalling.R.id.tvCancel) as TextView
        val spinnerArrayAdapter = ArrayAdapter<String>(activity as Context, android.R.layout.simple_spinner_dropdown_item, age)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        info.adapter = spinnerArrayAdapter
        val spinnerGradeAdapter = ArrayAdapter<String>(activity as Context, android.R.layout.simple_spinner_dropdown_item, gradeList)
        spinnerGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        grade.adapter = spinnerGradeAdapter
        val spinnerNeedAdapter = ArrayAdapter<String>(activity as Context, android.R.layout.simple_spinner_dropdown_item, neededList)
        spinnerNeedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        need.adapter = spinnerNeedAdapter
        val spinnerHelpAdapter = ArrayAdapter<String>(activity as Context, android.R.layout.simple_spinner_dropdown_item, helpList)
        spinnerHelpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        help.adapter = spinnerHelpAdapter
        val spinnerSuperHeroAdapter = ArrayAdapter<String>(activity as Context, android.R.layout.simple_spinner_dropdown_item, superHeroList)
        spinnerSuperHeroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        superhero.adapter = spinnerSuperHeroAdapter
        info.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedAge = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        grade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedGrade = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        need.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedNeed = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        help.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedHelp = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        superhero.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedSuperHero = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val alertDialog = builder.show()
        alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tvSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                alertDialog.dismiss()
                callback.onAddQuestionnaire(selectedAge, selectedGrade,
                    selectedNeed,selectedHelp, selectedSuperHero)

            }

        })
        tvCancel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                alertDialog.dismiss()
            }

        })


    }
}