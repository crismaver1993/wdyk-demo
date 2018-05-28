package cm.dot7.wdyk.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import cm.dot7.wdyk.R
import cm.dot7.wdyk.models.Article
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging


class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseMessaging.getInstance().subscribeToTopic("android");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val database = FirebaseDatabase.getInstance()

        val titleEditText = findViewById<View>(R.id.et_title) as EditText
        val authorEditText = findViewById<View>(R.id.et_author) as EditText
        val submitButton = findViewById<View>(R.id.btn_submit) as Button
        submitButton.setOnClickListener {
            val myRef = database.getReference("articles").push()
            val article = Article(titleEditText.text.toString(),
                    authorEditText.text.toString())
            myRef.setValue(article)
        }
    }
}