package com.db.newly.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.db.newly.R
import com.db.newly.data.model.ImagePost
import com.db.newly.extensions.inflate
import kotlinx.android.synthetic.main.view_list.view.*


class ImagesAdapter(
    var images: List<ImagePost>
) : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.inflate(R.layout.view_list, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(images[position])
    }

    override fun getItemCount(): Int = images.size

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        private val imageView = view.image
        private val title = view.title
        private val score = view.rank

        fun bindItems(image: ImagePost) {
            Glide.with(view)
                .load(image.images?.first()?.link)
                .placeholder(R.drawable.ic_image_24dp)
                .error(R.drawable.ic_image_24dp)
                .into(imageView)

            title.text=image.title
            score.text = image.score.toString()
        }
    }
}