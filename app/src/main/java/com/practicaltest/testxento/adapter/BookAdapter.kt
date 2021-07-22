package com.practicaltest.testxento.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.databinding.ItemBinding
import com.practicaltest.testxento.databinding.ItemBookBinding
import com.practicaltest.testxento.utils.Utils

class BookAdapter(private val listener: BookItemListener,val context: Context?) : RecyclerView.Adapter<BookViewHolder>() {

    interface BookItemListener {
        fun onClickedBook(bookId: Int)
    }

    val items = ArrayList<Book>()

    fun setItems(items: ArrayList<Book>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding: ItemBookBinding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) = holder.bind(items[position],context)
}

class BookViewHolder(private val itemBinding: ItemBookBinding, private val listener: BookAdapter.BookItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var book: Book

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    fun bind(item: Book, context: Context?) {
        this.book = item
        itemBinding.title.text = item.title
        itemBinding.desc.text = item.description
        itemBinding.source.text = item.authors
        itemBinding.time.text = item.publisher
        itemBinding.publishedAt.text = item.publishedDate
        itemBinding.author.text = "Rating: ${item.averageRating} (${item.ratingsCount})"
        val requestOptions = RequestOptions()
        requestOptions.placeholder(Utils.getRandomDrawbleColor())
        requestOptions.error(Utils.getRandomDrawbleColor())
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
        requestOptions.centerCrop()
        item.thumbnail?.let { img ->
            context?.let {
                Glide.with(it)
                    .load(img)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .centerCrop()
                    .apply(requestOptions)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            @Nullable e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.d("Test:exceptiom ",""+e)

                           // itemBinding.prograssLoadPhoto.setVisibility(View.GONE)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                           // itemBinding.prograssLoadPhoto.setVisibility(View.GONE)
                            return false
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(itemBinding.img)
            }
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedBook(book.id)
    }
}


