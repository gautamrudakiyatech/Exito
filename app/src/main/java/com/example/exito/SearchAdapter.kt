package com.example.exito

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private var docList: List<Doc>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textBreeds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_data, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val doc = docList[position]
        holder.textTitle.text = doc.title
    }

    override fun getItemCount(): Int = docList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Doc>) {
        docList = newList
        notifyDataSetChanged()
    }
}
