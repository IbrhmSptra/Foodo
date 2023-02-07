package id.kotlin.foodo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.DetailKategori.API_detailkat
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.recycleviewBookmark.API_bookmark
import id.kotlin.foodo.recycleviewBookmark.bookmarkAdapter
import id.kotlin.foodo.recycleviewBookmark.databookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class fragment_bookmark : Fragment() {

    //recycleview bookmark
    lateinit var recyclerView: RecyclerView
    lateinit var bookmarkAdapter: bookmarkAdapter
    lateinit var databookmark: ArrayList<databookmark>

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_bookmark::class.java)

    //komponen
    lateinit var tvkosong : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)

        //komponen
        tvkosong = view.findViewById(R.id.kosong)

        //set recycleview
        recyclerView = view.findViewById(R.id.bookmarkrc)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            val response = api.get(token = token , apiKey = apikey)
            //add data bookmark dari API
            databookmark = ArrayList()
            response.body()?.forEach {
                databookmark.add(databookmark(
                    id = it.id,
                    food = it.food,
                    img = it.img,
                    imgrc = it.imgrc
                ))
            }
            //pasang adapter dengan data ke recycleview
            bookmarkAdapter = bookmarkAdapter(databookmark)
            recyclerView.adapter = bookmarkAdapter

            if (databookmark.isEmpty()){
                recyclerView.visibility = View.INVISIBLE
                recyclerView.isEnabled = false
                tvkosong.visibility = View.VISIBLE
            }
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            val response = api.get(token = token , apiKey = apikey)
            //add data bookmark dari API
            databookmark = ArrayList()
            response.body()?.forEach {
                databookmark.add(databookmark(
                    id = it.id,
                    food = it.food,
                    img = it.img,
                    imgrc = it.imgrc
                ))
            }
            //pasang adapter dengan data ke recycleview
            bookmarkAdapter = bookmarkAdapter(databookmark)
            recyclerView.adapter = bookmarkAdapter

            if (databookmark.isEmpty()){
                recyclerView.visibility = View.INVISIBLE
                recyclerView.isEnabled = false
                tvkosong.visibility = View.VISIBLE
            }
        }
    }

}