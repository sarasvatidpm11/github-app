package com.dicoding.githubuserapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.database.FavoriteUser
import com.dicoding.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubuserapp.ui.UserAdapter
import com.dicoding.githubuserapp.ui.detail.DetailUserActivity
import com.dicoding.githubuserapp.ui.detail.DetailUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel : FavoriteUserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteUserActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        binding.rvUser.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter


//        viewModel = ViewModelProvider(this)[FavoriteUserViewModel::class.java]
//        viewModel.getFavoriteUsers().observe(this) { users ->
//            val items = arrayListOf<ItemsItem>()
//            users.map {
//                val item = ItemsItem(id = it.id, login = it.login, avatarUrl = it.avatarUrl)
//                items.add(item)
//            }
//            adapter.submitList(items)
//        }

//        viewModel.getFavoriteUsers().observe(this) {
//            if (it != null) {
//                val list = mapList(it)
//                adapter.setList(list)
//            }
//        }
//    }
//
//    private fun mapList(it: List<FavoriteUser>): ArrayList<ItemsItem> {
//        val listUsers = ArrayList<ItemsItem>()
//        for (user in it) {
//            val userMapped = ItemsItem(
//                user.login,
//                user.id.toString(),
//                user.avatarUrl.toString()
//            )
//            listUsers.add(userMapped)
//        }
//        return listUsers
//    }
    }
}