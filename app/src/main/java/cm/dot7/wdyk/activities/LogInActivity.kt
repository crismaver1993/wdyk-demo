package cm.dot7.wdyk.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import cm.dot7.wdyk.R
import cm.dot7.wdyk.databinding.ActivityUserBinding
import com.google.firebase.auth.FirebaseAuth


/**
 * Created by cmarin on 5/14/2018
 */
class LogInActivity : AppCompatActivity() {
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var logInButton: Button? = null
    private var signUpTextView: TextView? = null
    private var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)


        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()

        signUpTextView = findViewById<View>(R.id.signUpText) as TextView?
        emailEditText = findViewById<View>(R.id.emailField) as EditText?
        passwordEditText = findViewById<View>(R.id.passwordField) as EditText?
        logInButton = findViewById<View>(R.id.loginButton) as Button

        signUpTextView?.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        logInButton?.setOnClickListener {
            var email = emailEditText?.text.toString()
            var password = passwordEditText?.text.toString()

            email = email.trim { it <= ' ' }
            password = password.trim { it <= ' ' }

            if (email.isEmpty() || password.isEmpty()) {
                val builder = AlertDialog.Builder(this@LogInActivity)
                builder.setMessage(R.string.login_error_message)
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {
                mFirebaseAuth?.signInWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener(this@LogInActivity, { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            } else {
                                val builder = AlertDialog.Builder(this@LogInActivity)
                                builder.setMessage(task.exception!!.message)
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null)
                                val dialog = builder.create()
                                dialog.show()
                            }
                        })
            }
        }
    }
}