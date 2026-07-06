package com.example.biblereader

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class BookListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private lateinit var listView: ListView
    private var allBooks: List<BookInfo> = emptyList()
    private var displayedBooks: List<BookInfo> = emptyList()
    private var currentTestament = "OT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)
        title = "Books"

        dbHelper = DbHelper(applicationContext)
        listView = findViewById(R.id.bookListView)
        val toggle: RadioGroup = findViewById(R.id.testamentToggle)

        allBooks = dbHelper.getBooks()
        showBooks(currentTestament)

        toggle.setOnCheckedChangeListener { _, checkedId ->
            currentTestament = if (checkedId == R.id.toggleNT) "NT" else "OT"
            showBooks(currentTestament)
        }
    }

    private fun showBooks(testament: String) {
        displayedBooks = allBooks.filter { it.testament == testament }
        val labels = displayedBooks.map { "${it.name}  (${it.chapterCount} ch)" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, labels)

        listView.setOnItemClickListener { _, _, position, _ ->
            val book = displayedBooks[position]
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
