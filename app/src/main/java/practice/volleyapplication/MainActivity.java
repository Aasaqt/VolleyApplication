package practice.volleyapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    int page = 1;
    boolean reachedEnd = false;
    NewsListAdapter mAdapter;
    boolean loading = false;
    ArrayList<NewsListModel> movieListModel;
    RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onRequestStarted() {

        }

        @Override
        public void onRequestCompleted(Object responseObject) {
            loading = false;
            Gson gson = new Gson();
            ListModel listModel = gson.fromJson(responseObject.toString(), ListModel.class);
            for (int i = 0; i < listModel.getData().size(); i++) {
                movieListModel.add(listModel.getData().get(i));
            }
            if (listModel.getData().size() < 10)
                reachedEnd = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
        }

        @Override
        public void onRequestError(int errorCode, String message) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_step);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        movieListModel = new ArrayList<NewsListModel>();
        mAdapter = new NewsListAdapter(this, movieListModel);
        recyclerView.setAdapter(mAdapter);
        page = 1;
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (((mLayoutManager.findLastVisibleItemPosition() + 3) >= mLayoutManager.getItemCount()) && !loading) {
                    page++;
                    if (!loading && !reachedEnd) {
                        Controller.getMovies(MainActivity.this, mRequestListener);
                        loading = true;
                        Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Controller.getMovies(this,mRequestListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
