package com.example.a2020_feb_steelhacks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            val storeId = editText.text.toString()
            val productName = editText2.text.toString()
            findProduct(productName, storeId)
        }
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
                    var productTcin = ""
                    try {
                        productTcin = item.getJSONObject(0).getString("tcin")
                    } catch (e: Error) {

                    }
                    targetGetRequest(productTcin) {
                            if (it == 1) {
                                locationIcon.x = mensBtn.marginLeft.toFloat()
                                locationIcon.y = mensBtn.marginTop.toFloat()
                            } else if (it == 50) {
                                locationIcon.x = menswearBtn.marginLeft.toFloat() + 50.toFloat()
                                locationIcon.y = menswearBtn.marginTop.toFloat() - 100.toFloat()
                            } else if (it == 7) {
                                locationIcon.x = shoesBtn.marginLeft.toFloat()
                                locationIcon.y = shoesBtn.marginTop.toFloat()- 90.toFloat()
                            } else if (it == 18) {
                                locationIcon.x = homeStorageBtn.marginLeft.toFloat()
                                locationIcon.y = homeStorageBtn.marginTop.toFloat() - 150.toFloat()
                            }
                        }
                } catch (e: Error) {
                    println(e)
                }
            }

        })
    }


    fun targetGetRequest(productId: String?, callback: (Int) -> Unit){

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
                        println(aisle)
                        callback(aisle.toInt())
                    } else {
                    }
                } catch (e: Error) {
                    println(e)
                }
            }

        })
    }
}
