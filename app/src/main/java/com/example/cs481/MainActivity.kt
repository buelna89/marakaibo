package com.example.cs481

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cs481.Fragments.APIFragment
import com.example.cs481.Fragments.DisplayFragment
import com.example.cs481.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val apifragment = APIFragment()
    private val displayfragment = DisplayFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(apifragment)

        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnNavigationItemSelectedListener{
            item-> when(item.itemId){
                R.id.ic_api -> replaceFragment(apifragment)
                R.id.ic_display -> replaceFragment(displayfragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    /*fun onClickAPI(view: View){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, FirebaseFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }*/
    /*fun onClickDisplay(view: View){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, DataFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }*/
}