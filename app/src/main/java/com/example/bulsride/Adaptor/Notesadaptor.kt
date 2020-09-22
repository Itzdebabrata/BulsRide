package com.example.bulsride.Adaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsride.Database.Notes
import com.example.bulsride.Itemclick.Itemclick
import com.example.bulsride.R

open class Notesadaptor(private val list:List<Notes>, private val itemclick: Itemclick) : RecyclerView.Adapter<Notesadaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.adaptor_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var notess=list[position]
        var titles=notess.title
        var desc=notess.description
        var imagepath=notess.imagepath
        val photouri: Uri? = Uri.parse(imagepath)
        holder.title.setText(titles)
        holder.desc.setText(desc)
        holder.profile.setImageURI(photouri)
        holder.checked.isChecked= notess.istaskcomplet!!
        holder.itemView.setOnClickListener{itemclick.onclick(notess)}
        holder.checked.setOnCheckedChangeListener(object  : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notess.istaskcomplet=isChecked
                itemclick.onupdate(notess)
            }
        })
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title=itemView.findViewById<TextView>(R.id.titleshow)
        var desc=itemView.findViewById<TextView>(R.id.descshow)
        var profile=itemView.findViewById<ImageView>(R.id.profile)
        var checked=itemView.findViewById<CheckBox>(R.id.checked)
    }
}