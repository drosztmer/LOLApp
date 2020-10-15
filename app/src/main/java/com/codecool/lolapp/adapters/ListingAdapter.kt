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
import com.codecool.lolapp.R
import com.codecool.lolapp.activities.MainActivity
import com.codecool.lolapp.fragments.DetailsFragment
import com.codecool.lolapp.model.Character
import kotlinx.android.synthetic.main.list_item.*

class ListingAdapter(var characters: ArrayList<Character>) :
    RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    class ListingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.character_image)
        private val name = view.findViewById<TextView>(R.id.character_name)
        private val title = view.findViewById<TextView>(R.id.character_title)
        private val blurb = view.findViewById<TextView>(R.id.character_blurb)
        private val detailsButton = view.findViewById<ImageButton>(R.id.buttonDetails)

        fun bind(character: Character) {
            if (image != null) {
                Glide.with(view)
                    .load(character.image)
                    .into(image)
            }
            detailsButton.setOnClickListener { object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val bundle: Bundle = Bundle()
                    bundle.putString("id", character.id)
                    val detailsFragment: DetailsFragment = DetailsFragment()
                    detailsFragment.arguments = bundle
                    val activity = v?.context as AppCompatActivity
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, detailsFragment).addToBackStack(null).commit()
                }
            }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun onBindViewHolder(holder: ListingAdapter.ListingViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size
}