package com.example.chat_ai_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_ai_app.adapter.ChatAdapter
import com.example.chat_ai_app.model.Message
import com.google.android.material.animation.AnimationUtils
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private var chatAdapter: ChatAdapter? = null
    private val messages: MutableList<Message> = ArrayList<Message>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<Button>(R.id.btnSend)

        // Set up RecyclerView
        chatAdapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        // Add static bot message
        messages.add(
            Message(
                "Welcome! Ask about our products: Smartphone, Laptop, or Smartwatch.",
                false
            )
        )
        chatAdapter!!.notifyDataSetChanged()
        btnSend.setOnClickListener {
            val userMessage = etMessage.text.toString()
            if (!userMessage.isEmpty()) {
                // Add user message
                messages.add(Message(userMessage, true))
                etMessage.text.clear()

                // Add bot reply based on the user input
                val botReply = getBotReply(userMessage)
                messages.add(Message(botReply, false))
                chatAdapter!!.notifyDataSetChanged()

                // Scroll to the latest message
                recyclerView.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun getBotReply(userMessage: String): String {
        return if (userMessage.lowercase(Locale.getDefault()).contains("smartphone")) {
            "This smartphone features a 6.5-inch display, 128GB storage, 48MP camera, and a long-lasting battery."
        } else if (userMessage.lowercase(Locale.getDefault()).contains("laptop")) {
            "A powerful laptop with a 15.6-inch display, 16GB RAM, 512GB SSD, and an Intel Core i7 processor."
        } else if (userMessage.lowercase(Locale.getDefault()).contains("smartwatch")) {
            "The smartwatch offers fitness tracking, heart rate monitoring, GPS, and 7-day battery life."
        } else {
            "Sorry, I don't have information on that product. Please ask about our Smartphone, Laptop, or Smartwatch."
        }
    }
}

