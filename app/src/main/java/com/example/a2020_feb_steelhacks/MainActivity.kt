package com.example.a2020_feb_steelhacks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Button
import android.app.AlertDialog
import android.content.DialogInterface
import okhttp3.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            editText2.setText("bla")
        }

        //Get reference to button
        val btn = findViewById(R.id.quitButton) as Button
        //set on-click listener
        btn.setOnClickListener {
            //code to run

            //Build alert dialogue -> to be CREATED LATER
            val d = AlertDialog.Builder(this)
            //set message of box
            d.setMessage("Wanna close this?")
                //If cancellable
                .setCancelable(false)
                //positive button
                .setPositiveButton("Proceed", DialogInterface.OnClickListener { dialog, id ->
                    finish()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            val alert = d.create()
            //Set title
            alert.setTitle("Exit?")
            //finally, SHOW IT
            alert.show()
        }
    }

    fun searchButtonHandler(){

    }

    fun targetGetRequest(userQuery: String?){

    }
}
