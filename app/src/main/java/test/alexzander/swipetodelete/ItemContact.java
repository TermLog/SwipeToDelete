package test.alexzander.swipetodelete;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by AlexZandR on 4/24/17
 */

public class ItemContact {

    public static final int LEFT = -1;
    public static final int RIGHT = 1;

    boolean isPendingDelete = false;
    boolean isRunningAnimation = false;
    int direction;
    float posX = 0;
    String name;
    String phone;

    public ItemContact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    void setDirection(int swipeDir) {
        if (ItemTouchHelper.LEFT == swipeDir || ItemTouchHelper.START == swipeDir) {
            direction = LEFT;
        } else {
            direction = RIGHT;
        }
    }
}
