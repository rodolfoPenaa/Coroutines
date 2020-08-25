package com.example.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URI
import java.net.URL

class MainActivity : AppCompatActivity() {
    //private lateinit var mProgressBAR : ProgressBar           CON KOTLIN SYNTETIC NO ES NECESARIO
    //private lateinit var mSpaceImage : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg"
        //val url2= "https://corvalan.dev/avif/oko.avif"
        CoroutineScope(Dispatchers.Main).launch {
            val images= doInBackground(url)
            if (images!=null){
                updateVIEW(images)
            }
        }
        //mProgressBAR.progressBAR
        //mSpaceImage.nasa_image

    }
    suspend fun doInBackground(url:String): Bitmap?{
        //var urlDisplay = url
        var bitMAP: Bitmap?= null
        withContext(Dispatchers.Default) {
            try {
                progressBAR.visibility = View.VISIBLE
                val inputStream = java.net.URL(url).openStream()
                bitMAP = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                Log.e("eRRoR", e.message.toString())
                e.printStackTrace()
            }
        }
        return bitMAP
    }
    fun updateVIEW(result: Bitmap){
            progressBAR.visibility = View.GONE
            nasa_image.setImageBitmap(result)
    }
}