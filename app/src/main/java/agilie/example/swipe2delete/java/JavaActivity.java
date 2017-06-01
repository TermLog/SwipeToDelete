package agilie.example.swipe2delete.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.agilie.swipe2delete.SwipeToDeleteDelegate;

import test.alexzander.swipetodelete.R;

import static android.widget.LinearLayout.VERTICAL;
import static agilie.example.swipe2delete.UtilityKt.prepareContactList;

public class JavaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Java Activity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initJavaAdapter();
    }

    private void initJavaAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        JavaAdapter adapter = new JavaAdapter(prepareContactList(60));
        adapter.setSwipeToDeleteDelegate(new SwipeToDeleteDelegate(adapter.getUsers(), adapter));

        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(adapter.getSwipeToDeleteDelegate().getItemTouchCallBack());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}