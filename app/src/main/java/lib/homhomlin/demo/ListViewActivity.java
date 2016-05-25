package lib.homhomlin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import lib.homhomlib.design.refresh.DefaultRefreshLayout;
import lib.homhomlib.design.refresh.RefreshLayout;

/**
 * Created by Linhh on 16/4/15.
 */
public class ListViewActivity extends AppCompatActivity {
    private DefaultRefreshLayout mRefreshLayout;
    private final static String TAG = "ListViewActivity";
    private int mCount = 5;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mRefreshLayout = (DefaultRefreshLayout) findViewById(R.id.slidingLayout);
//        View front = View.inflate(this,R.layout.view_front,null);
        ListView listView = (ListView) this.findViewById(R.id.listview);
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
//        mSlidingLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        Log.i("onTouch","up");
//                        return false;
//                    default:
//                        return false;
//                }
//            }
//        });
//        mSlidingLayout.setSlidingListener(new SlidingLayout.SlidingListener() {
//            @Override
//            public void onSlidingOffset(View view, float delta) {
//                Log.i(TAG,"onSlidingOffset:" + delta);
//            }
//
//            @Override
//            public void onSlidingStateChange(View view, int state) {
//                Log.i(TAG,"onSlidingStateChange:" + state);
//            }
//
//            @Override
//            public void onSlidingChangePointer(View view, int pointerId) {
//
//            }
//        });
//        mSlidingLayout.setFrontView(front);
    }

    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(ListViewActivity.this, R.layout.list_item,null);

            }
            return convertView;
        }
    }
}