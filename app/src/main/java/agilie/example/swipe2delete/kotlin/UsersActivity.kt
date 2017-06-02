package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.java.UsersActivity
import agilie.example.swipe2delete.prepareContactList
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R.layout.activity_main

class UsersActivity : android.support.v7.app.AppCompatActivity() {

    var adapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()
    }

    fun initRecyclerView() {
        adapter = UserAdapter(prepareContactList(60)) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(adapter?.swipeToDeleteAdapter?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}