package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.prepareContactList
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R
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
            user ->
            Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()
        }

        adapter?.swipeToDeleteDelegate?.pending = true
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(adapter?.swipeToDeleteDelegate?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.action_undo_animation -> {
                    item.isChecked = !item.isChecked
                    adapter?.animationEnabled = item.isChecked
                    true
                }
                R.id.action_bottom_container -> {
                    item.isChecked = !item.isChecked
                    adapter?.bottomContainer = item.isChecked
                    true
                }
                R.id.action_pending -> {
                    item.isChecked = !item.isChecked
                    adapter?.swipeToDeleteDelegate?.pending = item.isChecked
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
