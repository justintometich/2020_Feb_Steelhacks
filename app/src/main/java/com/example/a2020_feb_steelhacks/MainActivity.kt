package com.example.a2020_feb_steelhacks

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException


class MainActivity : AppCompatActivity() {
    //var userSearchQueries = arrayOfNulls<String>(3)

    var client = OkHttpClient()
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener {
            val productName = productInput.text.toString()
            val storeId = storeIdInput.text.toString()
            findProduct(productName, storeId)
        }

        //comparePriceToAmazon("apples", 7.5.toFloat())
        //findProduct("toilet+paper", "2757")

        //locationIcon.x = mensBtn.marginLeft.toFloat()
        //locationIcon.y = mensBtn.marginTop.toFloat() + 350.0.toFloat()
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

            @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body?.string()
                try {
                    val responseJSON = JSONObject(responseStr)
                    val searchResponse = responseJSON.getJSONObject("search_response")
                    val items = searchResponse.getJSONObject("items")
                    val item = items.getJSONArray("Item")
                    for (i in 0 until item.length()) {
                        var productTcin = item.getJSONObject(i).getString("tcin")
                        //targetGetRequest(productTcin)
                        //val price = item.getJSONObject(i).getJSONObject("price").getString("current_retail").toFloat()
                        //var productTcin = ""
                        try {
                            productTcin = item.getJSONObject(0).getString("tcin")
                        } catch (e: Error) {
                            println(e)
                        }
                        targetGetRequest(productTcin) {
                            if (it%8 > 6) {
                                locationIcon.x = intimateBtn.marginLeft.toFloat() + intimateBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = intimateBtn.marginTop.toFloat() - intimateBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 5){
                                locationIcon.x = sportingGoodsBtn.marginLeft.toFloat() + sportingGoodsBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = sportingGoodsBtn.marginTop.toFloat() - sportingGoodsBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 4){
                                locationIcon.x = infantsBtn.marginLeft.toFloat() + infantsBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = infantsBtn.marginTop.toFloat() - infantsBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 3){
                                locationIcon.x = cosmeticsBtn.marginLeft.toFloat() + cosmeticsBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = cosmeticsBtn.marginTop.toFloat() - cosmeticsBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 2) {
                                locationIcon.x = mensBtn.marginLeft.toFloat() + mensBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = mensBtn.marginTop.toFloat() - mensBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 1) {
                                locationIcon.x = menswearBtn.marginLeft.toFloat() + menswearBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = menswearBtn.marginTop.toFloat() - menswearBtn.height.toFloat()/2.toFloat()
                            } else if (it%8 > 0) {
                                locationIcon.x = shoesBtn.marginLeft.toFloat() + shoesBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = shoesBtn.marginTop.toFloat() - shoesBtn.height.toFloat()/2.toFloat()
                            } else {
                                locationIcon.x = homeStorageBtn.marginLeft.toFloat() + homeStorageBtn.width.toFloat()/2.toFloat()
                                locationIcon.y = homeStorageBtn.marginTop.toFloat() - homeStorageBtn.height.toFloat()/2.toFloat()
                            }
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

    fun comparePriceToAmazon(productName: String, targetPrice: Float){
        var doc = Jsoup.connect("https://www.amazon.com/s?k=$productName&ref=nb_sb_noss_2").get()
        var element = doc.select("[data-cel-widget=\"search_result_3\"]")
        println(element)

    }

    fun snapMarkerToButton(location: String?){
        var aisle1 = findViewById<Button>(R.id.sportswearBtn)
        var aisle2 = findViewById<Button>(R.id.mensBtn)
    }
}
