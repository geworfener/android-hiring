package com.florianschoeberl.hiringfs.features.start

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import at.allaboutapps.recyclerview.decorations.A3SeparatorDecoration
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.base.BaseActivity
import com.florianschoeberl.hiringfs.features.start.ui.main.ClubAdapter
import com.florianschoeberl.hiringfs.features.start.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_activity.*
import com.google.android.material.snackbar.Snackbar


class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        viewModel = viewModel()

        val adapter = ClubAdapter(this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.addItemDecoration(A3SeparatorDecoration(resources, ResourcesCompat.getColor(resources, R.color.colorDivider, null)))

        val snack = Snackbar.make(container, R.string.loading, Snackbar.LENGTH_INDEFINITE)

        viewModel.clubs.observe(this, Observer { clubs -> clubs.let { adapter.setClubs(it) } })

        viewModel.getIsLoading().observe(this, Observer { isLoading ->
            if (isLoading) {
                snack.show()
            } else {
                Handler().postDelayed({
                    snack.dismiss()
                }, 1000)

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuRefresh -> viewModel.refresh()
            R.id.menuSort -> viewModel.changeOrdering()
        }

        return super.onOptionsItemSelected(item)
    }
}
