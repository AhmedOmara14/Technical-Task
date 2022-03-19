package com.task.football_league.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.customview.widget.Openable
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.task.football_league.R
import com.task.football_league.databinding.ActivityMainBinding
import com.task.football_league.di.PreferenceModule
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferenceModule: PreferenceModule
    private var drawer: DrawerLayout? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawer = binding.drawerLayout
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }


    override fun onResume() {
        super.onResume()
        preferenceModule.token="c1c639c9362a4739832cf0ea41cd71f3"
        binding.ivMenu.setOnClickListener {
            drawer!!.open()
        }
        binding.tvAllCompetitions.setOnClickListener {
            navController.navigate(R.id.allCompetitionsFragment)
            drawer!!.close()
        }
        binding.tvFavorites.setOnClickListener {
            navController.navigate(R.id.authFragment)
            drawer!!.close()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, object : Openable {
            override fun isOpen(): Boolean {
                return drawer!!.isOpen
            }

            override fun open() {
                TODO("Not yet implemented")
            }

            override fun close() {
                TODO("Not yet implemented")
            }
        })
                || super.onSupportNavigateUp())
    }

    override fun onBackPressed() {
        if (drawer!!.isOpen) {
            drawer!!.close()
        } else {
            finish()
        }
    }
}