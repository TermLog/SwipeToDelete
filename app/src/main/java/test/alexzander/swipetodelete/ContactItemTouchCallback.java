package test.alexzander.swipetodelete;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by AlexZandR on 4/24/17
 */

public class ContactItemTouchCallback extends ItemTouchHelper.Callback {

    private int swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.END |
            ItemTouchHelper.START;
    private ItemSwipeListener listener;

    public ContactItemTouchCallback(ItemSwipeListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if (listener != null) {
            listener.onItemSwiped((ContactHolder) viewHolder, swipeDir);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        getDefaultUIUtil().clearView(((ContactHolder) viewHolder).getTopContainer());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(((ContactHolder) viewHolder).getTopContainer());
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDraw(c, recyclerView,
                ((ContactHolder) viewHolder).getTopContainer(), dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDrawOver(c, recyclerView,
                ((ContactHolder) viewHolder).getTopContainer(), dX, dY,
                actionState, isCurrentlyActive);
    }
}
