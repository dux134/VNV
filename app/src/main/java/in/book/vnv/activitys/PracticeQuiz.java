package in.book.vnv.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.entity.QuestionDataModel;

public class PracticeQuiz extends AppCompatActivity {
    private ArrayList<QuestionDataModel> list = new ArrayList<>();

    private ProgressDialog progressDialog;
    public static String isSolved = "false";
    public static String exerciseNo = "1";
    public static String chapterName = "analogy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);
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
        JSONArray object = new JSONObject(string).getJSONArray(exerciseNo);
        for(int i=0;i< object.length();i++) {
            JSONObject ob = (JSONObject) object.getJSONObject(i);
            list.add(new QuestionDataModel(ob.getString("question"), ob.getString("a"), ob.getString("b"), ob.getString("c"), ob.getString("d"), ob.getString("ans")));
        }
        progressDialog.dismiss();
    }
}
