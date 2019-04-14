package in.book.vnv.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.adapters.ChaptersAdapter;
import in.book.vnv.adapters.QuestionAdapter;
import in.book.vnv.entity.ChaptersDataModel;

public class Chapters extends AppCompatActivity {
    private ArrayList<ChaptersDataModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        progressDialog = new ProgressDialog(Chapters.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recyclerView = findViewById(R.id.chapters_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ChaptersAdapter(list, Chapters.this, new ChaptersAdapter.RecyclerItemListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                startActivity(new Intent(Chapters.this,Questions.class));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        list.clear();
        list.add(new ChaptersDataModel("Exercise 1","23","50","1 - 100"));
        list.add(new ChaptersDataModel("Exercise 2","23","50","101 - 200"));
        list.add(new ChaptersDataModel("Exercise 3","23","50","201 - 300"));
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}