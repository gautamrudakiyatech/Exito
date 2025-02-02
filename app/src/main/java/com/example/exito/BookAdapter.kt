package com.example.exito

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private var entryList: List<ReadingLogEntry>) :
    RecyclerView.Adapter<BookAdapter.ReadingLogViewHolder>() {

    class ReadingLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textBreeds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingLogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_data, parent, false)
        return ReadingLogViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadingLogViewHolder, position: Int) {
        val entry = entryList[position]
        val work = entry.work

        holder.textTitle.text = work.title
    }

    override fun getItemCount() = entryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ReadingLogEntry>) {
        entryList = newList
        notifyDataSetChanged()
    }
}
