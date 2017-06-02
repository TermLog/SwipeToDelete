package agilie.example.swipe2delete.kotlin

import agilie.example.swipe2delete.User
import android.animation.Animator
import android.animation.ValueAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agilie.swipe2delete.ModelOptions
import com.agilie.swipe2delete.SwipeToDeleteDelegate
import com.agilie.swipe2delete.interfaces.IAnimationUpdateListener
import com.agilie.swipe2delete.interfaces.IAnimatorListener
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteAdapter
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteHolder
import kotlinx.android.synthetic.main.activity_main_item.view.*
import test.alexzander.swipetodelete.R.layout.activity_main_item


class UserAdapter(val mutableList: MutableList<User>, var onItemClick: (User) -> Any) :
        RecyclerView.Adapter<UserAdapter.MyHolder>(), ISwipeToDeleteAdapter<Int, User, UserAdapter.MyHolder>, IAnimationUpdateListener, IAnimatorListener {

    val swipeToDeleteAdapter = SwipeToDeleteDelegate(items = mutableList, swipeToDeleteAdapter = this)
    var animationEnable = true
    var bottomContainer = true

    init {
        swipeToDeleteAdapter.deletingDuration = 3000
    }

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].id, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            MyHolder(LayoutInflater.from(parent?.context).inflate(activity_main_item, parent, false))

    override fun removeItem(key: Int) {
        swipeToDeleteAdapter.removeItem(key)
    }

    override fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>) {
        if (animationEnable) swipeToDeleteAdapter.holders[options.key]?.progressBar?.visibility = View.GONE
    }

    override fun onAnimationUpdated(animation: ValueAnimator?, options: ModelOptions<*>) {
        if (animationEnable) {
            val posX = animation?.animatedValue as Float
            swipeToDeleteAdapter.holders[options.key]?.progressBar?.x = posX
            options.posX = posX
        }
    }

    override fun onAnimationStart(animation: Animator?, options: ModelOptions<*>) {
        if (animationEnable) {
            swipeToDeleteAdapter.holders[options.key]?.progressBar?.visibility = View.VISIBLE
        }
    }

    override fun findItemPositionByKey(key: Int) = (0..mutableList.lastIndex).firstOrNull { mutableList[it].id == key } ?: -1

    override fun onBindCommonItem(holder: MyHolder, key: Int, item: User, position: Int) {
        holder.apply {
            name.text = item.name
            id.text = item.id.toString()
            itemContainer.visibility = View.VISIBLE
            undoData.visibility = View.GONE
            progressBar.visibility = View.GONE
            itemContainer.setOnClickListener { onItemClick.invoke(item) }
        }
    }

    override fun onBindPendingItem(holder: MyHolder, key: Int, item: User, position: Int) {
        holder.itemContainer.visibility = View.GONE
        if (bottomContainer) {
            holder.apply {
                deletedName.text = "You have just deleted ${item.name}"
                itemContainer.visibility = View.GONE
                undoData.visibility = View.VISIBLE
                if (animationEnable) {
                    progressBar.visibility = View.VISIBLE
                }
                undoButton.setOnClickListener { swipeToDeleteAdapter.onUndo(key) }
            }

        }
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view), ISwipeToDeleteHolder<Int> {

        var deletedName = view.user_name_deleted
        var name = view.user_name
        var id = view.user_id
        var itemContainer = view.contact_container
        var undoContainer = view.undo_container
        var undoData = view.undo_data
        var undoButton = view.button_undo
        var progressBar = view.progress_indicator

        override val topContainer: View
            get() =
            if (pendingDelete && bottomContainer) undoContainer
            else itemContainer
        override var key: Int = -1
        override var pendingDelete: Boolean = false
    }
}