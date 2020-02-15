package com.example.a2020_feb_steelhacks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            editText2.setText("bla")
            searchButtonHandler()
        }
    }

    fun searchButtonHandler(){
        targetGetTCINRequest(editText.getText().toString())
    }

    fun targetGetTCINRequest(userQuery: String?){
        var tcinGetURL = "https://redsky.target.com/v2/plp/search/?channel=web&count=24&default_purchasability_filter=true&facet_recovery=false&isDLP=false&keyword=" + userQuery + "&offset=0&pageId=%2Fs%2F"+ userQuery +"&pricing_store_id=2757&scheduled_delivery_store_id=2757&store_ids=2757%2C1253%2C2385%2C2201%2C1219&visitorId=017047147667020194471136FE8109F8&include_sponsored_search_v2=true&ppatok=AOxT33a&platform=desktop&useragent=Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F79.0.3945.130+Safari%2F537.36&key=eb2551e4accc14f38cc42d32fbc2b2ea"
        var client = OkHttpClient()
        var request = OkHttpRequest(client)
    }

    fun targetGetCoordinatesRequest(tcin: String?){
        var coordinatesGetURL = "https://redsky.target.com/v1/location_details/"+ tcin +"?latitude=40.441&longitude=-79.946&zip=15260&state=PA&storeId=2757&scheduled_delivery_store_id=2757"
    }
}
