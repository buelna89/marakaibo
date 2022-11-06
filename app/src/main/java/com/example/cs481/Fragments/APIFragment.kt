package com.example.cs481.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cs481.*
import com.example.cs481.R
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class APIFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_display, container, false)


        val root = inflater.inflate(R.layout.fragment_api, container, false)

        val servicegenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = servicegenerator.getTodos()
        val apibutton = root.findViewById<Button>(R.id.api)

        val db = FirebaseFirestore.getInstance()

        apibutton.setOnClickListener() {
            call.enqueue(object : retrofit2.Callback<MutableList<PostModel>> {
                override fun onResponse(
                    call: Call<MutableList<PostModel>>,
                    response: Response<MutableList<PostModel>>
                ) {
                    if (response.isSuccessful) {
                        //val db = FirebaseFirestore.getInstance()
                        val dataentry: MutableMap<String, Any> = HashMap()
                        Log.e("Success", "Recieved")
                        response.body()?.forEach {
                            dataentry["userid"] = it.userId.toString()
                            dataentry["id"] = it.id.toString()
                            dataentry["title"] = it.title.toString()
                            dataentry["complete"] = it.completed.toString()

                            db.collection("midterm")
                                .add(dataentry)
                                .addOnSuccessListener {
                                    Log.d("dataentry", "Success")
                                }
                        }
                    }
                }

                override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("Error.", t.message.toString())
                }

            })
        }
        return root
    }
    companion object {

        private const val API_DISPLAY = "display"

        @JvmStatic
        fun newInstance(display: String): APIFragment {
            return APIFragment().apply {
                arguments = Bundle().apply {
                    putString(APIFragment.API_DISPLAY, display)
                }
            }
        }
    }
}
