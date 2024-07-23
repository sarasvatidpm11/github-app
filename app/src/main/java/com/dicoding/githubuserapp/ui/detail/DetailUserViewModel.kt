package com.dicoding.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.data.retrofit.ApiConfig
import com.dicoding.githubuserapp.database.FavoriteUser
import com.dicoding.githubuserapp.database.FavoriteUserDao
import com.dicoding.githubuserapp.database.FavoriteUserRoomDatabase
import com.dicoding.githubuserapp.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _detailUser.postValue(response.body())
                } else {
                    Log.e(javaClass.simpleName, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(javaClass.simpleName, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addFavorite(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.addFavoriteUser(favoriteUser)
    }

    fun deleteFavorite(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.deleteFavorite(favoriteUser)
    }

    fun checkUser(id: Int) {
        mFavoriteUserRepository.checkUser(id).observeForever {
            _isFavorite.postValue(it != null)
        }
    }
}