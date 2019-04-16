package in.book.vnv.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.adapters.ContentAdapter;
import in.book.vnv.entity.ContentDataModel;
import in.book.vnv.entity.DailyScore;

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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadContentList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadContentList() throws JSONException {
        list.clear();
        String data = readFromFile(Dashboard.PATH+"app.json");
        JSONObject object = new JSONObject(data).getJSONObject("practice").getJSONObject("noofquestions");
        for(int i=1;i<=object.length();i++) {
            JSONObject ob = object.getJSONObject(i + "");
            int n = Integer.parseInt(ob.getString("solved"));
            int d = Integer.parseInt(ob.getString("total"));
            DecimalFormat format = new DecimalFormat("#0.0");
            list.add(new ContentDataModel(ob.getString("name"),n+"",d+"",format.format((Double.parseDouble(n+"")/d)*100)));
            }
        adapter.notifyDataSetChanged();
    }

    private String readFromFile(String filename) {
        String ret = "";
        try {
            FileInputStream inputStream = new FileInputStream (new File(filename));
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    static {
        list.add(new ContentDataModel("Analogy","125","235","52"));
        list.add(new ContentDataModel("Blood Relation","125","235","52"));
        list.add(new ContentDataModel("Coding and Decoding","125","235","52"));
        list.add(new ContentDataModel("Series","125","235","52"));
    }
}
