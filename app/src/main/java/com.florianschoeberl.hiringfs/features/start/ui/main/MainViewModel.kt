package com.florianschoeberl.hiringfs.features.start.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.florianschoeberl.hiringfs.model.Club
import com.florianschoeberl.hiringfs.model.ClubDB
import com.florianschoeberl.hiringfs.model.ClubRepo
import com.florianschoeberl.hiringfs.networking.services.ApiService
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, apiService: ApiService) : AndroidViewModel(application) {

    private val repo: ClubRepo

    private val clubsAscending: LiveData<List<Club>>
    private val clubsDescending: LiveData<List<Club>>

    val clubs = MediatorLiveData<List<Club>>()

    private var ascending = true

    init {
        val clubsDao = ClubDB.getDatabase(application).clubDao()
        repo = ClubRepo(clubsDao, apiService)

        clubsAscending = repo.getClubsAsc()
        clubsDescending = repo.getClubsDesc()

        clubs.addSource(clubsAscending) { result -> result?.let { clubs.value = it } }
        clubs.addSource(clubsDescending) { }
    }

    fun changeOrdering() = when (ascending) {
        true -> clubsDescending.value?.let { clubs.value = it }
        false -> clubsAscending.value?.let { clubs.value = it }
    }.also { ascending = !ascending }

}
