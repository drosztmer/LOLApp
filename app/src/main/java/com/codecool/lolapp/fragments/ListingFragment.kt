package com.codecool.lolapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codecool.lolapp.R
import com.codecool.lolapp.adapters.ListingAdapter
import com.codecool.lolapp.model.Character
import com.codecool.lolapp.viewmodels.ListingViewModel
import kotlinx.android.synthetic.main.fragment_listing.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListingFragment : Fragment() {

    private val viewModel: ListingViewModel by viewModel()
    private val listingAdapter = ListingAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()
        observeViewModel()
        setHasOptionsMenu(true)

        listing_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listingAdapter
            setHasFixedSize(true)
        }

        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
            listing_rv.visibility = View.GONE
            list_error.visibility = View.GONE
            loading_view.visibility = View.VISIBLE
            viewModel.refresh()
        }
    }

    fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                val myList = arrayListOf<Character>()
                for ((key, value) in response.characterList) {
                    myList.add(value)
                    value.name = key
                }
                listing_rv.visibility = View.VISIBLE
                listingAdapter.updateCharacters(myList)
            }
        })

        viewModel.characterLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                list_error.visibility = if (it) View.VISIBLE else View.GONE
                listing_rv.visibility = if (it) View.GONE else View.VISIBLE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    loading_view.visibility = View.VISIBLE
                    list_error.visibility = View.GONE
                    listing_rv.visibility = View.GONE
                } else {
                    loading_view.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favourites -> findNavController().navigate(R.id.action_listingFragment_to_favouritesFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}