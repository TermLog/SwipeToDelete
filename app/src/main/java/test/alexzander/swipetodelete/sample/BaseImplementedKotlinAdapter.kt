package test.alexzander.swipetodelete.sample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swipetodeletelib.ISwipeToDeleteAdapter
import com.example.swipetodeletelib.ISwipeToDeleteHolder
import com.example.swipetodeletelib.SwipeToDeleteAdapter
import kotlinx.android.synthetic.main.user_item.view.*
import test.alexzander.swipetodelete.R.layout.user_item

class BaseImplementedKotlinAdapter(context: Context, var mutableList: MutableList<User>) :
        RecyclerView.Adapter<BaseImplementedKotlinAdapter.Holder>(), ISwipeToDeleteAdapter<String, User, BaseImplementedKotlinAdapter.Holder> {

    val swipeToDeleteAdapter = SwipeToDeleteAdapter(context = context, items = mutableList, swipeToDeleteAdapter = this)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(parent?.context).inflate(user_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].name, position)
    }

    override fun getItemCount() = mutableList.size

    override fun findItemPositionByKey(key: String) = (0..mutableList.lastIndex).firstOrNull { mutableList[it].name == key } ?: -1

    override fun onBindCommonItem(holder: Holder, key: String, item: User) {
        holder.userContainer.visibility = View.VISIBLE
        holder.undoContainer.visibility = View.GONE
        holder.userName.text = item.name
    }

    override fun onBindPendingItem(holder: Holder, key: String, item: User) {
        holder.undoContainer.visibility = View.VISIBLE
        holder.userContainer.visibility = View.GONE
        holder.undoButton.setOnClickListener { swipeToDeleteAdapter.onUndo(key) }
    }

    override fun removeItem(key: String, item: User) {
        swipeToDeleteAdapter.removeItem(key, item)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view), ISwipeToDeleteHolder<String> {

        var userContainer = view.user_container
        var userName = view.user_name

        var undoContainer = view.user_undo_container
        var undoButton = view.user_button_undo

        override var isPendingDelete: Boolean = false

        override val topContainer: View
            get() = if (isPendingDelete) undoContainer
            else userContainer

        override var key: String = ""
    }
}