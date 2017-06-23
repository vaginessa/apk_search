package com.example.alrol.apkmirrorsearch

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*

var searchString = ""
var searchFor = ""
var searchInput = ""
var searchResultArray = ArrayList<AppInfo>()

class MainActivity : Activity(), AsyncResponse {
    lateinit var searchUrl : String
    lateinit var searchField: SearchView
    lateinit var searchButton: Button
    lateinit var radioApk : RadioButton
    lateinit var radioApp : RadioButton
    lateinit var radioDev : RadioButton
    lateinit var homeProgressBar : ProgressBar
    lateinit var homeRecyclerView: RecyclerView
    lateinit var homeRecyclerViewLayoutManager: RecyclerView.LayoutManager
    lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    var scraper = HomeScraperTask()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(R.id.search_button) as Button
        searchField = findViewById(R.id.search) as SearchView
        radioApk = findViewById(R.id.apk_radio_button) as RadioButton
        radioApp = findViewById(R.id.app_radio_button) as RadioButton
        radioDev = findViewById(R.id.dev_radio_button) as RadioButton
        homeProgressBar = findViewById(R.id.homeRecyclerViewProgress) as ProgressBar
        homeRecyclerView = findViewById(R.id.homeRecyclerView) as RecyclerView

        scraper.delegate = this
        scraper.execute()

        searchResultArray.clear()


    }


    fun sendSearch (view: View) {

        if (radioApk.isChecked) {
            searchUrl = "https://www.apkmirror.com/?post_type=app_release&searchtype=apk&s="
            searchFor = "apk"
        }
        if (radioApp.isChecked) {
            searchUrl = "http://www.apkmirror.com/?post_type=app_release&searchtype=app&s="
            searchFor = "app"
        }
        if (radioDev.isChecked) {
            searchUrl = "https://www.apkmirror.com/?post_type=app_release&searchtype=dev&s="
            searchFor = "dev"
        }

        searchInput = searchField.query.toString()
        searchString = searchUrl+searchInput

        if (searchInput != "") {

            val scraperFragmentIntent = Intent(this, ScraperFragment::class.java)
            startActivity(scraperFragmentIntent)
        } else {
            searchField.setBackgroundColor(Color.RED)
        }


    }

    override fun processFinish(result: ArrayList<AppInfo>){

        homeRecyclerViewLayoutManager = LinearLayoutManager(this)
        homeRecyclerView.layoutManager = homeRecyclerViewLayoutManager

        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(result)
        homeRecyclerView.adapter =  homeRecyclerViewAdapter

        homeRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        homeProgressBar.visibility = View.GONE
    }




}
