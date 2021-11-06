package com.example.mymemeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.cronet.CronetHttpStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currenturl: String?=null
    //val progressBar = findViewById<ProgressBar>(R.id.prgbar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

          loadmeme()

    }

    fun loadmeme(){
        val progressBar = findViewById<ProgressBar>(R.id.prgbar)
progressBar.visibility=View.VISIBLE
// Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"
        val imageView = findViewById<ImageView>(R.id.image1)
// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                currenturl=response.getString("url")

                Glide.with(this).load(currenturl).listener(object :RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.INVISIBLE
                        return false
                    }
                }).into(imageView)

            },
            { error ->

            }
        )
// Add the request to the RequestQueue.
        SingletonClass.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun Nextmeme(view: View) { loadmeme() }
    fun sharememe(view: View) {

  val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme..$currenturl")
        val chooser=Intent.createChooser(intent,"Share this meme...")
        startActivity(chooser)

    }
}
