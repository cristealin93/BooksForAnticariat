package com.anticariat.friendlybooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ItemBooksBinding
import com.anticariat.friendlybooks.model.Books

class BooksAdapter(
    var booksList:ArrayList<Books>): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    inner class BooksViewHolder(var v: ItemBooksBinding):RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val inflate =LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_books,parent,false)
        val v= DataBindingUtil.inflate<ItemBooksBinding>(inflate, R.layout.item_books,parent,false)
        return BooksViewHolder(v)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.v.isBooks=booksList[position]

    }

    override fun getItemCount(): Int {
       return booksList.size
    }


}