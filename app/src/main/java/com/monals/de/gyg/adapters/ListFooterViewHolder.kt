package com.monals.de.gyg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.R
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: ReviewApiStatus?) {
        itemView.progress_bar.visibility = if (status == ReviewApiStatus.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == ReviewApiStatus.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}