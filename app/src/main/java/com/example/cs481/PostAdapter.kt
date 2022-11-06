package com.example.cs481


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs481.R


class PostAdapter(val postModel: MutableList<PostModel>, private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apipost, parent, false)
        return PostViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bindView(postModel[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return postModel.size
    }

}

class PostViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit): RecyclerView.ViewHolder (itemView),
    View.OnClickListener {
    val tvID: TextView = itemView.findViewById(R.id.tvid)
    val tvUSERID: TextView = itemView.findViewById(R.id.tvBody)
    val tvTITLE: TextView = itemView.findViewById(R.id.tvTitle)

    init {
        itemView.setOnClickListener(this)
    }

    fun bindView(postModel: PostModel, onItemClicked: (position: Int) -> Unit){
        tvID.text = postModel.id
        tvUSERID.text = postModel.userId
        tvTITLE.text = postModel.title

    }

    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }

}