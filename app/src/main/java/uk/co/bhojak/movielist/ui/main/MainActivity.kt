package uk.co.bhojak.movielist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(uk.co.bhojak.movielist.R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {
        addFragment(MovieListFragment(), false, "list")
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(uk.co.bhojak.movielist.R.id.frame_container, fragment, tag)
        ft.commitAllowingStateLoss()
    }


}
