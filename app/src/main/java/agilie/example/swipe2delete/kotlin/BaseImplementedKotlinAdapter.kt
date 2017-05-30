package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.User
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.agilie.swipe2delete.SwipeToDeleteDelegate
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteAdapter
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteHolder
import kotlinx.android.synthetic.main.item_user.view.*
import test.alexzander.swipetodelete.R.layout.item_user

class BaseImplementedKotlinAdapter(var mutableList: MutableList<User>) :
        RecyclerView.Adapter<BaseImplementedKotlinAdapter.Holder>(), ISwipeToDeleteAdapter<String, User, BaseImplementedKotlinAdapter.Holder> {

    val swipeToDeleteAdapter = SwipeToDeleteDelegate(items = mutableList, swipeToDeleteAdapter = this)

    override fun onCreateViewHolder(parent: android.view.ViewGroup?, viewType: Int) =
            BaseImplementedKotlinAdapter.Holder(LayoutInflater.from(parent?.context).inflate(item_user, parent, false))

    override fun onBindViewHolder(holder: BaseImplementedKotlinAdapter.Holder, position: Int) =
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].name, position)

    override fun getItemCount() = mutableList.size

    override fun findItemPositionByKey(key: String) = (0..mutableList.lastIndex).firstOrNull { mutableList[it].name == key } ?: -1

    override fun onBindCommonItem(holder: BaseImplementedKotlinAdapter.Holder, key: String, item: User, position: Int) {
        holder.userContainer.visibility = View.VISIBLE
        holder.userName.text = item.name
    }

    override fun removeItem(key: String) =
        swipeToDeleteAdapter.removeItem(key)

    class Holder(view: View) : RecyclerView.ViewHolder(view), ISwipeToDeleteHolder<String> {

        var userContainer = view.user_container
        var userName = view.user_name

        override var isPendingDelete: Boolean = false

        override val topContainer = userContainer!!

        override var key: String = ""
    }
}