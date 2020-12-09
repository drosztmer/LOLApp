package com.codecool.lolapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.codecool.lolapp.R
import com.codecool.lolapp.model.Details
import com.codecool.lolapp.util.DETAILS
import kotlinx.android.synthetic.main.fragment_lore.*

class LoreFragment : Fragment() {

    private lateinit var details: Details
    private val args by navArgs<LoreFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        details = args.details
        return inflater.inflate(R.layout.fragment_lore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfo()
    }

    private fun showInfo() {
        lore_text.text = details.lore
        ally_tips_text.text = details.allyTips.joinToString(separator = "\n")
        enemy_tips_text.text = details.enemyTips.joinToString(separator = "\n")
    }

}