package com.codecool.lolapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codecool.lolapp.R
import com.codecool.lolapp.adapters.FavouritesAdapter
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.util.SwipeToDelete
import com.codecool.lolapp.viewmodels.FavouritesViewModel
import com.google.android.material.snackbar.Snackbar
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

        favouritesAdapter.setOnItemClickListener(this)
        viewModel.refresh()

        favourites_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favouritesAdapter
            swipeToDelete(this)
        }

        observeViewModel()
    }

    fun observeViewModel() {

        viewModel.favourites.observe(viewLifecycleOwner, Observer { favourites ->
            favourites?.let {
                favourites_rv.visibility = View.VISIBLE
                favouritesAdapter.updateFavourites(it)
                checkEmptyList()
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
                if (it) Toast.makeText(context, getString(R.string.delete_success_text), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.deleteFailure.observe(viewLifecycleOwner, Observer { isDeleteFailure ->
            isDeleteFailure?.let {
                if (it) Toast.makeText(context, getString(R.string.delete_failure_text), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.restored.observe(viewLifecycleOwner, Observer { isRestored ->
            isRestored?.let {
                if (it) Toast.makeText(context, getString(R.string.item_restored), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClicked(id: String, position: Int) {
        viewModel.deleteFromFavourites(id)
        favouritesAdapter.removeFavourite(position)
        checkEmptyList()
    }

    private fun checkEmptyList() {
        if (favouritesAdapter.getListSize() == 0) {
            list_empty.visibility = View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = favouritesAdapter.favourites[viewHolder.adapterPosition]
                viewModel.deleteFromFavourites(deletedItem.id)
                favouritesAdapter.removeFavourite(viewHolder.adapterPosition)
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: Favourite, position: Int) {
        val snackbar = Snackbar.make(
            view, getString(R.string.item_deleted), Snackbar.LENGTH_LONG
        )
        snackbar.setAction(getString(R.string.undo)) {
            viewModel.addToFavourites(deletedItem)
        }
        snackbar.show()
    }

}