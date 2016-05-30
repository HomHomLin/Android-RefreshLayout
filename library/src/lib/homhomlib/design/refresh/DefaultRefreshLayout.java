package lib.homhomlib.design.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Linhh on 16/5/25.
 */
public class DefaultRefreshLayout extends RefreshLayout implements RefreshLayout.OnRefreshListener{

    private RefreshLoadingView mViewRefreshIcon;

    private View mViewRefreshBg;

    private TextView mTextView;

    private OnRefreshListener mOnRefreshListener;

    public DefaultRefreshLayout(Context context) {
        this(context,null);
    }

    public DefaultRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DefaultRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mViewRefreshBg = View.inflate(getContext(), R.layout.refresh_bg, null);
        mViewRefreshIcon = (RefreshLoadingView)mViewRefreshBg.findViewById(R.id.icon);
        mTextView = (TextView)mViewRefreshBg.findViewById(R.id.alert);
        //设置最大下拉位置
//        setSlidingDistance(getRefreshDistance());
        setBackgroundView(mViewRefreshBg);
        super.setOnRefreshListener(this);
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    @Override
    public void setRefreshing(boolean refresh) {
        super.setRefreshing(refresh);
        if(!refresh){
            mViewRefreshIcon.stop();
            mTextView.setText("完成");
        }
    }

    @Override
    public void onSlidingOffset(View view, float delta) {
        super.onSlidingOffset(view, delta);
        float changeOffset = delta / getRefreshDistance();
        mViewRefreshIcon.setAlpha(changeOffset);
        if(changeOffset >= 1){
            mTextView.setText("松开刷新");
        }else{
            mTextView.setText("用力拉呀");
        }
    }

    @Override
    public void onRefresh() {
        mViewRefreshIcon.start();
        mTextView.setText("loading");
        if(mOnRefreshListener != null){
            mOnRefreshListener.onRefresh();
        }
    }
}
