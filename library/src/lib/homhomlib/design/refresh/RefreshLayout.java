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

    private int mRefreshDistance = 250;//default

    private boolean mIsRefreshing = false;

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
        super.setSlidingDistance(mRefreshDistance);
        super.setSlidingMode(SLIDING_MODE_TOP);
        super.setSlidingListener(this);
        super.setOnTouchListener(this);
    }

    @Deprecated
    @Override
    public void setSlidingDistance(int distance) {
        //don't use
    }

    @Deprecated
    @Override
    public void setSlidingMode(int mode) {

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
        if(mIsRefreshing){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    protected void doRefresh(){
        mOnRefreshListener.onRefresh();
        mIsRefreshing = true;
    }

    public void setRefreshing(boolean refresh){
        if(refresh){
            //刷新
            if(mOnRefreshListener != null){
                getInstrument().smoothTo(getTargetView(),mRefreshDistance,200);
                doRefresh();
            }
        }else{
            //停止
            getInstrument().reset(getTargetView(),200);
            mIsRefreshing = false;
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
                if(getInstrument().getTranslationY(getTargetView()) >= mRefreshDistance) {
                    //刷新
                    if(mOnRefreshListener != null){
                        doRefresh();
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    public int getRefreshDistance(){
        return this.mRefreshDistance;
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
