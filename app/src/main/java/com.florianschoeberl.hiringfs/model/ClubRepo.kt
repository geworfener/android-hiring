package com.florianschoeberl.hiringfs.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ClubRepo(private val clubDao: ClubDao) {

    val all: LiveData<List<Club>> = clubDao.getAll()

    @WorkerThread
    suspend fun insert(club: Club) {
        clubDao.insert(club)
    }

    @WorkerThread
    suspend fun delete() {
        clubDao.deleteAll()
    }
}