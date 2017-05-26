package agilie.example.swipe2delete.ui

import agilie.example.swipe2delete.MainActivityNavigation
import agilie.example.swipe2delete.prepareContactList
import agilie.example.swipe2delete.sample.FullKotlinAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout.VERTICAL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R.layout.activity_main

class MainActivity : AppCompatActivity(), MainActivityNavigation {

    var adapter: FullKotlinAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        adapter = FullKotlinAdapter(prepareContactList(60), this)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(adapter?.swipeToDeleteAdapter?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun navigateToBaseKotlinActivity() {
        startActivity(Intent(this, BaseKotlinActivity::class.java))
    }

    override fun navigateToJavaActivity() {
        startActivity(Intent(this, JavaActivity::class.java))
    }
}