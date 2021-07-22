package com.practicaltest.testxento.fragment.book

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicaltest.testxento.R
import com.practicaltest.testxento.activity.DetailsActivity
import com.practicaltest.testxento.adapter.BookAdapter
import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.databinding.FragmentBookBinding
import com.practicaltest.testxento.databinding.FragmentNewsBinding
import com.practicaltest.testxento.utils.Resource
import com.practicaltest.testxento.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookApiFragment : Fragment(),BookAdapter.BookItemListener {

    private var binding: FragmentBookBinding by autoCleared()
    private val viewModel: BookViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private var item: List<Book> = ArrayList<Book>()
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter(this, context)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.bookDataSource.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty())
                        Log.d("TAG: ", "Book: " + it.data.size)
                    adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                }

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedBook(bookId: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)
        val model: Book = adapter.items.get(bookId)
        val rating = "Rating: ${model.averageRating} (${model.ratingsCount})"
        intent.putExtra("url", model.infoLink)
        intent.putExtra("title", model.title)
        intent.putExtra("img", model.thumbnail)
        intent.putExtra("date", model.publishedDate)
        intent.putExtra("source", model.authors)
        intent.putExtra("author", rating)
        startActivity(intent)
    }

}