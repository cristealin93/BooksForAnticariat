package com.anticariat.friendlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.anticariat.friendlybooks.R
//import com.anticariat.friendlybooks.activites.ui.home.HomeViewModel


class ProductsFragment : Fragment() {

   // private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // homeViewModel =ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_products, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
     //   homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "Products Fragment"

        return root
    }

}