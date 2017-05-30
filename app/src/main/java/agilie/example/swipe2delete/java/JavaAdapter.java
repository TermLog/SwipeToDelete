package agilie.example.swipe2delete.java;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.agilie.swipe2delete.SwipeToDeleteDelegate;
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteAdapter;
import com.agilie.swipe2delete.interfaces.ISwipeToDeleteHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import agilie.example.swipe2delete.User;
import test.alexzander.swipetodelete.R;


public class JavaAdapter extends RecyclerView.Adapter implements ISwipeToDeleteAdapter<String, User, JavaAdapter.Holder> {

    private List<User> users;
    private SwipeToDeleteDelegate swipeToDeleteDelegate;

    public JavaAdapter(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public SwipeToDeleteDelegate getSwipeToDeleteDelegate() {
        return swipeToDeleteDelegate;
    }

    public void setSwipeToDeleteDelegate(SwipeToDeleteDelegate swipeToDeleteDelegate) {
        this.swipeToDeleteDelegate = swipeToDeleteDelegate;
        swipeToDeleteDelegate.setDeletingDuration(5000L);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ISwipeToDeleteHolder) {
            swipeToDeleteDelegate.onBindViewHolder((ISwipeToDeleteHolder) holder, users.get(position).getName(), position);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int findItemPositionByKey(String key) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getName().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBindCommonItem(Holder holder, final String key, User item) {
        holder.userContainer.setVisibility(View.VISIBLE);
        holder.undoContainer.setVisibility(View.GONE);
        holder.userName.setText(item.getName());
    }

    @Override
    public void onBindPendingItem(Holder holder, final String key, User item) {
        holder.undoContainer.setVisibility(View.VISIBLE);
        holder.userContainer.setVisibility(View.GONE);
        holder.userButtonUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeToDeleteDelegate.onUndo(key);
            }
        });
    }

    @Override
    public void removeItem(String key) {
        swipeToDeleteDelegate.removeItem(key);
    }

    @Override
    public void onItemDeleted(User item) {

    }

    public class Holder extends RecyclerView.ViewHolder implements ISwipeToDeleteHolder<String> {

        Button userButtonUndo;
        FrameLayout userContainer;

        AppCompatTextView userName;
        FrameLayout undoContainer;

        boolean isPendingDelete;
        String key;

        Holder(View view) {
            super(view);
            userName = (AppCompatTextView) view.findViewById(R.id.user_name);
            userContainer = (FrameLayout) view.findViewById(R.id.user_container);

            userButtonUndo = (Button) view.findViewById(R.id.user_button_undo);
            undoContainer = (FrameLayout) view.findViewById(R.id.user_undo_container);
        }

        @Override
        public boolean isPendingDelete() {
            return isPendingDelete;
        }

        @Override
        public void setPendingDelete(boolean b) {
            isPendingDelete = b;
        }

        @NotNull
        @Override
        public View getTopContainer() {
            if (isPendingDelete) {
                return undoContainer;
            } else {
                return userContainer;
            }
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public void setKey(String s) {
            key = s;
        }
    }
}