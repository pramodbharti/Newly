package com.db.foody.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.db.foody.R
import com.db.foody.extensions.inflate
import com.db.foody.data.model.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_list.view.*


class RecipesAdapter(
    var recipes: List<Recipe>
) : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.inflate(R.layout.food_list, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val image = v.image
        private val title = v.title
        private val score = v.rank

        fun bindItems(recipe: Recipe) {
            Picasso.get()
                .load((recipe.imageUrl).replace("http","https"))
                .placeholder(R.drawable.recipe_place_holder)
                .error(R.drawable.recipe_error)
                .resize(400,300)
                .centerCrop()
                .into(image)
            title.text=recipe.title
            score.text = recipe.socialRanking.toInt().toString()
        }
    }
}