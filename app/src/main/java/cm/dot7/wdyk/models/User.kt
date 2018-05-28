package cm.dot7.wdyk.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import cm.dot7.wdyk.BR

/**
 * Created by cmarin on 5/11/2018
 */
class User(private var firstName: String?, private var lastName: String?, private var age: Int) : BaseObservable() {

    @Bindable
    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
        notifyPropertyChanged(BR.lastName)

    }

    @Bindable
    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
        notifyPropertyChanged(BR.age)
    }
}