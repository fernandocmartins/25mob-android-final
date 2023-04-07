package br.com.fiap25mob.mbamobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.fiap25mob.mbamobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        prepareToolbar()
        changeNavIcon()
    }

    fun prepareToolbar(){
        setSupportActionBar(binding.carsToolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
         return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun changeNavIcon(){
        navController.addOnDestinationChangedListener{ _, _, _ ->
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back_arrow)
        }
    }
}