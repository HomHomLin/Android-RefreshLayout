package lib.homhomlib.design.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import lib.homhomlib.design.SlidingLayout;

/**
 * Created by Linhh on 16/5/24.
 */
public class RefreshLayout extends SlidingLayout implements SlidingLayout.SlidingListener,View.OnTouchListener{
    private OnRefreshListener mOnRefreshListener;

    private boolean mIsRefreshing = false;

    private int mRefreshDistance = 200;//default

    private int mPauseTime = 200;//滑动到刷新距离的时间间隔
    private int mResetTime = 200;//恢复到初始状态的时间间隔

    private SlidingListener mDelegateSlidingListener;
    private OnTouchListener mDelegateTouchListener;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnRefreshListener{
        public void onRefresh();
    }

    private void init(){
        //只开启下拉模式
        super.setSlidingMode(SLIDING_MODE_TOP);
        //设置滑动监听
        super.setSlidingListener(this);
        //设置触摸监听
        super.setOnTouchListener(this);
    }

    @Deprecated
    @Override
    public void setSlidingMode(int mode) {
        //禁用滑动设置
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.mDelegateTouchListener = l;
    }

    @Override
    public void setSlidingListener(SlidingListener slidingListener) {
        this.mDelegateSlidingListener = slidingListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.mOnRefreshListener = onRefreshListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果正在刷新就不拦截
        if(mIsRefreshing){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 是否正在刷新
     * @return 刷新状态
     */
    public boolean isRefreshing(){
        return this.mIsRefreshing;
    }

    /**
     * 设置刷新状态，false为停止刷新
     * @param refresh
     */
    public void setRefreshing(boolean refresh){
        mIsRefreshing = refresh;
        if(refresh){
            getInstrument().smoothTo(getTargetView(), mRefreshDistance, mPauseTime);
            //刷新
            if(mOnRefreshListener != null){
                mOnRefreshListener.onRefresh();
            }
        }else{
            //停止
            getInstrument().reset(getTargetView(),mResetTime);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mDelegateTouchListener != null){
            //I don't care what you return
            mDelegateTouchListener.onTouch(v,event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //如果终止动作时，滑动距离大于预设距离就刷新，否则交给slidinglayout做处理
                if(getInstrument().getTranslationY(getTargetView()) >= mRefreshDistance) {
                    //刷新
                    if(mOnRefreshListener != null){
                        setRefreshing(true);
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    /**
     * 获取刷新距离
     * @return
     */
    public int getRefreshDistance(){
        return this.mRefreshDistance;
    }

    /**
     * 设置刷新距离，此值会影响刷新触发的距离和刷新恢复的距离，但不影响最大刷新距离
     * @param refreshDistance
     */
    public void setRefreshDistance(int refreshDistance){
        this.mRefreshDistance = refreshDistance;
    }

    @Override
    public void onSlidingOffset(View view, float delta) {
        if(mDelegateSlidingListener != null){
            mDelegateSlidingListener.onSlidingOffset(view,delta);
        }
    }

    @Override
    public void onSlidingStateChange(View view, int state) {
        if(mDelegateSlidingListener != null){
            mDelegateSlidingListener.onSlidingStateChange(view,state);
        }
    }

    @Override
    public void onSlidingChangePointer(View view, int pointerId) {
        if(mDelegateSlidingListener != null){
            mDelegateSlidingListener.onSlidingChangePointer(view,pointerId);
        }
    }
}
