package com.dicoding.githubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuserapp.database.FavoriteUser
import com.dicoding.githubuserapp.database.FavoriteUserDao
import com.dicoding.githubuserapp.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteDao()
    }

    fun  getFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getFavoriteUsers()

    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.addFavorite(favoriteUser) }
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.deleteFavorite(favoriteUser) }
    }

    fun checkUser(id: Int): LiveData<FavoriteUser> {
        return mFavoriteUserDao.checkUser(id)
    }
}