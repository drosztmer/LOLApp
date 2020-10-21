package com.codecool.lolapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codecool.lolapp.R
import com.codecool.lolapp.model.Details
import com.codecool.lolapp.model.DetailsInfo
import com.codecool.lolapp.model.DetailsStats
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.util.Util
import com.codecool.lolapp.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.awaitAll
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var characterId: String
    private lateinit var details: Details

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_details, container, false)
        characterId = arguments?.getString("id").toString()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.details_title)

        viewModel.refresh(characterId)

        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                details = response.details[characterId] ?: error("Error")
                showDetails(details)
            }
            viewModel.isFavourite.observe(viewLifecycleOwner, Observer { isFavourite ->
                isFavourite?.let {
                    if (it) {
                        favourite_button.setBackgroundResource(R.drawable.ic_full_star)
                        toggleClickListener(it)
                    } else {
                        favourite_button.setBackgroundResource(R.drawable.ic_empty_star)
                        toggleClickListener(it)

                    }
                }
            })
        })

        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    loading_view.visibility = View.VISIBLE
                    list_error.visibility = View.GONE
                } else {
                    loading_view.visibility = View.GONE
                }
            }
        })


    }

    private fun showDetails(details: Details) {
        activity?.let { Util.loadDetailsImage(details.id, it, details_image) }
        details_name_text.text = details.name
        details_tags_text.text = details.tags.joinToString(separator = ", ")
        details_title_text.text = details.title
    }

    fun toggleClickListener(isFav: Boolean) {
        if (isFav) {
            favourite_button.setOnClickListener {
                viewModel.deleteFromFavourites(details.id)
                Toast.makeText(context, getString(R.string.delete_success_text), Toast.LENGTH_SHORT).show()
            }
        } else {
            val favourite = Favourite(details.id, details.name, details.title, details.blurb)
            favourite_button.setOnClickListener {
                viewModel.addToFavourites(favourite)
                Toast.makeText(context, getString(R.string.favourites_add), Toast.LENGTH_SHORT).show()
            }
        }
    }

}