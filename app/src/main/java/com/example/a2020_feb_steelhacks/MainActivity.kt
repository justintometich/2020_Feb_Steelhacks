package com.example.a2020_feb_steelhacks

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    val eastLibertyStoreId = "2757"
    var userSearchQueries = arrayOfNulls<String>(3)

    var client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            editText2.setText("bla")
        }

        //findProduct("toilet+paper", eastLibertyStoreId)

        locationIcon.x = mensBtn.marginLeft.toFloat()
        locationIcon.y = mensBtn.marginTop.toFloat() + 350.0.toFloat()
    }

    fun searchButtonHandler(){

    }



    fun findProduct(productName: String, storeId: String) {
        val httpBuilder = "https://redsky.target.com/v2/plp/search/".toHttpUrlOrNull()!!.newBuilder()
            .addQueryParameter("keyword", productName)
            .addQueryParameter("pricing_store_id", storeId)
            .addQueryParameter("key", "hcsjcbskjcbsdj")
            .build()

        val request = Request.Builder().url(httpBuilder).get().build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body?.string()
                try {
                    val responseJSON = JSONObject(responseStr)
                    val searchResponse = responseJSON.getJSONObject("search_response")
                    val items = searchResponse.getJSONObject("items")
                    val item = items.getJSONArray("Item")
                    for (i in 0 until item.length()) {
                        val productTcin = item.getJSONObject(i).getString("tcin")
                        targetGetRequest(productTcin)
                        val price = item.getJSONObject(i).getJSONObject("price").getString("current_retail").toFloat()

                    }
                } catch (e: Error) {
                    println(e)
                }
            }

        })
    }


    fun targetGetRequest(productId: String?){

        val httpBuilder =
            "https://redsky.target.com/v1/location_details/$productId".toHttpUrlOrNull()!!.newBuilder()
                .addQueryParameter("storeId", "2757")
                .build()


        val request = Request.Builder()
            .url(httpBuilder.newBuilder().build())
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body?.string()
                try {
                    val responseJSON = JSONObject(responseStr!!)
                    val product = JSONObject(responseJSON.getString("product"))
                    val inStoreLocation = JSONObject(product.getString("in_store_location"))
                    if (inStoreLocation.isNull("status_code") && inStoreLocation.length() > 0) {
                        val block = inStoreLocation.getString("block")
                        val aisle = inStoreLocation.getString("aisle")
                        println("Block: " + block + " Aisle: " + aisle)
                        //println(inStoreLocation)
                    } else {
                        println("Status Code 200")
                    }
                } catch (e: Error) {
                    println(e)
                }
            }

        })
    }

    fun snapMarkerToButton(location: String?){
        var aisle1 = findViewById<Button>(R.id.sportswearBtn)
        var aisle2 = findViewById<Button>(R.id.mensBtn)
    }
}
