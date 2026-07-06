package com.example.biblereader

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class BookListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        title = "Books"

        dbHelper = DbHelper(applicationContext)
        val listView: ListView = findViewById(R.id.bookListView)

        val books = dbHelper.getBooks()
        val labels = books.map { "${it.name}  (${it.chapterCount} ch)" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, labels)

        listView.setOnItemClickListener { _, _, position, _ ->
            val book = books[position]
            val intent = Intent(this, ChapterListActivity::class.java)
            intent.putExtra("bookOrder", book.bookOrder)
            intent.putExtra("bookName", book.name)
            intent.putExtra("chapterCount", book.chapterCount)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
