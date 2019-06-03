package com.example.hellogames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use the Fragment manager to create a Fragment transaction
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // Create a new fragment instance
        val helloFragment = HelloFragment()
        // Do fragment actions in the transaction
        // Add a new one
        fragmentTransaction.add(R.id.main_container, helloFragment)
        // Or do both operations in one step
        fragmentTransaction.replace(R.id.main_container, helloFragment)
        // Commit transaction
        fragmentTransaction.commit()
    }

    fun GoToFragmentHello() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val helloFragment = HelloFragment()
        fragmentTransaction.add(R.id.main_container, helloFragment)
        fragmentTransaction.replace(R.id.main_container, helloFragment)
        fragmentTransaction.commit()
    }

    fun GoToFragmentBlank(gameid : Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val blankfragment = BlankFragment()
        //fragmentTransaction.add(R.id.main_container, blankfragment)
        fragmentTransaction.replace(R.id.main_container, blankfragment)
        blankfragment.setGameId(gameid)
        fragmentTransaction.commit()
    }

}
