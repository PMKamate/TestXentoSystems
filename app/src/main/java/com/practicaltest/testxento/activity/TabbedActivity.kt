package com.practicaltest.testxento.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.practicaltest.testxento.R
import com.practicaltest.testxento.adapter.TabPagerAdapter
import com.practicaltest.testxento.fragment.book.BookApiFragment
import com.practicaltest.testxento.fragment.news.NewsApiFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabbedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)
        val tabPagerAdapter = TabPagerAdapter(supportFragmentManager)
        tabPagerAdapter.addFragment(NewsApiFragment(), "News")
        tabPagerAdapter.addFragment(BookApiFragment(), "Book")
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setTabTextColors(Color.GRAY, Color.WHITE); // set the tab text colors for the both states of the tab.

        tabs.setupWithViewPager(viewPager)
    }
}