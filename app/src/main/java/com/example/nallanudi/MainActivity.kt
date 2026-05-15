package com.example.nallanudi

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var tts: TextToSpeech
    private lateinit var adapter: WordAdapter
    private val db by lazy { WordDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initialize TTS with safety
        tts = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) tts.language = Locale.US
        }

        // 2. Initialize Adapter
        adapter = WordAdapter({ term -> tts.speak(term, TextToSpeech.QUEUE_FLUSH, null, null) },
            { word ->
                lifecycleScope.launch {
                    val newStatus = if (word.is_favorite == 1) 0 else 1
                    db.wordDao().updateFavorite(word.id, newStatus)
                }
            })

        // 3. Setup RecyclerView
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // 4. Data Loading with ERROR CATCHING
        lifecycleScope.launch {
            try {
                if (db.wordDao().getCount() == 0) {
                    val jsonString = assets.open("words.json").bufferedReader().use { it.readText() }
                    val array = JSONArray(jsonString)
                    val list = mutableListOf<Word>()
                    for (i in 0 until array.length()) {
                        val o = array.getJSONObject(i)
                        // USE optString to prevent crashes from missing data
                        list.add(Word(
                            id = 0,
                            english_term = o.optString("english_term", "N/A"),
                            kannada_term = o.optString("kannada_term", "N/A"),
                            definition = o.optString("definition", "No definition"),
                            simple_example = o.optString("simple_example", ""),
                            subject = o.optString("subject", "General"),
                            is_favorite = 0
                        ))
                    }
                    db.wordDao().insertAll(list)
                }

                db.wordDao().getAllWords().collect { fullList ->
                    adapter.setData(fullList)
                    if (fullList.isNotEmpty()) {
                        val index = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % fullList.size
                        findViewById<TextView>(R.id.tvWOD).text = fullList[index].english_term
                    }
                }
            } catch (e: Exception) {
                // If there is an error, show a message instead of crashing
                Toast.makeText(this@MainActivity, "JSON Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        // 5. Search with Safety
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?) = false
            override fun onQueryTextChange(q: String?): Boolean {
                lifecycleScope.launch { db.wordDao().searchWords(q ?: "").collect { adapter.setData(it) } }
                return true
            }
        })

        // 6. Filter Chips
        findViewById<ChipGroup>(R.id.filterGroup).setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()
            lifecycleScope.launch {
                when (checkedId) {
                    R.id.chipScience -> db.wordDao().getWordsBySubject("Science").collect { adapter.setData(it) }
                    R.id.chipMath -> db.wordDao().getWordsBySubject("Math").collect { adapter.setData(it) }
                    R.id.chipCommerce -> db.wordDao().getWordsBySubject("Commerce").collect { adapter.setData(it) }
                    R.id.chipMyList -> db.wordDao().getFavorites().collect { adapter.setData(it) }
                    else -> db.wordDao().getAllWords().collect { adapter.setData(it) }
                }
            }
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) { tts.stop(); tts.shutdown() }
        super.onDestroy()
    }
}