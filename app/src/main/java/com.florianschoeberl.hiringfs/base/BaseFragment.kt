package com.florianschoeberl.hiringfs.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.florianschoeberl.hiringfs.di.Injectable
import com.florianschoeberl.hiringfs.di.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * Base class to use for this application
 */
abstract class BaseFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    /**
     * Request a ViewModel from the factory
     * @see ViewModelFactory
     */
    inline fun <reified T : ViewModel> viewModel() = ViewModelProviders.of(this, viewModelFactory).get(T::class.java)

    /**
     * Request a ViewModel scoped to the Activity from the factory
     * @see ViewModelFactory
     */
    inline fun <reified T : ViewModel> activityViewModel() = ViewModelProviders.of(requireActivity(), viewModelFactory).get(T::class.java)
}