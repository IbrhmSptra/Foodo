package id.kotlin.foodo.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.pesan.createRiwayat.API_riwayat
import id.kotlin.foodo.pesan.createRiwayat.dataRiwayat
import id.kotlin.foodo.recyleviewRiwayat.API_rcriwayat
import id.kotlin.foodo.recyleviewRiwayat.datarcriwayat
import id.kotlin.foodo.recyleviewRiwayat.rcriwayatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class fragment_riwayat : Fragment() {

    //recycleview riwayat
    lateinit var recyclerView: RecyclerView
    lateinit var datariwayat : ArrayList<datarcriwayat>
    lateinit var rcriwayatAdapter: rcriwayatAdapter

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_rcriwayat::class.java)

    //komponen
    lateinit var tv_kosong : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_riwayat, container, false)

        //init shared preference
        val sharedPreference =  activity?.getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        //get user id from shared pref
        val idUser = sharedPreference?.getString("id_user","").toString()

        //set recycleview
        recyclerView = view.findViewById(R.id.riwayatrc)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            val query = "eq.$idUser"
            val response = api.get(token = token , apiKey = apikey, id_user = query)
            //add datariwayat dengan API
            datariwayat = ArrayList()
            response.body()?.forEach {
                datariwayat.add(datarcriwayat(
                    food = it.food,
                    alamat = it.alamat,
                    img = it.img
                ))
            }
            //set adapter dengan data ke recycleview
            rcriwayatAdapter = rcriwayatAdapter(datariwayat)
            recyclerView.adapter = rcriwayatAdapter

            //kalau riwayat kosong maka hapus recycleview ganti dengan teks
            if (datariwayat.isNullOrEmpty()) {
                tv_kosong = view.findViewById(R.id.kosong)
                tv_kosong.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }



        return view
    }


}