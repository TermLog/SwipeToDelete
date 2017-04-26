package test.alexzander.swipetodelete;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexZandR on 4/24/17
 */

public abstract class ContactAdapterUtils {

    public static final long DELETING_DURATION = 3000;
    public static final long PENDING_DURATION = DELETING_DURATION - 150;
    public static final float DEVICE_SCREEN_WIDTH = 1080F;

    private ContactAdapterUtils() {
    }

    public static List<ItemContact> prepareContactList(int count) {
        List<ItemContact> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new ItemContact("User Name " + String.valueOf(i), "+1234567" + String.valueOf(i)));
        }
        return result;
    }

    public static ValueAnimator createAnimator(final View view, final ItemContact contact) {
        return initAnimator(view, contact, null);
    }

    public static ValueAnimator initAnimator(final View view, final ItemContact contact,
                                             ValueAnimator animator) {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(contact.posX, DEVICE_SCREEN_WIDTH * contact.direction);
        } else {
            animator.removeAllUpdateListeners();
            animator.removeAllListeners();
            animator.setFloatValues(contact.posX, DEVICE_SCREEN_WIDTH * contact.direction);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float posX = (Float) animation.getAnimatedValue();
                view.setX(posX);
                contact.posX = posX;
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                contact.isRunningAnimation = true;
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                contact.isRunningAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                clearContact(contact);
                clearView(view);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setDuration((long) (DELETING_DURATION * (DEVICE_SCREEN_WIDTH - contact.posX * contact.direction) / DEVICE_SCREEN_WIDTH));
        return animator;
    }

    public static void clearAnimator(final ValueAnimator animator) {
        if (animator != null) {
            animator.cancel();
            animator.removeAllUpdateListeners();
            animator.removeAllListeners();
        }
    }

    public static void clearContact(final ItemContact contact) {
        if (contact != null) {
            contact.isPendingDelete = false;
            contact.isRunningAnimation = false;
            contact.posX = 0;
        }
    }

    public static void clearView(final View view) {
        if (view != null) {
            view.setX(0);
            view.setVisibility(View.GONE);
        }
    }
}
