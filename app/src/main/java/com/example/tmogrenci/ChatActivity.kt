package com.example.tmogrenci

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    // Sıkça Sorulan Sorular (FAQ) listesi
    private val faqList = listOf(
        Pair(listOf("devlet", "kayıt", "belge", "devlet üniversitesi", "devlet üniversiteleri", "devlet üniversitesine kayıt", "devlet üniversitesi belgeler"),
            "Devlet üniversitelerine kayıt için genellikle şu belgeler gereklidir: YKS Yerleştirme Belgesi, mezuniyet belgesi veya diploma, 12 adet vesikalık fotoğraf, harç dekontu, kimlik fotokopisi, ikametgah beyanı, askerlik belgesi (gerekirse), üniversitenin istediği diğer belgeler. Not: Kayıt için şahsen başvuru gereklidir, eksik belgeyle kayıt yapılmaz."),
        Pair(listOf("vakıf", "kayıt", "evrak", "belge", "vakıf üniversitesi", "vakıf üniversiteleri", "vakıf üniversitesine kayıt", "vakıf üniversitesi belgeler"),
            "Vakıf üniversitelerine kayıt için genellikle şu belgeler gereklidir: YKS Yerleştirme Belgesi, lise diploması veya mezuniyet belgesi, 6-12 adet vesikalık fotoğraf, kimlik fotokopisi, ÖSYM Sonuç Belgesi, sağlık raporu (gerekirse), öğrenim ücreti dekontu, adli sicil kaydı (bazı üniversiteler için), YÖS veya uluslararası sınav sonucu (yabancılar için), üniversitenin istediği ek belgeler. Not: Eksik evrakla kayıt yapılmaz, son tarihlere dikkat edilmelidir."),
        Pair(listOf("ikamet", "öğrenci ikamet izni", "ikamet izni", "ikamet izni belgeler", "ikamet izni başvurusu", "oturma izni"),
            "Öğrenci ikamet izni için gereken belgeler: Başvuru formu, pasaport ve varsa vize fotokopisi, 2 biyometrik fotoğraf, ikamet izni harcı dekontu, adres belgesi, kalınacak yer belgesi, geçerli sağlık sigortası, UETS adresi (gerekirse), öğrencilik belgesi. Detaylar ve güncel bilgiler için https://e-ikamet.goc.gov.tr/ adresini ziyaret ediniz."),
        Pair(listOf("vize", "vize şartları", "türkiye vize", "türkiye vize şartları", "türkiye vize türleri", "vize türleri"),
            "Türkiye'ye giriş için vize şartları ülkeye ve amaca göre değişir. Bazı ülkeler vizeden muaftır, bazıları e-vize alabilir. Pasaport geçerliliği, sağlık sigortası ve kalış süresi gibi şartlar aranır. Vize türleri: turizm, transit, eğitim, çalışma, resmi görev ve diğer vizelerdir. Detaylar için Dışişleri Bakanlığı web sitesini kontrol ediniz."),
        Pair(listOf("e-devlet", "e-devlet kayıt", "e-devlet üzerinden kayıt", "e-devlet ile kayıt"),
            "E-devlet üzerinden elektronik kayıt yapan öğrencilerin genellikle üniversiteye ayrıca belge teslim etmelerine gerek yoktur, ancak üniversitenin duyurularını takip etmek önemlidir."),
        Pair(listOf("fotoğraf", "kayıt için fotoğraf", "fotoğraf nasıl olmalı", "vesikalık"),
            "Kayıt için istenen vesikalık fotoğraf: Son 6 ay içinde çekilmiş, önden, başı ve boynu açık, adayı kolaylıkla tanıtabilecek şekilde olmalıdır. Genellikle 6-12 adet istenir."),
        Pair(listOf("askerlik", "askerlik belgesi kimlerden istenir", "askerlik belgesi"),
            "Askerlik çağına gelmiş veya daha yukarı yaştaki erkek adaylar için ilgili askerlik şubesinden alınacak 'bir fakülte veya yüksekokula kaydolmasında askerlikçe bir sakınca olmadığı'na dair belge gereklidir."),
        Pair(listOf("sağlık sigortası", "sağlık sigortası zorunlu mu", "sigorta"),
            "Türkiye'de vize ve ikamet izni başvurularında geçerli sağlık sigortası yaptırılması şarttır. Öğrenciler kayıt tarihinden itibaren üç ay içinde genel sağlık sigortası yaptırmalıdır."),
        Pair(listOf("posta", "posta ile kayıt", "kayıt için belgeleri posta ile gönderebilir miyim"),
            "Kayıt için adayların bizzat başvurması gerekmektedir. Posta ile kayıt yapılmaz. Belgelerin onaysız sureti veya fotokopisi kabul edilmez."),
        Pair(listOf("kayıt", "son tarih", "kayıt için son tarih", "kayıt işlemleri son tarih"),
            "Kayıt işlemleri için son tarihlere dikkat edilmelidir. Eksik evrak olması durumunda kayıt yapılmaz.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        setupRecyclerView()
        setupSendButton()
        addWelcomeMessage()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter
    }

    private fun setupSendButton() {
        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                sendUserMessage(messageText)
                messageInput.text.clear()
            }
        }
    }

    private fun sendUserMessage(message: String) {
        val userMessage = ChatMessage(message, true, System.currentTimeMillis())
        messages.add(userMessage)
        chatAdapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)

        // Simulate bot response
        simulateBotResponse(message)
    }

    private fun simulateBotResponse(userMessage: String) {
        val lowerMessage = userMessage.lowercase()
        val response = faqList.firstOrNull { (keywords, _) ->
            keywords.any { lowerMessage.contains(it) }
        }?.second ?: "Bu konuda size yardımcı olmaya çalışayım. Daha spesifik bir soru sorabilir misiniz? Üniversite başvurusu, vize işlemleri veya genel bilgiler hakkında sorularınızı yanıtlayabilirim."

        recyclerView.postDelayed({
            val botMessage = ChatMessage(response, false, System.currentTimeMillis())
            messages.add(botMessage)
            chatAdapter.notifyItemInserted(messages.size - 1)
            recyclerView.scrollToPosition(messages.size - 1)
        }, 1000)
    }

    private fun addWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            getString(R.string.chat_welcome_message),
            false,
            System.currentTimeMillis()
        )
        messages.add(welcomeMessage)
        chatAdapter.notifyItemInserted(0)
    }
} 