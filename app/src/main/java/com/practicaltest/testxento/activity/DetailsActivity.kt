package com.practicaltest.testxento.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.practicaltest.testxento.R
import com.practicaltest.testxento.data.entities.Book
import com.practicaltest.testxento.fragment.book.BookViewModel
import com.practicaltest.testxento.utils.Resource
import com.practicaltest.testxento.utils.Utils
import com.practicaltest.testxento.viewModel.BookDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mDate: String? = null
    private var mSource: String? = null
    private var isHideToolbarView = false
    private var id: String? = null
    private var tag: String? = null
    private var mImg: String? = null
    private var mAuthor: String? = null

    private val viewModel: BookDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.setTitle("")
        appbar.addOnOffsetChangedListener(this)
        val intent: Intent = getIntent()
        id = intent.getStringExtra("id")
        tag = intent.getStringExtra("tag")
        if(tag.equals("BookApiFragment")){
            id?.let { viewModel.start(it) }
            setupObservers()
        }else{
            setNewsData()
        }
    }
    fun setNewsData(){
        mUrl = intent.getStringExtra("url")
        mImg = intent.getStringExtra("img")
        mTitle = intent.getStringExtra("title")
        mDate = intent.getStringExtra("date")
        mSource = intent.getStringExtra("source")
        mAuthor = intent.getStringExtra("author")
        val requestOptions = RequestOptions()
        requestOptions.error(Utils.getRandomDrawbleColor())
        Glide.with(this)
            .load(mImg)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdrop)
        title_on_appbar!!.text = mSource
        subtitle_on_appbar!!.text = mUrl
        date.setText(Utils.DateFormat(mDate))
        tvtitle.setText(mTitle)
        if(intent.hasExtra("tag")){
            time.text = mAuthor
        }else{
            val author: String
            author = if (mAuthor != null) {
                " \u2022 $mAuthor"
            } else {
                ""
            }
            time!!.text = mSource + author + " \u2022 " + Utils.DateToTimeFormat(mDate)
        }

        initWebView(mUrl)
    }

    @SuppressLint("CheckResult")
    fun setBookData(book: Book) {
        mTitle = book.title
        mDate = Utils.DateFormat(book.publishedDate)
        mUrl = book.infoLink
        mSource = book.authors
        val requestOptions = RequestOptions()
        requestOptions.error(Utils.getRandomDrawbleColor())
        Glide.with(this)
            .load(book.thumbnail)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(backdrop)
        title_on_appbar.text = book.authors
        subtitle_on_appbar.text = book.publisher
        date.text = Utils.DateFormat(book.publishedDate)
        tvtitle.text = book.title
        time.text = book.authors
        initWebView(book.infoLink)
    }

    private fun setupObservers() {
        viewModel.book.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d("Test: ", "DetailsBook " + it.data)
                    setBookData(it.data!!)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE

                }
            }
        })
    }

    private fun initWebView(url: String?) {
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        url?.let { webView.loadUrl(it) }
        progress_bar.visibility = View.GONE

    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val maxScroll: Int = appBarLayout.getTotalScrollRange()
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
        if (percentage == 1f && isHideToolbarView) {
            date_behavior!!.visibility = View.GONE
            title_appbar!!.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView
        } else if (percentage < 1f && !isHideToolbarView) {
            date_behavior!!.visibility = View.VISIBLE
            title_appbar!!.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.view_web) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(mUrl)
            startActivity(i)
            return true
        } else if (id == R.id.share) {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plan"
                i.putExtra(Intent.EXTRA_SUBJECT, mSource)
                val body = "$mTitle\n$mUrl\nShare from the App\n"
                i.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(i, "Share with :"))
            } catch (e: Exception) {
                Toast.makeText(this, "Hmm.. Sorry, \nCannot be share", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
