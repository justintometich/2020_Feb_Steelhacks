package com.example.a2020_feb_steelhacks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.text.Editable
import kotlinx.android.synthetic.main.activity_main.*
=======
import okhttp3.*
>>>>>>> b1ac7322b9d4361a615863a40d1d68dc83bbe5f1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            editText2.setText("bla")
        }
    }

    fun searchButtonHandler(){

    }

    fun targetGetRequest(userQuery: String?){

    }
}
