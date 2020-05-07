package com.monals.de.gyg.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.monals.de.gyg.R

/**Activity class in charge of only setting up its UI layout.*/
class ReviewListActivity : AppCompatActivity() {

    /** Main entrance point to the app.*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)
    }
}