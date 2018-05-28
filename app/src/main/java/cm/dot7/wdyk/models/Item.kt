package cm.dot7.wdyk.models

/**
 * Created by cmarin on 5/16/2018
 */
class Item(private var title: String?) {
    fun getTitle(): String? {
        return title
    }

    fun setTitle(mTitle: String) {
        this.title = mTitle
    }
}