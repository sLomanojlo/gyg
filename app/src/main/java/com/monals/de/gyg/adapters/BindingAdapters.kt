package com.monals.de.gyg.adapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.viewmodel.ReviewApiStatus

/**BindingAdapter for the game rounds RecyclerView ListAdapter having guesses and hits*/
@BindingAdapter("listData")
fun listData(recyclerView: RecyclerView, reviewList: PagedList<Review>?) {
    val adapter = recyclerView.adapter as ReviewListAdapter
    adapter.submitList(reviewList)
}

@BindingAdapter("error", "listEmpty")
fun bindError(cl: ConstraintLayout, state: ReviewApiStatus?, listEmpty: Boolean?) {
    cl.visibility = if(state == ReviewApiStatus.ERROR && listEmpty == true) View.VISIBLE else View.GONE
}

@BindingAdapter("error", "listEmpty")
fun bindProgressBar(pb: ProgressBar, state: ReviewApiStatus?, listEmpty: Boolean?) {
    pb.visibility = if(state == ReviewApiStatus.LOADING && listEmpty == true) View.VISIBLE else View.GONE
}

@BindingAdapter("string")
fun bindString(tw: TextView,string: String?) {
    tw.text = string
}

@BindingAdapter("bindId")
fun bindId(tw: TextView,id: Int?) {
    tw.text = id.toString()
}