package com.dicoding.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.database.FavoriteUser
import com.dicoding.githubuserapp.repository.FavoriteUserRepository


class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return mFavoriteUserRepository.getFavoriteUsers()
    }
}