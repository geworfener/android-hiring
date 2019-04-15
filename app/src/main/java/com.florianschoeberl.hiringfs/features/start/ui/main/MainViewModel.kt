package com.florianschoeberl.hiringfs.features.start.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.florianschoeberl.hiringfs.model.Club
import com.florianschoeberl.hiringfs.model.ClubDB
import com.florianschoeberl.hiringfs.model.ClubRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repo: ClubRepo
    val all: LiveData<List<Club>>

    init {
        val clubsDao = ClubDB.getDatabase(application).clubDao()
        repo = ClubRepo(clubsDao)
        all = repo.all
    }

    fun insert(club: Club) = scope.launch(Dispatchers.IO) {
        repo.insert(club)
    }

    fun delete() = scope.launch(Dispatchers.IO) {
        repo.delete()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}
