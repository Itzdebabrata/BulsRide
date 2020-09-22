package com.example.bulsride.Adaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsride.R
import com.example.bulsride.variables.Article
import com.example.bulsride.variables.Source

class Menuadaptor(val list:List<Article>):RecyclerView.Adapter<Menuadaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.menulayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog=list[position]
        holder.tvtitle.text=blog.title
        holder.tvsource.text=blog.source.name
        holder.tvdate.text=blog.publishedAt
        val ss=blog.urlToImage
        val photouri: Uri? = Uri.parse(ss)
        holder.menuimg.setImageURI(photouri)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val menuimg=itemView.findViewById<ImageView>(R.id.menuimg)
        val tvtitle=itemView.findViewById<TextView>(R.id.tvid)
        val tvsource=itemView.findViewById<TextView>(R.id.tvsource)
        val tvdate=itemView.findViewById<TextView>(R.id.tvdate)
        val cardview=itemView.findViewById<CardView>(R.id.cardview)
    }
}