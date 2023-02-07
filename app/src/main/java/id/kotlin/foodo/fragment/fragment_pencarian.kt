package id.kotlin.foodo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.foodo.R
import id.kotlin.foodo.RetrofitHelper
import id.kotlin.foodo.recycleviewPencarian.API_search
import id.kotlin.foodo.recycleviewPencarian.dataSearch
import id.kotlin.foodo.recycleviewPencarian.searchAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class fragment_pencarian : Fragment() {

    //recycleview search
    lateinit var recyclerView: RecyclerView
    lateinit var datasearch : ArrayList<dataSearch>
    lateinit var tempdatasearch : ArrayList<dataSearch>
    lateinit var adapter: searchAdapter

    //searchkomponen
    lateinit var searchView : SearchView

    //API
    val apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFxbmtlZXV0dGFjeWZjdGdlYnpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxNTgsImV4cCI6MTk4NTUzMTE1OH0.ZK9H5UcqMWlrrbbqzEnHVhJbdSi7x5CQRVsdB92Kt8c"
    val token = "Bearer $apikey"
    val api = RetrofitHelper.getInstance().create(API_search::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pencarian, container, false)

        //set recycleview
        recyclerView = view.findViewById(R.id.recycleSearch)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL , false)
        datasearch = ArrayList()
        tempdatasearch = ArrayList()
        CoroutineScope(Dispatchers.Main).launch {
            val response = api.get(token = token , apiKey = apikey)
            //add datasearch dengan api
            response.body()?.forEach {
                datasearch.add(dataSearch(
                    id = it.id,
                    food = it.food,
                    img = it.img,
                    imgrc = it.imgrc,
                    imgrckat = it.imgrckat
                ))
            }
            //copy all value dri data search ke temp
            tempdatasearch.addAll(datasearch)
            //init search komponen
            searchView = view.findViewById(R.id.search)
            searchView.clearFocus()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    tempdatasearch.clear()
                    val searchtext = newText!!.toLowerCase(Locale.getDefault())

                    if (searchtext.isNotEmpty()) {
                        datasearch.forEach{
                            if (it.food.toLowerCase(Locale.getDefault()).contains(searchtext)) {
                                tempdatasearch.add(it)
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()

                    }else {
                        tempdatasearch.clear()
                        tempdatasearch.addAll(datasearch)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }

                    return false
                }

            })
            adapter = searchAdapter(tempdatasearch)
            recyclerView.adapter = adapter
        }



        return view
    }


}