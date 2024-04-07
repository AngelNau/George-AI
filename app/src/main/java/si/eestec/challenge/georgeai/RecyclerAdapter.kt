package si.eestec.challenge.georgeai

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import si.eestec.challenge.georgeai.model.TaskModel

class RecyclerAdapter(
    private var tasks: List<TaskModel>,
    private val onItemClickCallback: (TaskModel) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.CardViewHolder?>() {

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemDescription: TextView? = null
        var itemTitle: TextView? = null
        var time: TextView? = null
        var date: TextView?= null

        init {
            itemDescription = itemView?.findViewById(R.id.item_description)
            itemTitle = itemView?.findViewById(R.id.item_title)
            time = itemView?.findViewById(R.id.item_start_time)
            date =itemView?.findViewById(R.id.item_date)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        // Inflate the card view layout
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)

        // Instantiate a new CardViewHolder with the inflated view
        val viewHolder = CardViewHolder(view)

        return viewHolder  }

    override fun onBindViewHolder(viewHolder: CardViewHolder, i: Int) {
//        if(tasks[i].image != null) {
//            viewHolder.itemImage?.setImageBitmap(stringToBitmap(tasks[i].image!!))
//        } else {
////            viewHolder.itemImage?.setImageResource(R.drawable.placeholder_photo)
//        }
        viewHolder.time?.text = tasks[i].time
        viewHolder.itemTitle?.text = tasks[i].title
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback(tasks[i])
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(newTasks: List<TaskModel>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

//    private fun stringToBitmap(base64String: String): Bitmap {
//        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
//        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
//    }
}