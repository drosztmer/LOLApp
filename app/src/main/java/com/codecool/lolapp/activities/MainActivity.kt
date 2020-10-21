package com.codecool.lolapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.codecool.lolapp.R
import com.codecool.lolapp.fragments.DetailsFragment
import com.codecool.lolapp.fragments.FavouritesFragment
import com.codecool.lolapp.fragments.ListingFragment
import com.codecool.lolapp.fragments.LoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listingFragment: ListingFragment
    private lateinit var favouritesFragment: FavouritesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupListingFragment()

    }

    private fun setupListingFragment() {
        listingFragment = ListingFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, listingFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.favorite) {
            openFavorites()
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    fun openFavorites() {

        favouritesFragment = FavouritesFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, favouritesFragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}