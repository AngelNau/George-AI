package si.eestec.challenge.georgeai

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import si.eestec.challenge.georgeai.model.TaskModel

class RecyclerAdapter(
    private var tasks: List<TaskModel>,
    private val onItemClickCallback: (String) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.CardViewHolder?>() {

    inner class CardViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var itemDescription: TextView? = null
        var itemTitle: TextView? = null
        var time: TextView? = null
        var date: TextView? = null
        var helpButton: Button? = null

        init {
            itemDescription = itemView?.findViewById(R.id.item_description)
            itemTitle = itemView?.findViewById(R.id.item_title)
            time = itemView?.findViewById(R.id.item_start_time)
            date = itemView?.findViewById(R.id.item_date)
            helpButton = itemView?.findViewById(R.id.helpButton)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewHolder {
        // Inflate the card view layout
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)

        // Instantiate a new CardViewHolder with the inflated view
        val viewHolder = CardViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder, i: Int) {
        viewHolder.time?.text = tasks[i].time
        viewHolder.itemTitle?.text = tasks[i].title
        viewHolder.itemDescription?.text = tasks[i].description
        viewHolder.helpButton?.setOnClickListener {
            onItemClickCallback(viewHolder.itemDescription?.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(newTasks: List<TaskModel>) {
        tasks = newTasks
    }

}