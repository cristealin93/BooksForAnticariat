package com.anticariat.friendlybooks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anticariat.friendlybooks.databinding.FragmentProductsBinding
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.Books
import com.google.firebase.database.DatabaseReference

//import com.anticariat.friendlybooks.activites.ui.home.HomeViewModel


class ProductsFragment : Fragment() {

    lateinit var mDataBase: DatabaseReference
    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnButton.setOnClickListener {  addDataToRealTimeDB()  }



    }

    private fun addDataToRealTimeDB() {
        val books=Books(binding.etTest.text.toString(),"val","test")
        FireStoreClass().addNewBooks(books)
    }

}