package com.example.alrol.apkmirrorsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList
import com.squareup.picasso.Picasso


/**
 * Created by alrol on 2017-06-21.
 */
class SearchResultsAdapter(val searchResults: ArrayList<AppInfo>): RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>() {



    class SearchResultsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var appNameText = view.findViewById(R.id.appName) as TextView
        var appDevText = view.findViewById(R.id.appDev) as TextView
        var appUploadedDateText = view.findViewById(R.id.appUploadedDate) as TextView
        var appDownloadNumberText = view.findViewById(R.id.appDownloadNumber) as TextView
        var appSizeText = view.findViewById(R.id.appSize) as TextView
        var appImage = view.findViewById(R.id.appImg) as ImageView
//        var downloadButton = view.findViewById(R.id.downloadButton) as ImageButton

        val dlScraper = DownloadLinkScraperTask()

        fun setOnClick(item: AppInfo){
            appNameText.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                startActivity(view.context, browserIntent, Bundle())
            }

            appImage.setOnClickListener {
                dlScraper.delegate = this
                dlScraper.execute(item.url)
                Toast.makeText(view.context,"Fetching download link", Toast.LENGTH_LONG)
            }
        }

        fun linkReady(downloadLink: String){
            val browserDownloadIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadLink))
            startActivity(view.context, browserDownloadIntent, Bundle())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsAdapter.SearchResultsViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.search_results_row_card, parent, false)

        return SearchResultsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {

        var app = searchResults[position]

        holder.setOnClick(app)

        Picasso.with(holder.appImage.context).load(app.imageUrl).into(holder.appImage)

        holder.appNameText.text = app.title
        holder.appDevText.text = app.dev
        holder.appUploadedDateText.text = "Uploaded: " + app.dateUploaded
        holder.appSizeText.text = app.fileSize
        holder.appDownloadNumberText.text = app.downloads + " downloads"

    }

    override fun getItemCount(): Int {
        return searchResults.size
    }



}