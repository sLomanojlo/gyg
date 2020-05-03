package com.monals.de.gyg.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.R
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.viewmodel.ReviewViewModel
import com.monals.de.gyg.adapters.ReviewListAdapter
import kotlinx.android.synthetic.main.activity_review_list.*

class ReviewListActivity : AppCompatActivity() {

    private lateinit var viewModel: ReviewViewModel
    private lateinit var reviewListAdapter: ReviewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)


        viewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        reviewListAdapter = ReviewListAdapter { viewModel.retry() }

        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = reviewListAdapter
        viewModel.reviewList.observe(this, androidx.lifecycle.Observer { reviewListAdapter.submitList(it)})
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }

        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (viewModel.listIsEmpty() && state == ReviewApiStatus.LOADING) View.VISIBLE else View.GONE
            clError.visibility = if (viewModel.listIsEmpty() && state == ReviewApiStatus.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                reviewListAdapter.setState(state ?: ReviewApiStatus.DONE)
            }
        })
    }

}