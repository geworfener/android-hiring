package com.florianschoeberl.hiringfs.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.florianschoeberl.hiringfs.networking.services.ApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import timber.log.Timber
import java.lang.Exception
import kotlin.coroutines.CoroutineContext


class ClubRepo constructor(private val clubDao: ClubDao, private val apiService: ApiService) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun getClubsAsc(): LiveData<List<Club>> {
        refresh()
        return clubDao.getAllAsc()
    }

    fun getClubsDesc(): LiveData<List<Club>> {
        refresh()
        return clubDao.getAllDesc()
    }

    fun refresh() {
        scope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                val response = apiService.getClubs().execute()

                if (response.isSuccessful) {
                    val list = response.body()!!
                            .map { c ->
                                Club(
                                        name = c.name
                                        , country = c.country
                                        , value = c.value
                                        , image = c.image)
                            }

                    clubDao.replaceAll(list)
                }

            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    val isLoading = MutableLiveData<Boolean>()
}