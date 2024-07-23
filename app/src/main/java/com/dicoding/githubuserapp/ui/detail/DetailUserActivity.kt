package com.dicoding.githubuserapp.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.databinding.ActivityDetailUserBinding
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.githubuserapp.database.FavoriteUser
import com.dicoding.githubuserapp.ui.follow.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel by viewModels<DetailUserViewModel>()
    private var favorite = false
    private var favoriteUser: FavoriteUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle ()
        bundle.putString(EXTRA_USERNAME, username)
        detailUserViewModel.getDetailUser(username)

        detailUserViewModel.detailUser.observe(this) { detail ->
            getDetailData(detail)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailUserViewModel.checkUser(id)
        isFavorite()
        setFavoriteButton()
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailData(detail: DetailUserResponse) {
        binding.apply {
            tvName.text = detail.login
            tvUsername.text = detail.name
            tvFollowers.text = "${detail.followers} Followers"
            tvFollowing.text = "${detail.following} Following"
            Glide.with(this@DetailUserActivity)
                .load(detail.avatarUrl)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivProfile)
        }
        binding.favButton.visibility = View.VISIBLE
        //binding.shareButton.visibility = View.VISIBLE
    }

    private fun setFavoriteButton() {
        binding.favButton.setOnClickListener {
            favoriteUser?.let {
                if (!favorite) {
                    detailUserViewModel.addFavorite(it)
                    favorite = true
                    binding.favButton.setImageResource(R.drawable.ic_favorite_full)
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
                } else {
                    detailUserViewModel.deleteFavorite(it)
                    favorite = false
                    binding.favButton.setImageResource(R.drawable.ic_favorite_border)
                    Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isFavorite() {
        detailUserViewModel.isFavorite.observe(this) { isFavorite ->
            favorite = isFavorite
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}