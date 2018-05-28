package cm.dot7.wdyk.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import cm.dot7.wdyk.R
import cm.dot7.wdyk.R.id.listView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mDatabase: DatabaseReference? = null
    private var mUserId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAll()
    }

    private fun initAll() {
        // Get the support action bar
        val actionBar = supportActionBar
        actionBar?.elevation = 4.0F

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth?.currentUser
        mDatabase = FirebaseDatabase.getInstance().reference

        if (mFirebaseUser == null) {
            loadLogInView()
        } else {

            mUserId = mFirebaseUser?.uid

            // Set up ListView
            val listView = findViewById<View>(listView) as ListView
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)

            listView.adapter = adapter
            listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                mUserId?.let {
                    mDatabase?.child("users")?.child(it)?.child("items")
                            ?.orderByChild("title")
                            ?.equalTo(listView.getItemAtPosition(position) as String)
                            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.hasChildren()) {
                                        val firstChild = dataSnapshot.children.iterator().next()
                                        firstChild.ref.removeValue()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {

                                }
                            })
                }
            }

            // Add items via the Button and EditText at the bottom of the view.
            val text = findViewById<View>(R.id.todoText) as EditText
            val button = findViewById<View>(R.id.addButton) as Button
            button.setOnClickListener {
                mUserId?.let {
                    mDatabase?.child("users")?.child(mUserId!!)?.child("items")?.push()?.child("title")?.setValue(text.text.toString())
                    text.setText("")
                }

                //own model
                /*button.setOnClickListener {
               val item = Item(text.text.toString())
               mDatabase?.child("users")?.child(mUserId)?.child("items")?.push()?.setValue(item)
               text.setText("")
           }*/

                // Use Firebase to populate the list.

                val childEventListener = object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                        adapter.add(dataSnapshot.child("title").value as String)
                    }

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    }

                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                        adapter.remove(dataSnapshot.child("title").value as String)
                    }

                    override fun onChildMoved(dataSnapshot: DataSnapshot, p1: String?) {
                    }

                    override fun onCancelled(dataSnapshot: DatabaseError) {
                    }

                }
                mUserId?.let { it1 -> mDatabase?.child("users")?.child(it1)?.child("items")?.addChildEventListener(childEventListener) }
            }

            // Delete items when clicked
            AdapterView.OnItemClickListener { parent, view, position, id ->
                mUserId?.let {
                    mDatabase?.child("users")?.child(it)?.child("items")
                            ?.orderByChild("title")
                            ?.equalTo(parent.getItemAtPosition(position) as String)
                            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.hasChildren()) {
                                        val firstChild = dataSnapshot.children.iterator().next()
                                        firstChild.ref.removeValue()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {

                                }
                            })
                }
            }

        }
    }

        private fun loadLogInView() {
            val intent = Intent(this, LogInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_main, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            return if (item?.itemId == R.id.action_logout) {
                mFirebaseAuth?.signOut();
                loadLogInView()
                return true
            } else {
                super.onOptionsItemSelected(item)
            }
        }

}