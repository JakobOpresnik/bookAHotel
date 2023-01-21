package com.example.reservehotel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.reservehotel.hotelUtils.Reservation
import com.example.reservehotel.hotelUtils.ReservationsList


class RecyclerAdapter(private val dataSet: ReservationsList, private val onClickObject: myOnClick) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface myOnClick {
        fun onClick(p0: View?, position: Int)
    }

    interface myOnLongClick {
        fun onLongClick(p0: View?, position: Int)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val info: TextView = view.findViewById(R.id.textView)
        val line: CardView = view.findViewById(R.id.cvLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservations_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel: Reservation = dataSet.pastReservations[position]

        holder.info.text = itemsViewModel.toString()

        holder.line.setOnClickListener { p0 ->
            Log.i("short click", "here code comes click on ${holder.adapterPosition}")
            onClickObject.onClick(p0, holder.adapterPosition)
        }

        /*holder.line.setOnLongClickListener { p0 ->
            //holder.line.setCardBackgroundColor(Color.RED)
            onLongClickObject.onLongClick(p0, holder.adapterPosition)
            true
        }*/
    }

    override fun getItemCount() = dataSet.pastReservations.size
}