package com.example.oop_teamproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.oop_teamproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActionBar 설정
        setSupportActionBar(binding.toolbar)

        // NavHostFragment 가져오기
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.booksearchFragment, R.id.bookreservFragment, R.id.filereservFragment, R.id.checkpageFragment)
        )

        // ActionBar와 NavController 연결
        setupActionBarWithNavController(navController, appBarConfiguration)

        // BottomNavigationView와 NavController 연결
        binding.bottomNav.setupWithNavController(navController)

        // 초기 화면 설정
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    // 로그인 화면에서는 툴바와 바텀 네비게이션 바 숨기기
                    binding.toolbar.visibility = View.GONE
                    binding.bottomNav.visibility = View.GONE
                }
                else -> {
                    // 다른 화면에서는 툴바와 바텀 네비게이션 바 보이기
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.booksearchFragment -> {
                    navController.navigate(R.id.booksearchFragment)
                    true
                }
                R.id.bookreservFragment -> {
                    navController.navigate(R.id.bookreservFragment)
                    true
                }
                R.id.filereservFragment -> {
                    navController.navigate(R.id.filereservFragment)
                    true
                }
                R.id.checkpageFragment -> {
                    navController.navigate(R.id.checkpageFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
