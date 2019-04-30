package in.book.vnv.activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import androidx.cardview.widget.CardView;
import in.book.vnv.R;
import in.book.vnv.entity.QuestionDataModel;
import in.book.vnv.util.FileUtil;

public class PracticeQuiz extends AppCompatActivity {
    private static final String TAG = "dux";
    private ArrayList<QuestionDataModel> list = new ArrayList<>();
    private ArrayList<QuestionDataModel> listUnsolved = new ArrayList<>();

    private ProgressDialog progressDialog;
    public static String isSolved = "false";
    public static String exerciseNo = "1";
    public static String chapterNo = "1";

    private TextView question, optionA, optionB, optionC, optionD, answer, description;
    private int questionNo = 0;
    LinearLayout linearLayoutA, linearLayoutB, linearLayoutC, linearLayoutD;
    private JSONArray jObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);

        question = findViewById(R.id.practice_test_question);
        optionA = findViewById(R.id.practice_test_optionA);
        optionB = findViewById(R.id.practice_test_optionB);
        optionC = findViewById(R.id.practice_test_optionC);
        optionD = findViewById(R.id.practice_test_optionD);
        answer = findViewById(R.id.practice_question_answer);
        description = findViewById(R.id.practice_question_description);
        try {
            loadQuestion();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        linearLayoutA = findViewById(R.id.practice_test_layout_a);
        linearLayoutB = findViewById(R.id.practice_test_layout_b);
        linearLayoutC = findViewById(R.id.practice_test_layout_c);
        linearLayoutD = findViewById(R.id.practice_test_layout_d);
        linearLayoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(questionNo).getSelectedAnswer().equalsIgnoreCase("")) {
                    list.get(questionNo).setSelectedAnswer("a");
                    showQuestion("present");
                }
            }
        });
        linearLayoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(questionNo).getSelectedAnswer().equalsIgnoreCase("")) {
                    list.get(questionNo).setSelectedAnswer("b");
                    showQuestion("present");
                }
            }
        });
        linearLayoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(questionNo).getSelectedAnswer().equalsIgnoreCase("")) {
                    list.get(questionNo).setSelectedAnswer("c");
                    showQuestion("present");
                }
            }
        });
        linearLayoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(questionNo).getSelectedAnswer().equalsIgnoreCase("")) {
                    list.get(questionNo).setSelectedAnswer("d");
                    showQuestion("present");
                }
            }
        });
        CardView previous = findViewById(R.id.practice_quiz_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (0 < questionNo) {
                    questionNo--;
                    showQuestion("previous");
                }
            }
        });
        CardView next = findViewById(R.id.practice_quiz_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() - 1 > questionNo) {
                    questionNo++;
                    showQuestion("next");
                }
            }
        });
        findViewById(R.id.practice_quiz_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        showQuestion("next");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showQuestion("present");
    }

    private void showQuestion(String mode) {
        QuestionDataModel model = list.get(questionNo);
        switch(mode) {
            case "next":
                while(model.isSolved() && questionNo < list.size()-1) {
                    questionNo++;
                    model = list.get(questionNo);
                }
                break;
            case "previous":
                while(model.isSolved() && questionNo > 0) {
                    questionNo--;
                    model = list.get(questionNo);
                }
                break;
            case "present":

        }
        if(model.isSolved())
            onBackPressed();
        question.setText(model.getQuestion());
        optionA.setText(model.getOptionA());
        optionB.setText(model.getOptionB());
        optionC.setText(model.getOptionC());
        optionD.setText(model.getOptionD());
        answer.setText(model.getAnswer());
        description.setText(model.getDescription());
        linearLayoutC.setBackgroundColor(Color.WHITE);
        linearLayoutA.setBackgroundColor(Color.WHITE);
        linearLayoutD.setBackgroundColor(Color.WHITE);
        linearLayoutB.setBackgroundColor(Color.WHITE);

        if (!model.getSelectedAnswer().equals("")) {
            setColor(model.getAnswer(), Color.GREEN);
            description.setVisibility(View.VISIBLE);
            if (!model.getAnswer().equalsIgnoreCase(model.getSelectedAnswer()))
                setColor(model.getSelectedAnswer(), Color.RED);
        } else {
            answer.setVisibility(View.INVISIBLE);
            description.setVisibility(View.INVISIBLE);
        }
    }

    private void setColor(String option, int color) {
        switch (option.toLowerCase()) {
            case "a":
                linearLayoutA.setBackgroundColor(color);
                break;
            case "b":
                linearLayoutB.setBackgroundColor(color);
                break;
            case "c":
                linearLayoutC.setBackgroundColor(color);
                break;
            case "d":
                linearLayoutD.setBackgroundColor(color);
                break;
        }
    }

    public String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    private void loadQuestion() throws JSONException, IOException {
        list.clear();
        String data = FileUtil.readFromFile(Dashboard.PATH + "app.json");
        jObject = new JSONObject(data).getJSONObject("practice").getJSONObject(chapterNo).getJSONObject(exerciseNo).getJSONArray("questions");
        String string = this.AssetJSONFile(Chapters.chapterName + ".json", getApplicationContext());
        JSONArray object = new JSONObject(string).getJSONArray(exerciseNo);
        for (int i = 0; i < object.length(); i++) {
            JSONObject ob = object.getJSONObject(i);
            list.add(new QuestionDataModel(ob.getString("question"), ob.getString("a"), ob.getString("b"), ob.getString("c"), ob.getString("d"), ob.getString("ans"),jObject.getBoolean(i)));
        }
    }

    @Override
    public void onBackPressed() {
        progressDialog = new ProgressDialog(PracticeQuiz.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        updateDatabase();
        progressDialog.dismiss();
        super.onBackPressed();
        finish();
    }

    private void updateDatabase() {
        try {
            String data = FileUtil.readFromFile(Dashboard.PATH + "app.json");
            JSONObject object = new JSONObject(data);
            JSONObject ob1 = object.getJSONObject("practice").getJSONObject(chapterNo);

            JSONObject map = new JSONObject();
            JSONArray arr = new JSONArray();
            int solved = 0,solvedNow = 0;
            for(int i=0;i<list.size();i++) {
                QuestionDataModel q = list.get(i);
                if(!q.isSolved() && !q.getSelectedAnswer().equalsIgnoreCase("")) {
                    arr.put(true);
                    solved++;
                    solvedNow++;
                } if(q.isSolved()) {
                    arr.put(true);
                    solved++;
                } else {
                    arr.put(false);
                }
            }
            map.put("questions", arr);
            map.put("solved", solved + "");
            map.put("total", list.size() + "");
            ob1.put(exerciseNo, map);

            int n = Integer.parseInt(ob1.get("solved").toString());
            ob1.put("solved", solvedNow + n + "");
            FileUtil.writeToFile(object.toString(), Dashboard.PATH + "app.json");

        } catch (Exception e) {
        }
    }
}
