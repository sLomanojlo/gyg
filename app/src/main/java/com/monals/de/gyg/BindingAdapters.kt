package com.monals.de.gyg

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.models.Act
import com.monals.de.gyg.models.ReviewAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, act: Act?) {
    val adapter = recyclerView.adapter as ReviewAdapter

    adapter.submitList(act?.reviewList)
}

@BindingAdapter("text")
fun bindText(textView: TextView, text: String) {
    textView.text = text
}

@BindingAdapter("reviewApiStatus")
fun bindStatus(statusImageView: ImageView, status: ReviewApiStatus?) {


    when (status) {
        ReviewApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ReviewApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ReviewApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}