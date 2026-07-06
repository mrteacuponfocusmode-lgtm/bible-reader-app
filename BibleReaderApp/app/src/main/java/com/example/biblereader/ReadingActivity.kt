package com.example.biblereader

import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReadingActivity : AppCompatActivity() {

    private lateinit var dbHelper: DbHelper
    private var bookOrder = 1
    private var chapter = 1
    private var bookName = "Book"

    private lateinit var titleView: TextView
    private lateinit var bodyView: TextView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        dbHelper = DbHelper(applicationContext)

        bookOrder = intent.getIntExtra("bookOrder", 1)
        chapter = intent.getIntExtra("chapter", 1)
        bookName = intent.getStringExtra("bookName") ?: "Book"

        titleView = findViewById(R.id.chapterTitle)
        bodyView = findViewById(R.id.chapterBody)
        scrollView = findViewById(R.id.chapterScrollView)
        val prevButton: Button = findViewById(R.id.prevButton)
        val nextButton: Button = findViewById(R.id.nextButton)

        prevButton.setOnClickListener { goToPreviousChapter() }
        nextButton.setOnClickListener { goToNextChapter() }

        loadChapter()
    }

    private fun loadChapter() {
        title = "$bookName $chapter"
        titleView.text = "$bookName $chapter"
        val verses = dbHelper.getChapterVerses(bookOrder, chapter)
        val sb = StringBuilder()
        for (v in verses) {
            sb.append(v.verse).append(" ").append(v.text).append("\n\n")
        }
        bodyView.text = sb.toString().trim()

        // Scroll back to the top every time a new chapter loads
        scrollView.post { scrollView.scrollTo(0, 0) }
    }

    private fun goToPreviousChapter() {
        if (chapter > 1) {
            chapter -= 1
            loadChapter()
        } else {
            val prevBook = dbHelper.getBook(bookOrder - 1)
            if (prevBook != null) {
                bookOrder -= 1
                bookName = prevBook.name
                chapter = prevBook.chapterCount
                loadChapter()
            }
        }
    }

    private fun goToNextChapter() {
        val currentBook = dbHelper.getBook(bookOrder)
        val maxChapter = currentBook?.chapterCount ?: chapter
        if (chapter < maxChapter) {
            chapter += 1
            loadChapter()
        } else {
            val nextBook = dbHelper.getBook(bookOrder + 1)
            if (nextBook != null) {
                bookOrder += 1
                bookName = nextBook.name
                chapter = 1
                loadChapter()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
