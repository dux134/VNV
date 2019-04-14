package in.book.vnv.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.adapters.ContentAdapter;
import in.book.vnv.entity.ContentDataModel;

public class Content extends AppCompatActivity {
    private static ArrayList<ContentDataModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        recyclerView = findViewById(R.id.content_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ContentAdapter(list, Content.this, new ContentAdapter.RecyclerItemListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                startActivity(new Intent(Content.this, Chapters.class));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    static {
        list.add(new ContentDataModel("Analogy","125","235","52"));
        list.add(new ContentDataModel("Blood Relation","125","235","52"));
        list.add(new ContentDataModel("Coding and Decoding","125","235","52"));
        list.add(new ContentDataModel("Series","125","235","52"));
    }
}
