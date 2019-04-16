package com.florianschoeberl.hiringfs.features.start

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.allaboutapps.recyclerview.decorations.A3SeparatorDecoration
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.base.BaseActivity
import com.florianschoeberl.hiringfs.features.start.ui.main.ClubAdapter
import com.florianschoeberl.hiringfs.features.start.ui.main.MainViewModel
import com.florianschoeberl.hiringfs.model.Club
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        viewModel = viewModel()
        Timber.d("onCreate")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ClubAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView.addItemDecoration(A3SeparatorDecoration(resources, ResourcesCompat.getColor(resources, R.color.colorDivider, null)))

        viewModel.all.observe(this, Observer { clubs -> clubs.let { adapter.setClubs(it) }})
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private var counter: Int = 0

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        //todo tests
        if (counter == 0) {
            viewModel.delete()
        } else {
            viewModel.insert(Club(counter, "testclub", "testcountry", 500, "url"))
        }

        counter++

        return super.onOptionsItemSelected(item)
    }
}
