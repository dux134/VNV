package in.book.vnv.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.book.vnv.R;
import in.book.vnv.adapters.ChaptersAdapter;
import in.book.vnv.adapters.QuestionAdapter;
import in.book.vnv.entity.ChaptersDataModel;
import in.book.vnv.entity.ContentDataModel;
import in.book.vnv.entity.QuestionDataModel;
import in.book.vnv.util.FileUtil;

public class Chapters extends AppCompatActivity {
    private ArrayList<ChaptersDataModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    public static String chapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        progressDialog = new ProgressDialog(Chapters.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        recyclerView = findViewById(R.id.chapters_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ChaptersAdapter(list, Chapters.this, new ChaptersAdapter.RecyclerItemListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                PracticeQuiz.exerciseNo = adapterPosition+1+"";
                startActivity(new Intent(Chapters.this,PracticeQuiz.class));
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    private void loadChapters() throws JSONException, IOException {
        list.clear();
        String string = this.AssetJSONFile(chapterName+".json",getApplicationContext());
        JSONObject object = new JSONObject(string);

        String appData = FileUtil.readFromFile(Dashboard.PATH+"app.json");
        JSONObject appOb = new JSONObject(appData).getJSONObject("practice");
//        Log.d("TAG", "loadChapters: "+object.length());
        for(int i=1;i <= object.length();i++) {
            JSONArray ob = object.getJSONArray(i+"");
            String questions = "";
            if(i==1)
                questions = 1+" - " + ob.length();
            else
                questions = (((i-1)*100)+1) + " - " + (((i-1)*100)+ob.length());
            String s = appOb
                    .getJSONObject("1")
                    .getJSONObject(i+"")
                    .getString("solved");
//            Log.d("TAG", "loadChapters: "+s);
            ChaptersDataModel ch = new ChaptersDataModel("Exercise "+i,s,ob.length()+"",questions);
            list.add(ch);
            adapter.notifyDataSetChanged();

        }
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadChapters();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
//    private void loadListData() throws JSONException {
//    list.clear();
//    String data = readFromFile(Dashboard.PATH+"app.json");
//    JSONObject ob1 = new JSONObject(data);
//    JSONObject object = ob1.getJSONObject("practice").getJSONObject(chapterName);
//        for(int i=1;i<=object.length();i++) {
//        JSONObject ob = object.getJSONObject(i + "");
//        list.add(new ChaptersDataModel("Exercise "+i,"",ob.length()+"",""));
//        }
//        Log.d("TAG", "loadContentList: "+ob1.toString());
//        adapter.notifyDataSetChanged();
//}

//    private String readFromFile(String filename) {
//        String ret = "";
//        try {
//            FileInputStream inputStream = new FileInputStream (new File(filename));
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString);
//                }
//                inputStream.close();
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        }
//        return ret;
//    }
}