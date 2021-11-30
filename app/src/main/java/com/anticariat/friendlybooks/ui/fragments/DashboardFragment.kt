package com.anticariat.friendlybooks.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Adapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.adapter.BooksAdapter
import com.anticariat.friendlybooks.databinding.ActivityDashBoardBinding.inflate
import com.anticariat.friendlybooks.databinding.ActivityLoginBinding
import com.anticariat.friendlybooks.databinding.FragmentDashboardBinding
import com.anticariat.friendlybooks.model.Books
import com.anticariat.friendlybooks.ui.activites.SettingsActivity
import com.google.firebase.database.*

//import com.anticariat.friendlybooks.activites.ui.dashboard.DashboardViewModel


class DashboardFragment : Fragment() {

    lateinit var mDataBase:DatabaseReference
    private lateinit var booksList:ArrayList<Books>
    private lateinit var mAdapter:BooksAdapter

    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksList = arrayListOf<Books>()
        mAdapter = BooksAdapter(booksList)

//
//        binding.rvBooks.apply {
//            layoutManager = LinearLayoutManager(activity)
//            setHasFixedSize(true)
//        }
        binding.rvBooks.layoutManager= LinearLayoutManager(activity)
        binding.rvBooks.setHasFixedSize(true)

        getDataBooks()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id=item.itemId
        when(id){
            R.id.action_settings->
            {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

private fun getDataBooks(){
    mDataBase = FirebaseDatabase.getInstance("https://anticariatapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Books")
    mDataBase.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                for (booksSnapshot in snapshot.children){
                    val books = booksSnapshot.getValue(Books::class.java)
                    booksList.add(books!!)
                    binding.rvBooks.adapter = mAdapter
                }

            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(activity,
                error.message, Toast.LENGTH_SHORT).show()

        }

    })



}
}