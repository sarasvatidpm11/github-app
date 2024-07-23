package com.dicoding.githubuserapp.ui.follow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.databinding.FragmentFollowBinding
import com.dicoding.githubuserapp.ui.UserAdapter
import com.dicoding.githubuserapp.ui.detail.DetailUserActivity

class FollowingFragment: Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        adapter = UserAdapter()
        binding.rvUsers.layoutManager = LinearLayoutManager(activity)


        viewModel.listFollowing.observe(viewLifecycleOwner) { items ->
            setListFollowing(items)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.getListFollowing(username)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setListFollowing(items: List<ItemsItem>) {
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}