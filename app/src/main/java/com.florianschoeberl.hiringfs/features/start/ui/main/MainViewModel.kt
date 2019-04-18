package com.florianschoeberl.hiringfs.features.start.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.florianschoeberl.hiringfs.model.Club
import com.florianschoeberl.hiringfs.model.ClubRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, clubRepo: ClubRepo) : AndroidViewModel(application) {

    private val repo: ClubRepo = clubRepo

    private val clubsAscending: LiveData<List<Club>> = repo.getClubsAsc()
    private val clubsDescending: LiveData<List<Club>> = repo.getClubsDesc()

    val clubs = MediatorLiveData<List<Club>>()

    private var ascending = true

    init {
        clubs.addSource(clubsAscending) { result -> result?.let { clubs.postValue(it) } }
        clubs.addSource(clubsDescending) { }
    }

    fun changeOrdering() = when (ascending) {
        true  -> clubsDescending.value?.let { clubs.postValue(it) }
        false -> clubsAscending.value?.let { clubs.postValue(it) }
    }.also { ascending = !ascending }

    fun getIsLoading(): LiveData<Boolean> {
        return repo.isLoading
    }

    fun refresh() = repo.refresh()
}
