package com.codecool.lolapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codecool.lolapp.R
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.util.Util
import kotlinx.android.synthetic.main.favourites_item.view.*
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesAdapter(var favourites: ArrayList<Favourite>) :
    RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    companion object {
        lateinit var onItemClickListener: OnItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClicked(id: String, position: Int)
    }

    fun getListSize(): Int {
        return favourites.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun updateFavourites(newFavourites: List<Favourite>) {
        favourites.clear()
        favourites.addAll(newFavourites)
        notifyDataSetChanged()
    }

    fun removeFavourite(position: Int) {
        notifyItemRemoved(position)
        favourites.removeAt(position)
    }

    class FavouritesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.character_image
        private val name = view.character_name
        private val title = view.character_title
        private val deleteButton = view.button_delete


        fun bind(favourite: Favourite) {
            if (image != null) {
                Util.loadListImage(favourite.id, view, image)
            }
            name.text = favourite.name
            title.text = favourite.title
            deleteButton.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder =
        FavouritesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favourites_item, parent, false)
        )

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(favourites[position])
        holder.itemView.button_delete.setOnClickListener {
            onItemClickListener.onItemClicked(favourites[position].id, position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = favourites.size

}