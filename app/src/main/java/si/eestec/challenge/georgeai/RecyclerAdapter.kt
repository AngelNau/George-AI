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
    private val tasks: List<TaskModel>,
    private val onItemClickCallback: (TaskModel) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.CardViewHolder?>() {

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemImage: ImageView? = null
        var itemTitle: TextView? = null
        var from: TextView? = null
        var to: TextView? = null

        init {
            itemImage = itemView?.findViewById(R.id.item_image)
            itemTitle = itemView?.findViewById(R.id.item_title)
            from = itemView?.findViewById(R.id.item_from)
            to = itemView?.findViewById(R.id.item_to)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder, i: Int) {
        if(tasks[i].image != null) {
            viewHolder.itemImage?.setImageBitmap(stringToBitmap(tasks[i].image!!))
        } else {
//            viewHolder.itemImage?.setImageResource(R.drawable.placeholder_photo)
        }
        viewHolder.from?.text = tasks[i].from
        viewHolder.to?.text = tasks[i].to
        viewHolder.itemTitle?.text = tasks[i].title
        viewHolder.itemView.setOnClickListener {
            onItemClickCallback(tasks[i])
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    private fun stringToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}