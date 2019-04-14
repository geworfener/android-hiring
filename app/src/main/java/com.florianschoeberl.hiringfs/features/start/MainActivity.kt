package com.florianschoeberl.hiringfs.features.start

import android.os.Bundle
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.base.BaseActivity
import com.florianschoeberl.hiringfs.features.start.ui.main.MainViewModel
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = viewModel()

        Timber.d("hallo log")
    }
}
