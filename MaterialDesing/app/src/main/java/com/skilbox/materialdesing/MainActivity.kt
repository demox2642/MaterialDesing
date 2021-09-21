package com.skilbox.materialdesing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    val fragment = FerstFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.page_2 -> {
                    val fragment = SecondFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.page_3 -> {
                    val fragment = ThirdFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.page_4 -> {
                    val fragment = FourthFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
            }
            false
        }
    }
}
