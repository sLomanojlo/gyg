package com.monals.de.gyg.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monals.de.gyg.R
import com.monals.de.gyg.adapters.ReviewListAdapter
import com.monals.de.gyg.viewmodel.ReviewApiStatus
import com.monals.de.gyg.viewmodel.ReviewViewModel
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
            progress_bar.visibility = if (viewModel.isListEmpty() && state == ReviewApiStatus.LOADING) View.VISIBLE else View.GONE
            clError.visibility = if (viewModel.isListEmpty() && state == ReviewApiStatus.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.isListEmpty()) {
                reviewListAdapter.setState(state ?: ReviewApiStatus.DONE)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_replay -> {
                initAdapter()
                initState()
                Toast.makeText(this, "Replay", LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}