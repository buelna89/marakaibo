package com.example.cs481.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs481.*
import com.example.cs481.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class DisplayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_display, container, false)


        val root = inflater.inflate(R.layout.fragment_display, container, false)

        val db = FirebaseFirestore.getInstance()
        val datacollect = mutableListOf<PostModel>()
        val recyclerview = root.findViewById<RecyclerView>(R.id.recyclerview)
        val displaybutton = root.findViewById<Button>(R.id.display)
        val FAB = root.findViewById<FloatingActionButton>(R.id.fab)


        FAB.setOnClickListener() {
            val intent = Intent(context, DataEntryActivity::class.java)
            startActivity(intent)
        }

        displaybutton.setOnClickListener() {
            val collect = db.collection("midterm")

            collect.get().addOnSuccessListener { result ->
                result.forEach() {
                    val dataentry = PostModel(
                        it.data["userid"].toString(),
                        it.data["id"].toString(),
                        it.data["title"].toString(),
                        it.data["complete"].toString()
                    )
                    datacollect.add(dataentry)
                }

                Log.d("List: ", datacollect.toString())
                recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PostAdapter(datacollect) {position -> onListItemCLick(position, recyclerview)
                    }
                }
            }


        }
        return root
    }

    private fun onListItemCLick(position: Int, recyclerview: RecyclerView?) {
        val user_id = recyclerview?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.userid)?.text.toString()
        val id = recyclerview?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.id)?.text.toString()
        val to_do = recyclerview?.findViewHolderForAdapterPosition(position)?.itemView?.findViewById<TextView>(R.id.todo)?.text.toString()
        val intent = Intent(context, DataEntryActivity::class.java)
        intent.putExtra("userid", user_id)
        intent.putExtra("id", id)
        intent.putExtra("todo", to_do)
        startActivity(intent)
    }

    companion object {
        private const val display_DISPLAY = "display"

        @JvmStatic
        fun newInstance(display: String): DisplayFragment {
            return DisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(display_DISPLAY, display)
                }
            }
        }
    }
}

