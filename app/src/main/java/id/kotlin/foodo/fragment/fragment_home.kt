package id.kotlin.foodo.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.login
import id.kotlin.foodo.recycleviewKategori.API_kategori
import id.kotlin.foodo.recycleviewKategori.dataKategori
import id.kotlin.foodo.recycleviewKategori.kategoriAdapter
import id.kotlin.foodo.recycleviewRekomendasi.API_rekomendasi
import id.kotlin.foodo.recycleviewRekomendasi.dataRekomendasi
import id.kotlin.foodo.recycleviewRekomendasi.rekomendasiAdapter
import id.kotlin.foodo.recycleviewTrending.API_trending
import id.kotlin.foodo.recycleviewTrending.dataTrending
import id.kotlin.foodo.recycleviewTrending.trendingAdapter
import id.kotlin.foodo.userAPI.API_akun
import id.kotlin.foodo.userAPI.dataRegAkun
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class fragment_home : Fragment() {

    //recycleview kategori
    lateinit var recyclekat : RecyclerView
    lateinit var kategoriAdapter: kategoriAdapter
    lateinit var dataKategori: ArrayList<dataKategori>

    //recycleview trending
    lateinit var recycletrend : RecyclerView
    lateinit var trendingadapter : trendingAdapter
    lateinit var datatrending : ArrayList<dataTrending>

    //recycleview rekomendasi
    lateinit var recyclerekomend : RecyclerView
    lateinit var rekomendasiAdapter: rekomendasiAdapter
    lateinit var datarekomend : ArrayList<dataRekomendasi>


    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val apikat = RetrofitHelper.getInstance().create(API_kategori::class.java)
    val apirekomend = RetrofitHelper.getInstance().create(API_rekomendasi::class.java)
    val apitrend = RetrofitHelper.getInstance().create(API_trending::class.java)
    val apiakun = RetrofitHelper.getInstance().create(API_akun::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //init
        val btnlogout = view.findViewById<ImageView>(R.id.logout)
        val tvuser = view.findViewById<TextView>(R.id.tv_user)




        //set text username
        val sharedPreference =  activity?.getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        val email = sharedPreference?.getString("email" , "").toString()

        CoroutineScope(Dispatchers.Main).launch {
            val query = "eq.$email"
            val response = apiakun.get(token = token, apiKey = apikey, query = query)
            response.body()?.forEach {
                tvuser.text = it.username
            }
        }



        //logout button
        btnlogout.setOnClickListener {
            val alert = AlertDialog.Builder(activity)
            alert.setTitle("Logout")
            alert.setMessage("Apakah anda yakin ingin keluar?")
            alert.setPositiveButton("Iya" , { dialog : DialogInterface?, which : Int ->
                //menghapus shared pref email untuk mengakhiri sesi
                var editor = sharedPreference?.edit()
                editor?.clear()
                editor?.remove("email")
                editor?.remove("id_user")
                editor?.commit()

                //intent ke login
                val intent = Intent(activity , login::class.java)
                startActivity(intent)
                activity?.finish()
            })
            alert.setNegativeButton("Tidak" , { dialog : DialogInterface?, which : Int ->})
            alert.show()
        }


        //banner iklan / image slider
        val iklan = ArrayList<SlideModel>()
        iklan.add(SlideModel(R.drawable.iklan_1))
        iklan.add(SlideModel(R.drawable.iklan_2))
        iklan.add(SlideModel(R.drawable.iklan_4))
        iklan.add(SlideModel(R.drawable.iklan_3))
        val imageslider = view.findViewById<ImageSlider>(R.id.iklan)
        imageslider.setImageList(iklan)

        //set recycleview kategori
        recyclekat = view.findViewById(R.id.recycleKategori)
        recyclekat.setHasFixedSize(true)
        recyclekat.layoutManager = LinearLayoutManager(activity , LinearLayoutManager.HORIZONTAL ,false)
        CoroutineScope(Dispatchers.Main).launch {
            dataKategori = ArrayList()
            val response = apikat.get(token = token , apiKey = apikey)
            //add datakategori dari API
            response.body()?.forEach {
                dataKategori.add(dataKategori(
                    idkat = it.id_kategori,
                    kategori = it.kategori,
                    imgrc = it.img,
                    imgheader = it.header
                ))
            }
            //pasang adapter dengan data dan recycleview
            kategoriAdapter = kategoriAdapter(dataKategori)
            recyclekat.adapter = kategoriAdapter
        }


        //set recycleview trending
        recycletrend = view.findViewById(R.id.recycleTrending)
        recycletrend.setHasFixedSize(true)
        recycletrend.layoutManager = LinearLayoutManager(activity , LinearLayoutManager.HORIZONTAL ,false)
        CoroutineScope(Dispatchers.Main).launch {
            val response = apitrend.get(token = token , apiKey = apikey)
            //add datatrending dengan API
            datatrending = ArrayList()
            response.body()?.forEach {
                datatrending.add(dataTrending(
                    idfood = it.id,
                    food = it.food,
                    imgrc = it.imgrc,
                    img = it.img,
                    imgrckat = it.imgrckat
                ))
            }
            //set adapter dengan data dan pasang ke recycleview
            trendingadapter = trendingAdapter(datatrending)
            recycletrend.adapter = trendingadapter
        }



        //set recycleview rekomendasi
        recyclerekomend = view.findViewById(R.id.recycleRekomendasi)
        recyclerekomend.setHasFixedSize(true)
        recyclerekomend.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        CoroutineScope(Dispatchers.Main).launch {
            val response = apirekomend.get(token = token , apiKey = apikey)
            //add datatrending dengan API
            datarekomend = ArrayList()
            response.body()?.forEach {
                datarekomend.add(dataRekomendasi(
                    id = it.id,
                    food = it.food,
                    imgrc = it.imgrc,
                    img = it.img,
                    imgrckat = it.imgrckat
                ))
            }
            //set adapter dengan data dan pasang ke recycleview
            rekomendasiAdapter = rekomendasiAdapter(datarekomend)
            recyclerekomend.adapter = rekomendasiAdapter
        }


        return view
    }
}