package id.kotlin.foodo.pesan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.pesan.createRiwayat.API_riwayat
import id.kotlin.foodo.pesan.createRiwayat.dataRiwayat
import id.kotlin.foodo.pesan.recycleviewdetailharga.API_detailharga
import id.kotlin.foodo.pesan.recycleviewdetailharga.dataDetailHarga
import id.kotlin.foodo.pesan.recycleviewdetailharga.detailhargaAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class pesanActivity : AppCompatActivity() {

    //recycleview detailharga
    lateinit var recyclerView: RecyclerView
    lateinit var detailhargaAdapter: detailhargaAdapter
    lateinit var datadetailHarga: ArrayList<dataDetailHarga>

    //harga
    lateinit var harga : ArrayList<Int>

    //komponen
    lateinit var backbtn : LinearLayout
    lateinit var tv_totalbayar : TextView
    lateinit var pesanbtn : ImageView
    lateinit var etalamat : EditText

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val apidetailharga = RetrofitHelper.getInstance().create(API_detailharga::class.java)
    val apiriwayat = RetrofitHelper.getInstance().create(API_riwayat::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)

        //init shared preference
        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        //get user id from shared pref
        val idUser = sharedPreference.getString("id_user","").toString()

        //get value yang di intent dari detailActivity
        val food = intent.getStringExtra("food")
        val img = intent.getStringExtra("img")

        //backbtn
        backbtn = findViewById(R.id.backbtnpesan)
        backbtn.setOnClickListener{
            onBackPressed()
        }


        //set recycleview
        recyclerView = findViewById(R.id.detailhargarc)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            val query = "eq.$food"
            val response = apidetailharga.get(token = token, apiKey = apikey , queryfood = query)
            //add dataDetailharga dari API
            datadetailHarga = ArrayList()
            response.body()?.forEach {
                datadetailHarga.add(
                    dataDetailHarga(
                        jumlah = it.angka,
                        bahan = it.bahan,
                        harga = it.harga
                ))
            }
            //add Arraylist Harga dari API untuk menghitung total harga dengan sum()
            harga = ArrayList()
            response.body()?.forEach {
                harga.add(it.harga)
            }

            //set dapater dengan data ke recycleview
            detailhargaAdapter = detailhargaAdapter(datadetailHarga)
            recyclerView.adapter = detailhargaAdapter

            //hitung total harga
            val totalharga = harga.sum().toString()
            tv_totalbayar = findViewById(R.id.tv_totalbayar)
            tv_totalbayar.text = "Rp.$totalharga"

        }


        //pesan btn (create database riwayat + Loading progress)
        pesanbtn = findViewById(R.id.pesanbtn)
        pesanbtn.setOnClickListener{
            etalamat = findViewById(R.id.et_alamat)
            val alamat = etalamat.text.toString()

            //validasi jika alamat kosong
            if (alamat.isNullOrEmpty()) {
                etalamat.error = "Isi Alamat Terlebih Dahulu"
            } else {
                val data = dataRiwayat(food!! , alamat!! , img!!, idUser!!)
                CoroutineScope(Dispatchers.Main).launch {
                    apiriwayat.createriwayat(apiKey = apikey , token = token , data = data)
                }
                val intent = Intent(applicationContext , loadingActivity::class.java)
                startActivity(intent)
            }

        }


    }
}