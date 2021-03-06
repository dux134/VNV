package in.book.vnv.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.SliderViewPager;
import in.book.vnv.adapters.DailyTestAdapter;
import in.book.vnv.entity.DailyScore;
import in.book.vnv.util.FileUtil;
import me.relex.circleindicator.CircleIndicator;

public class Dashboard extends AppCompatActivity {
    private final String TAG = "TAG";
    public ViewPager viewpager;
    private ArrayList<String> sliderImagesUrlList;
    private SliderViewPager sliderViewPager;
    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter1;
    private static ArrayList<DailyScore> list1 = new ArrayList<>();
    public final static String PATH = Environment.getExternalStorageDirectory() + "/Android/data/in.book.vnv/";
    private TextView totalQuestion,solvedQuestion,noOfTestGiven,averageScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ImageView toolbar_image = findViewById(R.id.toolbar_image);
        toolbar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        createAppFolder();
        loadImageInSlider();


        totalQuestion = findViewById(R.id.practice_totalquestion);
        solvedQuestion = findViewById(R.id.practice_solvedquestion);
        noOfTestGiven = findViewById(R.id.dashboard_no_of_testgiven);
        averageScore = findViewById(R.id.dashboard_average_score);

        recyclerView1 = findViewById(R.id.quicktext_recycler);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter1 = new DailyTestAdapter(list1,Dashboard.this);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        LinearLayout practiceLayout = findViewById(R.id.practice_layout);
        practiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,Content.class));
            }
        });

        CardView testTotal = findViewById(R.id.test_total);
        CardView testSolved = findViewById(R.id.test_solved);
        CardView testUnsolved = findViewById(R.id.test_unsolved);
        testTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,TestDescription.class));
            }
        });
        testSolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,TestDescription.class));
            }
        });
        testUnsolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,TestDescription.class));
            }
        });
    }

    private void createAppFolder() { ;
        File f1 = new File(PATH);
        if(!f1.exists())
            f1.mkdir();
        File root = new File(PATH+"app.json");
        Log.d(TAG, "createAppFolder: "+root.toString());
        if (!root.exists()) {
            copyFile("app.json");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadScoreData();
            loadJsonData();
            loadTextFileds();
        } catch (Exception e) {

        }
    }

    public void loadScoreData() throws IOException, JSONException {
        list1.clear();
        String data = FileUtil.readFromFile(PATH+"app.json");
        JSONObject object = new JSONObject(data).getJSONObject("lasttestscores");
        Log.d(TAG, "loadScoreData: " + object.length() );
        for(int i=1;i<=object.length();i++) {
            JSONObject ob = object.getJSONObject(i + "");
            Log.d(TAG, "loadScoreData: list :"+ob.toString());
            list1.add(new DailyScore(i + "", ob.getString("date"), ob.getString("solved"), ob.getString("correct"), ob.getString("total")));
            int n = Integer.parseInt(ob.getString("total")) + 1;
            ob.put("total",n+"");
        }
        adapter1.notifyDataSetChanged();
    }

    public void loadJsonData() {

    }

    private void loadImageInSlider() {
        sliderImagesUrlList = new ArrayList<String>();
        sliderImagesUrlList.add("https://quotes4ever.com/wp-content/uploads/2017/09/luxury-quotes-46.jpg");
        sliderImagesUrlList.add("https://quotes4ever.com/wp-content/uploads/2017/09/luxury-quotes-46.jpg");
        sliderImagesUrlList.add("https://quotes4ever.com/wp-content/uploads/2017/09/luxury-quotes-46.jpg");
        sliderImagesUrlList.add("https://quotes4ever.com/wp-content/uploads/2017/09/luxury-quotes-46.jpg");
        sliderImagesUrlList.add("https://quotes4ever.com/wp-content/uploads/2017/09/luxury-quotes-46.jpg");
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        sliderViewPager = new SliderViewPager(viewpager,Dashboard.this, sliderImagesUrlList);
        viewpager.setAdapter(sliderViewPager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        viewpager.setCurrentItem(2);
        indicator.setViewPager(viewpager);
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String newFileName = PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private void loadTextFileds() {
        try {
            String data = FileUtil.readFromFile(PATH + "app.json");
            JSONObject object = new JSONObject(data);
            JSONObject ob1 = object.getJSONObject("quicktests");
            noOfTestGiven.setText(ob1.getString("nooftest"));
            averageScore.setText(ob1.getString("averagescore"));
            JSONObject ob2 = object.getJSONObject("practiceDashboard");
            solvedQuestion.setText(ob2.getString("solvedQuestion"));
            totalQuestion.setText(ob2.getString("totalQuestion"));
        } catch(Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
}