package com.example.nallanudi

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
    private val onSpeak: (String) -> Unit,
    private val onFavClick: (Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var words = listOf<Word>()

    fun setData(newList: List<Word>) {
        words = newList
        notifyDataSetChanged()
    }

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val en: TextView = view.findViewById(R.id.tvEn)
        val kn: TextView = view.findViewById(R.id.tvKn)
        val def: TextView = view.findViewById(R.id.tvDef)
        val ex: TextView = view.findViewById(R.id.tvEx)
        val btnFav: ImageButton = view.findViewById(R.id.btnFav)
        val btnSpk: Button = view.findViewById(R.id.btnSpeak)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WordViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val w = words[position]
        holder.en.text = w.english_term
        holder.kn.text = w.kannada_term
        holder.def.text = w.definition
        holder.ex.text = "Example: " + w.simple_example

        // Favorite Toggle UI
        holder.btnFav.setImageResource(if (w.is_favorite == 1) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
        holder.btnFav.setOnClickListener { onFavClick(w) }

        // Flashcard Reveal Logic
        holder.kn.visibility = View.GONE
        holder.def.visibility = View.GONE
        holder.ex.visibility = View.GONE

        holder.itemView.setOnClickListener {
            holder.kn.visibility = View.VISIBLE
            holder.def.visibility = View.VISIBLE
            holder.ex.visibility = View.VISIBLE
        }

        holder.btnSpk.setOnClickListener { onSpeak(w.english_term) }
    }

    override fun getItemCount() = words.size
}