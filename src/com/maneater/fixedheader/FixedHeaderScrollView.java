package com.maneater.fixedheader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class FixedHeaderScrollView extends ScrollView {

	public FixedHeaderScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private View targetView = null;

	public void setHeaderView(View view) {
		this.targetView = view;
		invalidate();
	}

	private int mTranslateY = 0;
	private boolean targetIsShow = false;

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		View target = targetView;
		if (target != null) {
			int scollY = getScrollY();
			int top = target.getTop();
			if (scollY >= top) {
				int pLeft = getPaddingLeft();
				int pTop = getPaddingTop();
				targetIsShow = true;
				canvas.translate(pLeft, pTop + scollY - target.getTop());
				drawChild(canvas, target, getDrawingTime());
			} else {
				targetIsShow = false;
			}
		}
		canvas.restore();

	}

	private View mTouchTarget;
	private final Rect mTouchRect = new Rect();

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		final float x = ev.getX();
		final float y = ev.getY();
		final int action = ev.getAction();
		View target = targetView;
		if (action == MotionEvent.ACTION_DOWN && targetIsShow && isViewTouched(target, x, y)) {
			mTouchTarget = target;
		}

		if (mTouchTarget != null) {
			//offset the source event;
			ev.offsetLocation(0, mTouchTarget.getTop() - mTranslateY);
			postDelayed(new Runnable() {

				@Override
				public void run() {
					invalidate();
				}
			}, 100);
		}
		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			mTouchTarget = null;
		}
		//use super dispatch
		return super.dispatchTouchEvent(ev);
	}

	private boolean isViewTouched(View view, float x, float y) {
		view.getHitRect(mTouchRect);
		mTouchRect.bottom = mTouchRect.bottom - mTouchRect.top;
		mTouchRect.top = 0;
		mTouchRect.left += getPaddingLeft();
		mTouchRect.right -= getPaddingRight();
		return mTouchRect.contains((int) x, (int) y);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		mTranslateY = getScrollY();
	}

}
