package com.mabbar.myapplication.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mabbar.myapplication.ArticlesItem
import com.mabbar.myapplication.R
import org.w3c.dom.Text

class NewsAdapter(var items:List<ArticlesItem?>?) :RecyclerView.Adapter<NewsAdapter.ViewHlder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHlder {
        val View=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return ViewHlder(View)
    }
    override fun onBindViewHolder(holder: ViewHlder, position: Int) {
        val item=items?.get(position)
        holder.title.setText(item?.title)
        holder.outher.setText(item?.author)
        holder.date_time.setText(item?.publishedAt)
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.image)


    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }
    fun changeData(data:List<ArticlesItem?>?){
        items=data
        notifyDataSetChanged()

    }




    class ViewHlder (itemview: View):RecyclerView.ViewHolder(itemview){
        val title:TextView=itemview.findViewById(R.id.tittle)
        val outher:TextView=itemview.findViewById(R.id.outhar)
        val image:ImageView=itemview.findViewById(R.id.image)
        val date_time:TextView=itemview.findViewById(R.id.datetime)

    }
}