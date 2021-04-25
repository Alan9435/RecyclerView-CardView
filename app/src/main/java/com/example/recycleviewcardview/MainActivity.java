package com.example.recycleviewcardview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatbtn;
    private ArrayList<ExampleItem> exampleList = new ArrayList<>();
    private String url = "https://pixabay.com/api/?key=21323023-1a023bbaa5108b470625b5820&q=kitten&image_type=photo&pretty=true&per_page=100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatbtn = findViewById(R.id.floatbtn);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exampleList.clear();
                getData();
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();    //通知adapter數據變更
            }
        });

        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setHasFixedSize(true);                //確定item不會影響RecyclerView的寬高時可以設置
        mRecyclerView.setItemViewCacheSize(200);  //設定緩存的item數
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getData();


    }

    private void getData() {
//        for (int i=0;i<=30;i++){
//            exampleList.add(new ExampleItem(R.drawable.ic_baseline_audiotrack_24, String.valueOf(i), String.valueOf(i)));
//            exampleList.add(new ExampleItem(R.drawable.ic_baseline_directions_boat_24, String.valueOf(i), String.valueOf(i)));
//            exampleList.add(new ExampleItem(R.drawable.ic_android_black_24dp, String.valueOf(i), String.valueOf(i)));
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = DoGet(url);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("hits");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String id = jsonObject2.getString("id");
                        String likes = jsonObject2.getString("likes");
                        String img = jsonObject2.getString("webformatURL");
                        exampleList.add(new ExampleItem(img,id,likes));
                    }runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mAdapter = new ExampleAdapter(exampleList);
                            mRecyclerView.setAdapter(mAdapter);
                            recyclerViewAction(mRecyclerView,exampleList,mAdapter);
                        }
                    });


                } catch (Exception e) {
                    Log.v("***","*** error: " + e);
                }
            }
        }).start();
    }

    private void recyclerViewAction(RecyclerView recyclerView, final ArrayList<ExampleItem> choose, final RecyclerView.Adapter myAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            //管理操作
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            //上下拖曳
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //左右滑動
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        choose.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    public String DoGet(String hostURL) {
        URL url = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;
        String receiveData = "";
        try {
            url = new URL(hostURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            /*HttpURLConnection默認使用GET 故不用setRequestMethod
              也默認伺服器讀取 所以省略           conn.setDoInput(true)
                */
            httpURLConnection.setUseCaches(false);  //禁用網路緩存
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}