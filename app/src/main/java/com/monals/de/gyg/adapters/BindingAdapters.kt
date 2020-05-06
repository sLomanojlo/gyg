package com.monals.de.gyg.adapters

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monals.de.gyg.R
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.util.convertDate
import com.monals.de.gyg.util.setUpRatingImages
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

@BindingAdapter("bindString")
fun bindString(tw: TextView,string: String) {
    tw.text = string
}

@BindingAdapter("bindStringNullable")
fun bindStringNullable(tw: TextView,string: String?) {
    tw.text = string
}

@BindingAdapter("bindDate")
fun bindDate(tw: TextView,string: String) {
    tw.text = convertDate(string)
}

@BindingAdapter("bindId")
fun bindId(tw: TextView,id: Int?) {
    tw.text = id.toString()
}

@BindingAdapter("bindImage")
fun bindImage(iw: ImageView, url: String?) {
    Glide.with(iw)
        .load(url ?: R.drawable.ic_face_black)
        .apply(
            RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_face_black))
        .into(iw)
}

@BindingAdapter("bindStars")
fun bindStars(cl: ConstraintLayout, rating: Int) {
    setUpRatingImages(cl, rating)
}

private const val COUPLE = "couple"
private const val FRIENDS = "friends"
private const val SOLO = "solo"
private const val YOUNG_FAMILY = "young family"

@BindingAdapter("bindTravelerType")
fun bindTravelerType(iw: ImageView, travelerType: String?) {

    Glide.with(iw)
        .load(
            when (travelerType) {
             COUPLE -> R.drawable.ic_couple
             FRIENDS -> R.drawable.ic_friends
             SOLO -> R.drawable.ic_solo
             YOUNG_FAMILY -> R.drawable.ic_young_family
                else -> R.drawable.ic_face_black
            })
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.ic_connection_error))
        .into(iw)

}
