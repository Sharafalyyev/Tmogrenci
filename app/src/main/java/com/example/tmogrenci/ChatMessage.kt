package com.example.tmogrenci
 
data class ChatMessage(
    val message: String,
    val isFromUser: Boolean,
    val timestamp: Long
) 