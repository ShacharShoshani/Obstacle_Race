package com.example.obstaclerace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.example.obstaclerace.databinding.GameRecordItemBinding
import com.example.obstaclerace.interfaces.GameRecordCallback
import com.example.obstaclerace.models.GameRecord
import java.sql.Timestamp
import java.util.Date

class GameRecordAdapter(private val records: List<GameRecord>) :
    RecyclerView.Adapter<GameRecordAdapter.RecordViewHolder>() {

    var gameRecordCallback: GameRecordCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding =
            GameRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        with(holder) {
            with(records[position]) {
                setLabelText(binding.recordLBLCoins, coins.toString())
                setLabelText(binding.recordLBLDate, Date(Timestamp(timestamp).time).toString())
                setLabelText(binding.recordLBLMode, gameMode)
                setLabelText(binding.recordLBLDistance, distance.toString())
            }
        }
    }

    private fun setLabelText(label: MaterialTextView, data: String? = "") {
        val title = label.text.split(":")[0]
        label.text = buildString {
            append(title)
            append(":     ")
            append(data)
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }

    inner class RecordViewHolder(val binding: GameRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.displayLocationBTN.setOnClickListener {
                val item = records[adapterPosition]
                gameRecordCallback?.itemClicked(item, adapterPosition)
            }
        }
    }
}