package agilie.example.swipe2delete

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.LinearLayout
import test.alexzander.swipetodelete.R
import agilie.example.swipe2delete.sample.BaseImplementedKotlinAdapter
import kotlinx.android.synthetic.main.activity_base_kotlin.*
import kotlinx.android.synthetic.main.content_main.*

class BaseKotlinActivity : AppCompatActivity() {

    var adapter: BaseImplementedKotlinAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_kotlin)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("BaseKotlinActivity")
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = BaseImplementedKotlinAdapter(this, prepareContactList(60))
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(adapter?.swipeToDeleteAdapter?.itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}