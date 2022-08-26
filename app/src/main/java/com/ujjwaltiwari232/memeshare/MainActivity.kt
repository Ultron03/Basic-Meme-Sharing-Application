package com.ujjwaltiwari232.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target



class MainActivity : AppCompatActivity() {

    var currentImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMenu()
    }

    fun nextMeme(view: android.view.View) {
       loadMenu()
    }
    fun shareMeme(view: android.view.View) {
       val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey , Checkout this cool meme I got $currentImageUrl")
        val chooser =Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    private fun loadMenu(){
    // Instantiate the RequestQueue.
        val  progressBar=findViewById<ProgressBar>(R.id.progressBar)
        val  nextButton=findViewById<Button>(R.id.nextButton)
        val  shareButton=findViewById<Button>(R.id.shareButton)

        progressBar.visibility= View.VISIBLE
        nextButton.isEnabled=false
        shareButton.isEnabled=false
    val url = "https://meme-api.herokuapp.com/gimme"

    // Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url,null,
        { response ->
            currentImageUrl= response.getString("url" )
            val imageView=findViewById<ImageView>(R.id.imageView)
            Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility= View.GONE
                    nextButton.isEnabled=true
                    shareButton.isEnabled=true
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                   progressBar.visibility= View.GONE
                    nextButton.isEnabled=true
                    shareButton.isEnabled=true
                    return false
                }
            }).into(imageView)
        },
        {
            Toast.makeText(this,"somthing went wrong",Toast.LENGTH_LONG).show()
        })

// Add the request to the RequestQueue.
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
