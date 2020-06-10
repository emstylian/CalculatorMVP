package com.kotlin.calculatormvp.ui.calculatorScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.calculatormvp.R


class CalculationsHistoryAdapter(
    private var items: MutableList<String>,
    private val fragment: Fragment
) : RecyclerView.Adapter<CalculationsHistoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalculationsHistoryAdapter.MyViewHolder {
        val itemView =
            LayoutInflater.from(fragment.context)
                .inflate(R.layout.calculations_history_slot, parent, false)
        return MyViewHolder(itemView)
    }

    fun setAdapterData(items: MutableList<String>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CalculationsHistoryAdapter.MyViewHolder, position: Int) {
        val history_str = items[position]
        holder.teamNameTV.text = history_str
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout = itemView.findViewById<LinearLayout>(R.id.history_slot_ll)
        var teamNameTV = itemView.findViewById<TextView>(R.id.history_slot_tv)
    }
}