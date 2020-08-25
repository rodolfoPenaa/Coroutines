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
        //val url = "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg" - 1 -
        val urls = listOf<String>("https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg")
        val progressBARList = listOf(progressBAR, progressBAR1, progressBAR2, progressBAR3)
        val imagesofspace = listOf(nasa_image,nasa_image1,nasa_image2,nasa_image3)

            CoroutineScope(Dispatchers.Main).launch {
                doInBackground(urls,progressBARList,imagesofspace)
            }
    }
    suspend fun doInBackground(urls:List<String>, progressBARlist: List<ProgressBar>, imageList: List<ImageView>){
        //var urlDisplay = url
        var listaZIP = urls.zip(progressBARlist)
        var j = imageList.iterator()
        for ((url, progressB) in listaZIP) {
        withContext(Dispatchers.Default) {
                try {
                    progressB.visibility = View.VISIBLE
                    val inputStream = java.net.URL(url).openStream()
                    var result = BitmapFactory.decodeStream(inputStream)
                    var imagen = j.next()
                    imagen.setImageBitmap(result)
                    Log.e("VISIBLE", "pass")
                    // bitMAP = BitmapFactory.decodeStream(inputStream)       ANTES
                } catch (e: Exception) {
                    Log.e("eRRoR", e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }

    //fun updateVIEW(result: Bitmap){
           //progressBAR.visibility = View.GONE
        // nasa_image.setImageBitmap(result)
   // }
}