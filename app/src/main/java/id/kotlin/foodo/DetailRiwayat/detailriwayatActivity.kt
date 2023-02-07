package id.kotlin.foodo.DetailRiwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.pesan.recycleviewdetailharga.API_detailharga
import id.kotlin.foodo.pesan.recycleviewdetailharga.dataDetailHarga
import id.kotlin.foodo.pesan.recycleviewdetailharga.detailhargaAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class detailriwayatActivity : AppCompatActivity() {

    //recycleview detail harga
    lateinit var recyclerView: RecyclerView
    lateinit var detailhargaAdapter: detailhargaAdapter
    lateinit var datadetailHarga: ArrayList<dataDetailHarga>

    //komponen
    lateinit var backbtn : LinearLayout
    lateinit var imgfood : ImageView
    lateinit var tvfood : TextView
    lateinit var tvalamat : TextView
    lateinit var tvharga : TextView

    //harga
    lateinit var harga : ArrayList<Int>

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val apidetailharga = RetrofitHelper.getInstance().create(API_detailharga::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailriwayat)

        //backbtn
        backbtn = findViewById(R.id.backbtnDT)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        //get value yang sudah di intent dari fragment riwayat
        val food = intent.getStringExtra("food")
        val alamat = intent.getStringExtra("alamat")
        val img = intent.getStringExtra("img")

        //set img alamat dan nama food sesuai sama apa yang di klik
        //initilized
        tvfood = findViewById(R.id.tvfoodDT)
        imgfood = findViewById(R.id.imgDT)
        tvalamat = findViewById(R.id.tvalamatDT)
        //set
        tvalamat.text = alamat
        tvfood.text = food
        Glide.with(this).load(img).into(imgfood)


        //set recycleview
        recyclerView = findViewById(R.id.detailhargarcDT)
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

            //add Arraylist Harga dari API
            harga = ArrayList()
            response.body()?.forEach {
                harga.add(it.harga)
            }

            //hitung total harga
            val totalharga = harga.sum().toString()
            tvharga = findViewById(R.id.tv_totalDT)
            tvharga.text = "Rp.$totalharga"

            //set dapater dengan data ke recycleview
            detailhargaAdapter = detailhargaAdapter(datadetailHarga)
            recyclerView.adapter = detailhargaAdapter

        }

    }
}