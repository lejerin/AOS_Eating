package desla.aos.eating.ui.main

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import desla.aos.eating.R
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repository.MainRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setupViews()
        updateStatusBarColor("#ffffff")

        val repository = MainRepository(ServerApi())
        val factory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }



    private fun setupViews()
    {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.fragNavHost)

        // Setting Navigation Controller with the BottomNavigationView
        bottomNavView.setupWithNavController(navController)

        bottomNavView.itemIconTintList = null

    }

    private fun updateStatusBarColor(color: String) { // Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }

    fun setVisibilityBottomNavigation(isShow: Boolean){
        if(bottomNavView == null) return
        if(isShow) bottomNavView.visibility = View.VISIBLE
        else bottomNavView.visibility = View.GONE
    }
}