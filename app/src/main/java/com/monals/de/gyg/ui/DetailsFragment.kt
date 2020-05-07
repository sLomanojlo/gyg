package com.monals.de.gyg.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.monals.de.gyg.databinding.FragmentDetailsBinding
import com.monals.de.gyg.models.Review
import com.monals.de.gyg.viewmodel.DetailsViewModel
import com.monals.de.gyg.viewmodel.obtainViewModel

class DetailsFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var sharedPrefs: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /** Instantiating or retrieving our [DetailsViewModel]*/
        viewModel = obtainViewModel(DetailsViewModel::class.java)

        val binding = FragmentDetailsBinding.inflate(inflater)

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment */
        binding.lifecycleOwner = this

        /** Giving the binding access to the OverviewViewModel */
        binding.viewModel = viewModel

        /**Fetching the selected review from local storage.*/
        val review: Review = getFromSharedPrefs(REVIEW)

        /**Setting the review in our ViewModel for further potential use.*/
        viewModel.setReview(review)


        return binding.root
    }

    /**Basic utility function for retrieving data from local storage.*/
    private fun getFromSharedPrefs(value: String): Review {
        sharedPrefs = requireActivity().getSharedPreferences(DEFAULT_SHARED_PREFS, Context.MODE_PRIVATE)
        val jsonString = sharedPrefs.getString(value, null)

        return GsonBuilder().create().fromJson(jsonString, Review::class.java)
    }

}