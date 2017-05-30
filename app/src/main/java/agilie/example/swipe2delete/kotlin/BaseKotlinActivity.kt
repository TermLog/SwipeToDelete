package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.prepareContactList
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_base_kotlin.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R.layout.activity_base_kotlin

class BaseKotlinActivity : AppCompatActivity() {

    var adapter: BaseImplementedKotlinAdapter? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_base_kotlin)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Base Implemented Kotlin Activity"
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = BaseImplementedKotlinAdapter(prepareContactList(60))
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(adapter?.swipeToDeleteAdapter?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}