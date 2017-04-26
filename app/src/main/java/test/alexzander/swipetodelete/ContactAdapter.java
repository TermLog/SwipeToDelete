package test.alexzander.swipetodelete;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import static test.alexzander.swipetodelete.ContactAdapterUtils.PENDING_DURATION;

/**
 * Created by AlexZandR on 4/24/17
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> implements ItemSwipeListener,
        UndoClickListener {

    private Handler handler = new Handler(Looper.getMainLooper());
    private HashMap<String, Runnable> pendingRemoveActions = new HashMap<>(1);
    private HashMap<String, ValueAnimator> animatorsMap = new HashMap<>(1);
    private List<ItemContact> contacts;

    public ContactAdapter(List<ItemContact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        return new ContactHolder(view, this);
    }

    @Override
    public void onBindViewHolder(final ContactHolder holder, int position) {
        try {
            final ItemContact contact = contacts.get(position);
            if (contact == null) {
                contacts.remove(position);
                notifyItemRemoved(position);
            } else {
                holder.isPendingDelete = contact.isPendingDelete;
                if (contact.isPendingDelete) {
                    onBindPendingContact(holder, contact);
                } else {
                    onBindCommonContact(holder, contact);
                }
            }
        } catch (IndexOutOfBoundsException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onItemSwiped(final ContactHolder holder, final int swipeDir) {
        int position = holder.getAdapterPosition();
        final ItemContact contact = contacts.get(position);
        if (contact.isPendingDelete) {
            removeItem(contact);
        } else {
            contact.isPendingDelete = true;
            contact.setDirection(swipeDir);
            notifyItemChanged(position);
        }
    }

    @Override
    public void onUndoClick(final int position) {
        if (position != -1) {
            final String mapKey = contacts.get(position).name;
            handler.removeCallbacks(pendingRemoveActions.get(mapKey));
            contacts.get(position).isPendingDelete = false;
            notifyItemChanged(position);
            ContactAdapterUtils.clearAnimator(animatorsMap.get(mapKey));
        }
    }

    private void onBindCommonContact(final ContactHolder holder, final ItemContact contact) {
        clearAnimation(contact, holder.progressIndicator);
        holder.name.setText(contact.name);
        holder.phone.setText(contact.phone);
        holder.contactContainer.setVisibility(View.VISIBLE);
        holder.undoData.setVisibility(View.GONE);
    }

    private void onBindPendingContact(final ContactHolder holder, final ItemContact contact) {
        holder.deletedName.setText("You have just deleted " + contact.name);
        holder.contactContainer.setVisibility(View.GONE);
        holder.undoData.setVisibility(View.VISIBLE);
        holder.progressIndicator.setVisibility(View.VISIBLE);
        holder.progressIndicator.setX(contact.posX);

        if (pendingRemoveActions.get(contact.name) == null) {
            pendingRemoveActions.put(contact.name, new Runnable() {
                @Override
                public void run() {
                    removeItem(contact);
                }
            });
        }

        handler.postDelayed(pendingRemoveActions.get(contact.name), PENDING_DURATION);
        final ValueAnimator animator;
        if (animatorsMap.get(contact.name) != null) {
            animator = animatorsMap.get(contact.name);
            ContactAdapterUtils.initAnimator(holder.progressIndicator, contact, animator);
        } else {
            animator = ContactAdapterUtils.createAnimator(holder.progressIndicator, contact);
            animatorsMap.put(contact.name, animator);
        }
        animator.start();
    }

    private void removeItem(final ItemContact contact) {
        final int pos = contacts.indexOf(contact);
        handler.removeCallbacks(pendingRemoveActions.remove(contact.name));
        pendingRemoveActions.remove(contact.name);
        contacts.remove(contact);
        notifyItemRemoved(pos);
        ContactAdapterUtils.clearContact(contact);
        ContactAdapterUtils.clearAnimator(animatorsMap.remove(contact.name));
    }

    private void clearAnimation(final ItemContact contact, final View progressView) {
        ContactAdapterUtils.clearAnimator(animatorsMap.get(contact.name));
        ContactAdapterUtils.clearContact(contact);
        ContactAdapterUtils.clearView(progressView);
    }
}
