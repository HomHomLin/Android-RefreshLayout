package lib.homhomlib.design.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Linhh on 16/5/24.
 */
public class RefreshLoadingView extends ImageView {

    public interface Callback{
        void onAnimationEnd();
    }

    private AnimationDrawable mAnimation;

    private Callback mListener;

    private int mTotalTime;

    public RefreshLoadingView(Context context) {
        this(context,null);
    }

    public RefreshLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void stop(){
        if(mAnimation != null){
            mAnimation.stop();
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(null != mListener){
                mListener.onAnimationEnd();
            }
        }
    };

    public void start(){
        if(null != mAnimation){
            calcTimerAnimationDuration();
            mAnimation.start();
            if(mAnimation.isOneShot()){
                int timerAnimationDuration = mTotalTime;
                postDelayed(mRunnable,timerAnimationDuration);
            }
        }
    }

    public void setCallback(Callback c){
        mListener = c;
    }

    private void init(){
        mAnimation = (AnimationDrawable) getBackground();
        stop();
    }

    public void setBackground(int drawableRes){
        if(0 == drawableRes)
            return;
        stop();
        setBackgroundResource(drawableRes);
        mAnimation = (AnimationDrawable) getBackground();
    }

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		mAnimation.start();
//		super.onWindowFocusChanged(hasFocus);
//	}

    private int calcTimerAnimationDuration() {
        int size = mAnimation.getNumberOfFrames();
        for (int i = 0; i < size; i++) {
            mTotalTime += mAnimation.getDuration(i);
        }
        return mTotalTime;
    }

    public int getTotalTime(){
        return mTotalTime;
    }

}
