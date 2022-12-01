package be.arnaud.rocketleaguestats.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.databinding.ActivityMainBinding
import be.arnaud.rocketleaguestats.ui.search.SearchFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        controller = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(controller)

        binding.appBarMain.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus && getSearchFragment() == null) {
                navigate(R.id.nav_search)
            }
        }

        binding.appBarMain.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getSearchFragment()?.query(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getSearchFragment()?.query(newText!!)
                return false
            }
        })

        binding.appBarMain.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (!binding.appBarMain.searchView.isIconified) {
            binding.appBarMain.searchView.isIconified = true
            binding.appBarMain.searchView.isIconified = true
        }
    }

    private fun getSearchFragment(): SearchFragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        if (fragment is SearchFragment){
            return fragment
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*when (item.itemId) {
            R.id.action_search -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_search)
            }
        }*/
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun navigate(navigationId: Int) {
        controller.navigate(navigationId)
    }

    fun navigate(navigationId: Int, bundle: Bundle?) {
        controller.navigate(navigationId, bundle)
    }
}