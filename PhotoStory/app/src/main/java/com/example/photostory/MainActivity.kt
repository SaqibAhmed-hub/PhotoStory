package com.example.photostory

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jp.shts.android.storiesprogressview.StoriesProgressView


class MainActivity : AppCompatActivity(), StoriesProgressView.StoriesListener {


    private lateinit var storiesProgressView: StoriesProgressView
    private lateinit var image: ImageView
    private lateinit var textview: TextView

    private var counter = 0
    private val resourse = arrayOf(
            Image(R.drawable.sample1, "Mountain"),
            Image(R.drawable.sample2, "Cruise"),
            Image(R.drawable.sample3, "Lonely"),
            Image(R.drawable.sample4, "Curiosity"),
            Image(R.drawable.sample5, "Blossom"),
            Image(R.drawable.sample6, "Parashute Balloon"),
            Image(R.drawable.sample7, "City light"),
            Image(R.drawable.sample8, "Night Beauty"),
            Image(R.drawable.sample9, "Galaxies"),
            Image(R.drawable.sample10, "Waterfall")
    )

    var pressTime = 0L
    var limit = 500L

    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressTime = System.currentTimeMillis()
                    storiesProgressView!!.pause()
                    return false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    storiesProgressView!!.resume()
                    return limit < now - pressTime
                }
            }
            return false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        image = findViewById(R.id.imageview)
        textview = findViewById(R.id.tvview)
        storiesProgressView = findViewById(R.id.stories)

        storiesProgressView.setStoriesCount(resourse.size)
        storiesProgressView.setStoryDuration(3000L)
        storiesProgressView.setStoriesListener(this)
        storiesProgressView.startStories()


        Glide.with(this)
                .load(resourse[counter].imageurl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)
        textview.text = resourse[counter].title

        // image.setImageResource(resourse[counter])

        //Reverse
        val reverse = findViewById<View>(R.id.reverse)
        reverse.setOnClickListener {
            storiesProgressView.reverse()
        }
        reverse.setOnTouchListener(onTouchListener)

        //Skip
        val skip = findViewById<View>(R.id.skip)
        skip.setOnClickListener {
            storiesProgressView.skip()
        }
        skip.setOnTouchListener(onTouchListener)
    }

    override fun onComplete() {
        finish()
    }

    override fun onPrev() {
        if ((counter - 1) < 0) return

        //image.setImageResource(resourse[--counter])
        Glide.with(this)
                .load(resourse[--counter].imageurl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

        textview.text = resourse[counter].title
    }

    override fun onNext() {
        //   image.setImageResource(resourse[++counter])
        Glide.with(this)
                .load(resourse[++counter].imageurl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

        textview.text = resourse[counter].title
    }

    override fun onDestroy() {
        storiesProgressView.destroy()
        super.onDestroy()
    }
}