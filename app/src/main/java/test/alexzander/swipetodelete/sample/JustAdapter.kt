package test.alexzander.swipetodelete.sample

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swipetodeletelib.*
import kotlinx.android.synthetic.main.list_item.view.*
import test.alexzander.swipetodelete.R.layout.list_item


class JustAdapter(val context: Context, val mutableList: MutableList<User>) : RecyclerView.Adapter<JustAdapter.MyHolder>(), ISwipeToDeleteAdapter<Int, User, JustAdapter.MyHolder> {
    val swipeToDeleteAdapter = SwipeToDeleteAdapter(context = context, items = mutableList, swipeToDeleteAdapter = this)

    override fun getItemCount() = mutableList.size

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder?.key = mutableList[position].id
        swipeToDeleteAdapter.onBindViewHolder(holder, mutableList[position].id, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent?.context).inflate(list_item, parent, false)
        return MyHolder(view)
    }

    override val animatorListener: AnimatorListener
        get() = object : AnimatorListener {
            override fun onAnimationEnd(animation: Animator?, options: ModelOptions<*>) {
                swipeToDeleteAdapter.holders[options.key]?.progressBar?.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?, options: ModelOptions<*>) {

            }

            override fun onAnimationStart(animation: Animator?, options: ModelOptions<*>) {
                swipeToDeleteAdapter.holders[options.key]?.progressBar?.visibility = View.VISIBLE

            }
        }

    override fun findPositionByKey(key: Int) = (0..mutableList.lastIndex).firstOrNull { mutableList[it].id == key }
            ?: -1

    override fun onBindCommonContact(holder: MyHolder?, key: Int, item: User) {
        holder?.name?.text = item.name
        holder?.phone?.text = item.id.toString()
        holder?.contactContainer?.visibility = View.VISIBLE
        holder?.undoData?.visibility = View.GONE
        holder?.progressBar?.visibility = View.GONE
    }

    override fun onBindPendingContact(holder: MyHolder?, key: Int, item: User) {
        holder?.deletedName?.text = "You have just deleted " + item.name
        holder?.contactContainer?.visibility = View.GONE
        holder?.undoData?.visibility = View.VISIBLE
        holder?.progressBar?.visibility = View.VISIBLE
    }

    inner class MyHolder(view: View?) : RecyclerView.ViewHolder(view), ISwipeToDeleteHolder<Int> {

        var deletedName = view?.user_name_deleted
        var name = view?.user_name
        var phone = view?.user_phone_number
        var contactContainer = view?.contact_container
        var undoContainer = view?.undo_container
        var undoData = view?.undo_data
        var undoButton = view?.button_undo
        var progressBar = view?.progress_indicator


        override var listener: UndoClickListener<Int>
            get() = listener
            set(value) {
                value
            }

        override var isPendingDelete: Boolean = false


        override val topContainer: View
            get() =
            if (isPendingDelete) undoContainer as View
            else contactContainer as View

        override var key: Int = -1



        init {
            undoButton?.setOnClickListener { listener.onUndoClick(key) }
        }


    }
}