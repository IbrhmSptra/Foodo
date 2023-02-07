package id.kotlin.foodo.recycleviewBookmark


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.kotlin.foodo.R
import id.kotlin.foodo.Resep.detailActivity


class bookmarkAdapter (private val bookmarklist : ArrayList<databookmark>)
    : RecyclerView.Adapter<bookmarkAdapter.bookmarkviewholder>() {

    class bookmarkviewholder(view : View) : RecyclerView.ViewHolder(view) {
        val kartu : CardView = view.findViewById(R.id.kartubookmark)
        val imgrc : ImageView = view.findViewById(R.id.imgrcbookmark)
        val tvrc : TextView = view.findViewById(R.id.tvrcbookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookmarkviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark , parent , false)
        return bookmarkviewholder(view)
    }

    override fun onBindViewHolder(holder: bookmarkviewholder, position: Int) {
        val bookmark = bookmarklist[position]
        holder.tvrc.text = bookmark.food
        Glide.with(holder.imgrc.context).load(bookmark.imgrc).into(holder.imgrc)
        holder.kartu.setOnClickListener {
            //intent ke detailactivity lempar nama food, img food dan imgrckat
            val intent = Intent(holder.kartu.context , detailActivity::class.java)
            intent.putExtra("food" , bookmark.food)
            intent.putExtra("img" , bookmark.img)
            intent.putExtra("imgrckat" , bookmark.imgrc)
            holder.kartu.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookmarklist.size
    }

}