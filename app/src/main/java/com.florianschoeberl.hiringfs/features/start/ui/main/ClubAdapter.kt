package com.florianschoeberl.hiringfs.features.start.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.model.Club
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions



class ClubAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var clubs = emptyList<Club>() // Cached copy of clubs
    val options = RequestOptions()
            .error(R.drawable.club_placeholder)

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.text_name)
        val country: TextView = itemView.findViewById(R.id.text_country)
        val value: TextView = itemView.findViewById(R.id.text_value)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ClubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val current = clubs[position]
        holder.name.text = current.name
        holder.country.text = current.country
        holder.value.text = holder.itemView.resources.getString(R.string.club_value, current.value) //todo
        Glide.with(holder.itemView.context) //todo
                .load(current.image)
                .apply(options)
                .into(holder.image)
    }

    internal fun setClubs(clubs: List<Club>) {
        this.clubs = clubs
        notifyDataSetChanged()
    }

    override fun getItemCount() = clubs.size
}