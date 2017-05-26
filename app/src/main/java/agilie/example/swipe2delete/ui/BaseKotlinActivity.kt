package agilie.example.swipe2delete.ui

import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_base_kotlin.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R.layout.activity_base_kotlin

class BaseKotlinActivity : android.support.v7.app.AppCompatActivity() {

    var adapter: agilie.example.swipe2delete.sample.BaseImplementedKotlinAdapter? = null

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
        recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = agilie.example.swipe2delete.sample.BaseImplementedKotlinAdapter(agilie.example.swipe2delete.prepareContactList(60))
        recyclerView.adapter = adapter

        val dividerItemDecoration = android.support.v7.widget.DividerItemDecoration(this, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = android.support.v7.widget.helper.ItemTouchHelper(adapter?.swipeToDeleteAdapter?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}