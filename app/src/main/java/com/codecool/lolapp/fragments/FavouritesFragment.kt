package com.codecool.lolapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codecool.lolapp.R
import com.codecool.lolapp.adapters.FavouritesAdapter
import com.codecool.lolapp.viewmodels.FavouritesViewModel
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(), FavouritesAdapter.OnItemClickListener {

    private val viewModel: FavouritesViewModel by viewModel()
    private val favouritesAdapter = FavouritesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.favourites_title)

        viewModel.refresh()

        favouritesAdapter.setOnItemClickListener(this)

        favourites_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouritesAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {

        viewModel.favourites.observe(viewLifecycleOwner, Observer { favourites ->
            favourites?.let {
                favourites_rv.visibility = View.VISIBLE
                favouritesAdapter.updateFavourites(it)
            }
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
                    favourites_rv.visibility = View.GONE
                } else {
                    loading_view.visibility = View.GONE
                }
            }
        })
        viewModel.deleteSuccess.observe(viewLifecycleOwner, Observer { isDeleteSuccess ->
            isDeleteSuccess?.let {
                if (it) Toast.makeText(context, getString(R.string.delete_success_text), Toast.LENGTH_LONG).show()
            }
        })
        viewModel.deleteFailure.observe(viewLifecycleOwner, Observer { isDeleteFailure ->
            isDeleteFailure?.let {
                if (it) Toast.makeText(context, getString(R.string.delete_failure_text), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClicked(id: String, position: Int) {
        viewModel.deleteFromFavourites(id)
        viewModel.refresh()
        favouritesAdapter.notifyItemRemoved(position)
    }

}