package com.florianschoeberl.hiringfs.features.start.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.florianschoeberl.hiringfs.R
import com.florianschoeberl.hiringfs.model.Club
import com.bumptech.glide.request.RequestOptions
import androidx.core.view.ViewCompat
import com.florianschoeberl.hiringfs.features.start.DetailActivity
import com.florianschoeberl.hiringfs.features.start.MainActivity
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class ClubAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var clubs = emptyList<Club>()
    val options = RequestOptions()
            .placeholder(R.drawable.club_placeholder)
            .error(R.drawable.club_placeholder)

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.text_name
        val country: TextView = itemView.text_country
        val value: TextView = itemView.text_value
        val image: ImageView = itemView.image
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

        ViewCompat.setTransitionName(holder.image, "$current.id")

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context as MainActivity, DetailActivity::class.java)


            intent.putExtra("CURRENT_ITEM", current)
            intent.putExtra("TRANSITION_NAME", ViewCompat.getTransitionName(holder.image))

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as MainActivity, holder.image, ViewCompat.getTransitionName(holder.image)!!) //todo

            context.startActivity(intent, options.toBundle())
        }
    }

    internal fun setClubs(clubs: List<Club>) {
        this.clubs = clubs
        notifyDataSetChanged()
    }

    override fun getItemCount() = clubs.size
}