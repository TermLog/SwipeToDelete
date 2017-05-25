package test.alexzander.swipetodelete.sample;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.swipetodeletelib.AnimationUpdateListener;
import com.example.swipetodeletelib.AnimatorListener;
import com.example.swipetodeletelib.ISwipeToDeleteAdapter;
import com.example.swipetodeletelib.ISwipeToDeleteHolder;
import com.example.swipetodeletelib.SwipeToDeleteAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import test.alexzander.swipetodelete.R;


public class JavaAdapter extends RecyclerView.Adapter implements ISwipeToDeleteAdapter<String, User, JavaAdapter.Holder> {

    private List<User> users;
    private Context context;
    private SwipeToDeleteAdapter swipeToDeleteAdapter;

    public JavaAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setSwipeToDeleteAdapter(SwipeToDeleteAdapter swipeToDeleteAdapter) {
        this.swipeToDeleteAdapter = swipeToDeleteAdapter;
    }

    public SwipeToDeleteAdapter getSwipeToDeleteAdapter() {
        return swipeToDeleteAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ISwipeToDeleteHolder) {
            swipeToDeleteAdapter.onBindViewHolder((ISwipeToDeleteHolder) holder, users.get(position).getName(), position);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Nullable
    @Override
    public AnimatorListener getAnimatorListener() {
        return null;
    }

    @Nullable
    @Override
    public AnimationUpdateListener getAnimationUpdateListener() {
        return null;
    }

    @Override
    public int findItemPositionByKey(String key) {
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getName() == key) {
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
                swipeToDeleteAdapter.onUndo(key);
            }
        });
    }

    @Override
    public boolean deleteAction(User item) {
        return false;
    }

    @Override
    public void onItemDeleted(User item) {

    }

    @Override
    public void onDeleteFailed(User item) {

    }

   public class Holder extends RecyclerView.ViewHolder implements ISwipeToDeleteHolder<String> {

        Button userButtonUndo;
        FrameLayout userContainer;

        AppCompatTextView userName;
        FrameLayout undoContainer;

        Holder(View view) {
            super(view);
            userName = (AppCompatTextView) view.findViewById(R.id.user_name);
            userContainer = (FrameLayout) view.findViewById(R.id.user_container);

            userButtonUndo = (Button) view.findViewById(R.id.user_button_undo);
            undoContainer = (FrameLayout) view.findViewById(R.id.user_undo_container);
        }

        boolean isPendingDelete;

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

        String key;

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