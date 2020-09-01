package com.customvideocalling.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.customvideocalling.R
import com.customvideocalling.views.student.AddTokentActivity

class ChooseUserTypeActivity : AppCompatActivity() {
    private var btnTeacher: Button ?= null
    private var btnStudent: Button ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user_type)
        btnStudent = findViewById(R.id.btn_student)
        btnTeacher = findViewById(R.id.btn_teacher)
        btnTeacher!!.setOnClickListener {
            val intent = Intent(this, TeacherSignupActivity::class.java)
           startActivity(intent)
        }
        btnStudent!!.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}