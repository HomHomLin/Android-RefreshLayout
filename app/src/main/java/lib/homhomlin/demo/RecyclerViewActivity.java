package lib.homhomlin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import lib.homhomlib.design.refresh.DefaultRefreshLayout;
import lib.homhomlib.design.refresh.RefreshLayout;

/**
 * Created by Linhh on 16/4/18.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private MonoRefreshLayout mRefreshLayout;
    private final static String TAG = "ListViewActivity";
    private int mCount = 5;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRefreshLayout = (MonoRefreshLayout) findViewById(R.id.slidingLayout);
//        View front = View.inflate(this,R.layout.view_front,null);
        RecyclerView listView = (RecyclerView) this.findViewById(R.id.recycler_view);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setHasFixedSize(true);
        mAdapter = new Adapter();
        listView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCount = 30;
                        mAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                },1500);
            }
        });
//        mSlidingLayout.setFrontView(front);
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = View.inflate(RecyclerViewActivity.this, R.layout.list_item, null);

            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {

        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return 60;
        }
    }
}
