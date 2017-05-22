package test.alexzander.swipetodelete

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Display
import android.view.View
import android.view.WindowManager

import android.widget.LinearLayout.VERTICAL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private var adapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deviceWidth()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Navigate to the Create Contact screen ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        if (adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        adapter = ContactAdapter(ContactAdapterUtils.prepareContactList(50))
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val touchCallback = ContactItemTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun deviceWidth() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay

        val size = Point()
        display.getSize(size)
        sDeviceScreenWidth = size.x
    }

    companion object {

        var sDeviceScreenWidth: Int = 0
    }
}
