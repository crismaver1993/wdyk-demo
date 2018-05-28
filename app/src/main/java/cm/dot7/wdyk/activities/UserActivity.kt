package cm.dot7.wdyk.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cm.dot7.wdyk.R
import cm.dot7.wdyk.databinding.ActivityUserBinding
import cm.dot7.wdyk.models.User

class UserActivity : AppCompatActivity() {

    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        user = User("Borja", "Bravo", 24)
        binding.setUser(user)
    }

    fun onClickMe(view: View) {
        user?.setFirstName("Juan")
        user?.setLastName("García")
        user?.setAge(35)
    }
}
