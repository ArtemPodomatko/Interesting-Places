package ru.aapodomatko.interestingplaces.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.databinding.ActivityMainBinding



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            setContentView(mBinding.root)
        }, 3000)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.findNavController()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.descriptionFragment || destination.id == R.id.mapsFragment) {
                mBinding.tabLayout.visibility = View.GONE

            } else {
                mBinding.tabLayout.visibility = View.VISIBLE
            }
        }

        mBinding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> navController.navigate(R.id.mainFragment)
                    1 -> navController.navigate(R.id.searchFragment)
                    2 -> navController.navigate(R.id.favoriteFragment)
                }
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.mainFragment -> mBinding.tabLayout.getTabAt(0)?.select()
                        R.id.searchFragment -> mBinding.tabLayout.getTabAt(1)?.select()
                        R.id.favoriteFragment -> mBinding.tabLayout.getTabAt(2)?.select()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                navController.popBackStack()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}