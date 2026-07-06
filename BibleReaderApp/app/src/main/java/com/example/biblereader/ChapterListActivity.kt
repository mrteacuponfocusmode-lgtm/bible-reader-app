package com.example.biblereader

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ChapterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_list)

        val bookOrder = intent.getIntExtra("bookOrder", 1)
        val bookName = intent.getStringExtra("bookName") ?: "Book"
        val chapterCount = intent.getIntExtra("chapterCount", 1)

        title = bookName

        val listView: ListView = findViewById(R.id.chapterListView)
        val labels = (1..chapterCount).map { "Chapter $it" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, labels)

        listView.setOnItemClickListener { _, _, position, _ ->
            val chapter = position + 1
            val readIntent = Intent(this, ReadingActivity::class.java)
            readIntent.putExtra("bookOrder", bookOrder)
            readIntent.putExtra("bookName", bookName)
            readIntent.putExtra("chapter", chapter)
            startActivity(readIntent)
        }
    }
}
