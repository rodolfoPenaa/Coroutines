package com.example.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
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
    private lateinit var progressBARList: List<ProgressBar>
    private lateinit var imagesofspace: List<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Running in Main Thread", Toast.LENGTH_SHORT).show()
        })
        //val url = "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg" - 1 -
        val urls = listOf<String>("https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg",
            "https://apod.nasa.gov/apod/image/1908/M61-HST-ESO-S1024.jpg")
            progressBARList = listOf(progressBAR, progressBAR1, progressBAR2, progressBAR3)
            imagesofspace = listOf(nasa_image,nasa_image1,nasa_image2,nasa_image3)
            CoroutineScope(Dispatchers.Main).launch {
                for (i in 0 until urls.size) {
                progressBARList[i].visibility = View.VISIBLE
                    var img = doInBackground1(urls[i])
                    progressBARList[i].visibility= View.GONE
                    imagesofspace[i].setImageBitmap(img)
                }
            }
    }
    suspend fun doInBackground(urls:List<String>){
        for (i in 0 until urls.size) {
        withContext(Dispatchers.Default) {
                try {
                    progressBARList[i].visibility = View.VISIBLE
                    val inputStream = java.net.URL(urls[i]).openStream()
                    var result = BitmapFactory.decodeStream(inputStream)
                    imagesofspace[i].setImageBitmap(result)
                    progressBARList[i].visibility = View.GONE
                } catch (e: Exception) {
                    Log.e("eRRoR", e.message.toString())
                    e.printStackTrace()
                }
            }
        }
    }
    suspend fun doInBackground1(url:String): Bitmap?{
        var bitMAP: Bitmap?= null
        withContext(Dispatchers.Default) {
            try {val inputStream = java.net.URL(url).openStream()
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