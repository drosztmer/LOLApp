package com.codecool.lolapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codecool.lolapp.R
import com.codecool.lolapp.fragments.DetailsFragment
import com.codecool.lolapp.model.Character
import com.codecool.lolapp.util.BASE_URL_IMAGE
import com.codecool.lolapp.util.BASE_URL_TYPE


class ListingAdapter(var characters: ArrayList<Character>) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    class ListingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.character_image)
        private val name = view.findViewById<TextView>(R.id.character_name)
        private val title = view.findViewById<TextView>(R.id.character_title)
        private val blurb = view.findViewById<TextView>(R.id.character_blurb)
        private val detailsButton = view.findViewById<ImageButton>(R.id.button_details)

        fun bind(character: Character) {
            if (image != null) {
                val url = BASE_URL_IMAGE + character.name + BASE_URL_TYPE

                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)

                Glide.with(view)
                    .load(url)
                    .apply(options)
                    .into(image)
            }
            name.text = character.name
            title.text = character.title
            blurb.text = character.blurb
            detailsButton.setOnClickListener { object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val bundle: Bundle = Bundle()
                    bundle.putString("id", character.id)
                    val detailsFragment: DetailsFragment = DetailsFragment()
                    detailsFragment.arguments = bundle
                    val activity = v?.context as AppCompatActivity
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment).addToBackStack(null).commit()
                }
            }

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
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size
}