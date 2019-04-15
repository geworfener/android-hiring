package com.florianschoeberl.hiringfs.features.start

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.base.BaseActivity
import com.florianschoeberl.hiringfs.features.start.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = viewModel()
        setSupportActionBar(toolbar)
        Timber.d("hallo log")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        //TODO: handle sort item click
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show()

        return super.onOptionsItemSelected(item)
    }
}
