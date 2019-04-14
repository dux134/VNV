package in.book.vnv.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.book.vnv.R;
import in.book.vnv.adapters.QuestionAdapter;
import in.book.vnv.entity.QuestionDataModel;

public class Questions extends AppCompatActivity {
    private ArrayList<QuestionDataModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    public static String isSolved = "false";
    public static String chapterName = "analogy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        progressDialog = new ProgressDialog(Questions.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recyclerView = findViewById(R.id.chaper_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new QuestionAdapter(list, Questions.this, new QuestionAdapter.RecyclerItemListener() {
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        try {
            loadQuestion();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    private void loadQuestion() throws JSONException, IOException {
        list.clear();
        String string = this.AssetJSONFile(chapterName+".json",getApplicationContext());
        JSONObject object = new JSONObject(string);
        for(int i=1;i<= object.length();i++) {
            JSONObject ob = object.getJSONObject(i+"");
            if(ob.getString("solved").equalsIgnoreCase(isSolved))
                list.add(new QuestionDataModel(ob.getString("question"),ob.getString("a"),ob.getString("b"),ob.getString("c"),ob.getString("d"),ob.getString("ans")));
        }
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}
