package com.practicaltest.testxento.fragment.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicaltest.testxento.activity.DetailsActivity
import com.practicaltest.testxento.adapter.NewsAdapter
import com.practicaltest.testxento.data.entities.News
import com.practicaltest.testxento.databinding.FragmentNewsBinding
import com.practicaltest.testxento.utils.Resource
import com.practicaltest.testxento.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class NewsApiFragment : Fragment(), NewsAdapter.NewsItemListener {
    private var binding: FragmentNewsBinding by autoCleared()
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(this, context)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.newsDataSource.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        Log.d("TAG: ", "news: " + it.data.size)
                        adapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedNews(nesId: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        val model: News = adapter.items.get(nesId)
        intent.putExtra("id", nesId)
        intent.putExtra("tag", "NewsApiFragment")
        intent.putExtra("url", model.url)
        intent.putExtra("title", model.title)
        intent.putExtra("img", model.urlToImage)
        intent.putExtra("date", model.publishedAt)
        intent.putExtra("source", model.author)
        intent.putExtra("author", model.author)
        startActivity(intent)
    }
}