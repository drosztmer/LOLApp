package com.codecool.lolapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codecool.lolapp.R
import com.codecool.lolapp.fragments.DetailsFragment
import com.codecool.lolapp.model.Character
import com.codecool.lolapp.util.BASE_URL_LIST_IMAGE
import com.codecool.lolapp.util.BASE_URL_LIST_TYPE
import com.codecool.lolapp.util.ID
import com.codecool.lolapp.util.Util
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
                Util.loadListImage(character.id, view, image)
            }
            name.text = character.name
            title.text = character.title
            blurb.text = character.blurb
            detailsButton.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(ID, character.id)
                    val detailsFragment = DetailsFragment()
                    detailsFragment.arguments = bundle
                    val activity = itemView.context as AppCompatActivity
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()

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
        val isExpendable: Boolean = characters[position].expanded
        holder.itemView.expandable_layout.visibility = if (isExpendable) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener {
            val character_current = characters[position]
            character_current.expanded = !character_current.expanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = characters.size
}