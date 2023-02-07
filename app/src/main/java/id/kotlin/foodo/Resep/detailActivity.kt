package id.kotlin.foodo.Resep

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.R
import id.kotlin.foodo.Resep.bookmark.API_detailbookmark
import id.kotlin.foodo.Resep.bookmark.createbookmark
import id.kotlin.foodo.Resep.bookmark.datafood
import id.kotlin.foodo.Resep.recycleMarimasak.API_marimasak
import id.kotlin.foodo.Resep.recycleMarimasak.dataMarimasak
import id.kotlin.foodo.Resep.recycleMarimasak.marimasakAdapter
import id.kotlin.foodo.Resep.recycleviewBahan.API_bahan
import id.kotlin.foodo.Resep.recycleviewBahan.bahanAdapter
import id.kotlin.foodo.Resep.recycleviewBahan.dataBahan
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.pesan.pesanActivity
import id.kotlin.foodo.recycleviewBookmark.API_bookmark
import kotlinx.android.synthetic.main.activity_detailkategori.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class detailActivity : AppCompatActivity() {

    //recycleview Bahan
    lateinit var recyclebahan : RecyclerView
    lateinit var adapterbahan : bahanAdapter
    lateinit var databahan : ArrayList<dataBahan>

    //recycleview marimasak
    lateinit var recyclemarimasak : RecyclerView
    lateinit var adaptermarimasak : marimasakAdapter
    lateinit var dataMarimasak: ArrayList<dataMarimasak>

    //komponen
    lateinit var tvfood : TextView
    lateinit var imgfood : ImageView
    lateinit var backbtn : LinearLayout
    lateinit var bookmark : ImageView
    lateinit var bookmarkglow : ImageView
    lateinit var pesanbtn : ImageView

    //bookmark
    lateinit var foodbookmarked : ArrayList<String>


    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val apibahan = RetrofitHelper.getInstance().create(API_bahan::class.java)
    val apimarimasak = RetrofitHelper.getInstance().create(API_marimasak::class.java)
    val apibookmark =RetrofitHelper.getInstance().create(API_detailbookmark::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //get nama food , img food dan imgrckat dari intent
        val food = intent.getStringExtra("food")
        val img = intent.getStringExtra("img")
        val imgrckat = intent.getStringExtra("imgrckat")

        //set tvfood dan imgfood agar sesuai dengan apa yg di intent
        tvfood = findViewById(R.id.tv_fooddetail)
        imgfood = findViewById(R.id.imgfooddetail)
        tvfood.text = food
        Glide.with(this).load(img).into(imgfood)

        //back btn
        backbtn = findViewById(R.id.backbtndetail)
        backbtn.setOnClickListener {
            onBackPressed()
        }

        //pesanbtn intent to pesanactivity lempar food dan img
        pesanbtn = findViewById(R.id.pesanbahanbtn)
        pesanbtn.setOnClickListener {
            val intent = Intent(this , pesanActivity::class.java)
            intent.putExtra("food" , food)
            intent.putExtra("img" , img)
            startActivity(intent)
        }

        //seleksi apakah food sudah pernah di bookmark atau belum kalau sudah langsung glow kalau belum normal
        CoroutineScope(Dispatchers.Main).launch {
            val response = apibookmark.getfoodbookmark(token = token , apiKey = apikey)
            //add foodbookmarked dari API
            foodbookmarked = ArrayList()
            response.body()?.forEach {
                foodbookmarked.add(it.food)
            }
            //logic seleksi
            if (foodbookmarked.contains(food)){
                //glow
                bookmark.isEnabled = false
                bookmark.visibility = View.GONE
                bookmarkglow.visibility = View.VISIBLE
                bookmarkglow.isEnabled = true
            } else{
                //no glow / normal
                bookmarkglow.isEnabled = false
                bookmarkglow.visibility = View.GONE
                bookmark.isEnabled = true
                bookmark.visibility = View.VISIBLE
            }
        }


        //bookmark
        bookmark = findViewById(R.id.bookmark)
        bookmarkglow = findViewById(R.id.bookmarkglow)
        //create
        bookmark.setOnClickListener {
            //toogle logic
            bookmark.isEnabled = false
            bookmark.visibility = View.GONE
            bookmarkglow.visibility = View.VISIBLE
            bookmarkglow.isEnabled = true

            CoroutineScope(Dispatchers.Main).launch {
                val data = createbookmark(food = food!!, img = img!! , imgrc = imgrckat!!)
                val response = apibookmark.createbookmark(token = token , apiKey = apikey , data = data)
            }
        }
        //delete
        bookmarkglow.setOnClickListener {
            //toogle logic
            bookmarkglow.isEnabled = false
            bookmarkglow.visibility = View.GONE
            bookmark.isEnabled = true
            bookmark.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.Main).launch {
                val query = "eq.$food"
                apibookmark.deletebookmark(token = token , apiKey = apikey, foodbookmark = query)
            }
        }


        //set recycleview bahan
        recyclebahan = findViewById(R.id.bahanrc)
        recyclebahan.setHasFixedSize(true)
        recyclebahan.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            var query = "eq.$food"
            val response = apibahan.get(token = token , apiKey = apikey , queryfood = query)
            //add databahan dari API
            databahan = ArrayList()
            response.body()?.forEach {
                databahan.add(dataBahan(
                    jumlah = it.angka,
                    bahan = it.bahan,
                    harga = it.harga
                ))
            }
            //set adapter dengan data ke recycleview
            adapterbahan = bahanAdapter(databahan)
            recyclebahan.adapter = adapterbahan
        }



        //set recycleview marimasak
        recyclemarimasak = findViewById(R.id.marimasakrc)
        recyclemarimasak.setHasFixedSize(true)
        recyclemarimasak.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)
        CoroutineScope(Dispatchers.Main).launch {
            var query = "eq.$food"
            val response = apimarimasak.get(token = token , apiKey = apikey , queryfood = query)
            //add databahan dari API
            dataMarimasak = ArrayList()
            response.body()?.forEach {
                dataMarimasak.add(
                    dataMarimasak(
                        langkah = it.tahap,
                        cara = it.cara
                ))
            }
            //set adapter dengan data ke recycleview
            adaptermarimasak = marimasakAdapter(dataMarimasak)
            recyclemarimasak.adapter = adaptermarimasak
        }



    }
}