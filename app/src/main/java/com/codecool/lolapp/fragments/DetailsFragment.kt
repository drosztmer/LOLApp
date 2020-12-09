package com.codecool.lolapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.codecool.lolapp.R
import com.codecool.lolapp.model.Details
import com.codecool.lolapp.model.data.Favourite
import com.codecool.lolapp.util.DETAILS
import com.codecool.lolapp.util.ID
import com.codecool.lolapp.util.Util
import com.codecool.lolapp.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()

    private lateinit var characterId: String
    private var details: Details = Details("", "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        characterId = arguments?.getString(ID).toString()
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.details_title)

        viewModel.refresh(characterId)

        observeViewModel()

        lore_button.setOnClickListener {
            loreClick()
        }

    }

    private fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            response.let {
                details = response.details[characterId] ?: error(getString(R.string.error))
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
                details_scrollview.visibility = if (it) View.GONE else View.VISIBLE
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
        details_title_text.text = details.title
        details_tags_text.text = details.tags.joinToString(separator = ", ")
        details_attack_text.text = details.info.attack.toString()
        details_defense_text.text = details.info.defense.toString()
        details_magic_text.text = details.info.magic.toString()
        details_difficulty_text.text = details.info.magic.toString()
        details_hp_text.text = details.stats.hp.toString()
        details_mp_text.text = details.stats.mp.toString()
        details_move_speed_text.text = details.stats.moveSpeed.toString()
        details_attack_range_text.text = details.stats.attackRange.toString()
        details_attack_damage_text.text = details.stats.attackDamage.toString()

    }

    private fun toggleClickListener(isFav: Boolean) {
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

    private fun loreClick() {
        val bundle = Bundle()
        bundle.putSerializable(DETAILS, details)
        val loreFragment = LoreFragment()
        loreFragment.arguments = bundle

    }

}