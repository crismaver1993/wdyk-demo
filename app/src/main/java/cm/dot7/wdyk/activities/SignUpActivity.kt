package cm.dot7.wdyk.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import cm.dot7.wdyk.R
import com.google.firebase.auth.FirebaseAuth


/**
 * Created by cmarin on 5/14/2018
 */
class SignUpActivity : AppCompatActivity() {

    private var passwordEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var signUpButton: Button? = null
    private var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()

        passwordEditText = findViewById<View>(R.id.passwordField) as EditText?
        emailEditText = findViewById<View>(R.id.emailField) as EditText?
        signUpButton = findViewById<View>(R.id.signupButton) as Button?

        signUpButton?.setOnClickListener {
            var password = passwordEditText?.text.toString()
            var email = emailEditText?.text.toString()

            password = password.trim { it <= ' ' }
            email = email.trim { it <= ' ' }

            if (password.isEmpty() || email.isEmpty()) {
                val builder = AlertDialog.Builder(this@SignUpActivity)
                builder.setMessage(R.string.signup_error_message)
                        .setTitle(R.string.signup_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {
                mFirebaseAuth?.createUserWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener(this@SignUpActivity) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            } else {
                                val builder = AlertDialog.Builder(this@SignUpActivity)
                                builder.setMessage(task.exception!!.message)
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null)
                                val dialog = builder.create()
                                dialog.show()
                            }
                        }
            }
        }
    }
}