package com.codecool.lolapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.codecool.lolapp.R
import com.codecool.lolapp.fragments.FavouritesFragment
import com.codecool.lolapp.fragments.ListingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listingFragment: ListingFragment
    private lateinit var favouritesFragment: FavouritesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
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
        if (id == R.id.favourite) {
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