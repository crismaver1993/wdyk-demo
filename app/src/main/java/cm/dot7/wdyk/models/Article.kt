package cm.dot7.wdyk.models

/**
 * Created by cmarin on 5/23/2018
 */
class Article(private var title: String?,private var author: String?) {
    fun getTitle(): String? {
        return title
    }

    fun setTitle(mTitle: String) {
        this.title = mTitle

    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(mAuthor: String) {
        this.author = mAuthor
    }
}