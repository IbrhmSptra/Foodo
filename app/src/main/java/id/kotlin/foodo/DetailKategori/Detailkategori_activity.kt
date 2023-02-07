package id.kotlin.foodo.DetailKategori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import kotlinx.android.synthetic.main.activity_detailkategori.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Detailkategori_activity : AppCompatActivity() {

    //recycleview
    lateinit var recyclerView: RecyclerView
    lateinit var detailkatAdapter: detailkatAdapter
    lateinit var datadetailkat: ArrayList<datadetailkat>

    //komponen
    lateinit var imgheader : ImageView
    lateinit var backbtn : LinearLayout

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_detailkat::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailkategori)

        //get value yang di intent dari kategori
        var header = intent.getStringExtra("header")
        var id = intent.getStringExtra("id")
        //set header sesuai dengan yg di klik
        imgheader = findViewById(R.id.header)
        Glide.with(this).load(header).into(imgheader)


        //backbtn
        backbtn = findViewById(R.id.backbtnkatdetail)
        backbtn.setOnClickListener{
            onBackPressed()
        }

        //set recyclview
        recyclerView = findViewById(R.id.katdetailrc)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            val query = "eq.$id"
            val response = api.get(token = token , apiKey = apikey , kategori = query)
            datadetailkat = ArrayList()
            //add datadetailkat dar API
            response.body()?.forEach {
                datadetailkat.add(datadetailkat(
                    id = it.id,
                    food = it.food,
                    img = it.img,
                    imgrc = it.imgrckat
                ))
            }
            //set adapter dengan data dan pasang ke recycleview
            detailkatAdapter = detailkatAdapter(datadetailkat)
            recyclerView.adapter = detailkatAdapter
        }


    }
}