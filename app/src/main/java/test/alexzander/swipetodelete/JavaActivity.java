package test.alexzander.swipetodelete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.swipetodeletelib.SwipeToDeleteAdapter;

import test.alexzander.swipetodelete.sample.JavaAdapter;

import static android.widget.LinearLayout.VERTICAL;
import static test.alexzander.swipetodelete.UtilityKt.prepareContactList;

public class JavaActivity extends AppCompatActivity {

    JavaAdapter javaAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.java_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("JavaActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initJavaAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (javaAdapter != null) {
            javaAdapter.notifyDataSetChanged();
        }
    }

    private void initJavaAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        JavaAdapter adapter = new JavaAdapter(prepareContactList(60));
        adapter.setSwipeToDeleteAdapter(new SwipeToDeleteAdapter(adapter.getUsers(), this, adapter));

        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.getSwipeToDeleteAdapter().getItemTouchCallBack());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}