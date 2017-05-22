package test.alexzander.swipetodelete

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by AlexZandR on 4/24/17
 */

class ContactItemTouchCallback(private val listener: ItemSwipeListener?) : ItemTouchHelper.Callback() {

    private val swipeDirs = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.END or
            ItemTouchHelper.START

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeDirs)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
        listener?.onItemSwiped(viewHolder as ContactHolder, swipeDir)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView((viewHolder as ContactHolder).topContainer)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected((viewHolder as ContactHolder).topContainer)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView,
                (viewHolder as ContactHolder).topContainer, dX, dY,
                actionState, isCurrentlyActive)
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView,
                (viewHolder as ContactHolder).topContainer, dX, dY,
                actionState, isCurrentlyActive)
    }
}
