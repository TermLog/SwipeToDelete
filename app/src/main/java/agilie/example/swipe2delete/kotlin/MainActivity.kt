package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.MainActivityNavigation
import agilie.example.swipe2delete.java.JavaActivity
import agilie.example.swipe2delete.prepareContactList
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout.VERTICAL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view.*
import test.alexzander.swipetodelete.R.layout.activity_main

class MainActivity : android.support.v7.app.AppCompatActivity(), MainActivityNavigation {

    var adapter: FullKotlinAdapter? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()
    }

    fun initRecyclerView() {
        if (RecViewObjects.mainAdapter == null) {
            adapter = FullKotlinAdapter(prepareContactList(60), this)
            RecViewObjects.mainAdapter = adapter
        } else { adapter = RecViewObjects.mainAdapter }
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
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