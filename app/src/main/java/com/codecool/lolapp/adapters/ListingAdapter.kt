package com.codecool.lolapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.codecool.lolapp.R
import com.codecool.lolapp.fragments.ListingFragmentDirections
import com.codecool.lolapp.model.Character
import com.codecool.lolapp.util.*
import kotlinx.android.synthetic.main.list_item.view.*


class ListingAdapter(var characters: ArrayList<Character>) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    class ListingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.character_image
        private val name = view.character_name
        private val title = view.character_title
        private val blurb = view.character_blurb
        private val detailsButton = view.button_details

        fun bind(character: Character) {
            if (image != null) {
                val url =  BASE_URL_LIST_IMAGE + character.id + BASE_URL_LIST_TYPE
                image.loadImage(url, getProgressDrawable(view.context))
            }
            name.text = character.name
            title.text = character.title
            blurb.text = character.blurb
            detailsButton.setOnClickListener {

                val action = ListingFragmentDirections.actionListingFragmentToDetailsFragment(character.id)
                view.findNavController().navigate(action)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListingAdapter.ListingViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
        val isExpendable: Boolean = character.expanded
        holder.itemView.expandable_layout.visibility = if (isExpendable) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            character.expanded = !character.expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = characters.size
}