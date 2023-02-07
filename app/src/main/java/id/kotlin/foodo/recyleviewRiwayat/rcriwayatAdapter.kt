package id.kotlin.foodo.recyleviewRiwayat



import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.DetailRiwayat.detailriwayatActivity
import id.kotlin.foodo.R



class rcriwayatAdapter (private val riwayatlist : ArrayList<datarcriwayat>)
    : RecyclerView.Adapter<rcriwayatAdapter.riwayatviewholder>() {

    class riwayatviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.karturiwayat)
        val imgrc : ImageView = view.findViewById(R.id.imgrcriwayat)
        val tvfood : TextView = view.findViewById(R.id.tvrcfood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): riwayatviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_riwayat , parent , false)
        return riwayatviewholder(view)
    }

    override fun onBindViewHolder(holder: riwayatviewholder, position: Int) {
        val data = riwayatlist[position]
        holder.tvfood.text = data.food
        Glide.with(holder.imgrc.context).load(data.img).into(holder.imgrc)
        holder.kartu.setOnClickListener {
            //intent ke detailriwayatActivity lempar food alamt dan img
            val intent = Intent(holder.kartu.context , detailriwayatActivity::class.java)
            intent.putExtra("food" , data.food)
            intent.putExtra("alamat" , data.alamat)
            intent.putExtra("img" , data.img)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return riwayatlist.size
    }

}