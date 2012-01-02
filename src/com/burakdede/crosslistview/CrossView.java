package com.burakdede.crosslistview;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CrossView extends LinearLayout{

	public CrossView(Context context) {
		super(context);
		this.setClickable(true);
	}
	
	public CrossView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setClickable(true);
	}

	
	public MotionEvent downStart = null;
	
	public boolean onInterceptTouchEvent(MotionEvent event) {
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downStart = MotionEvent.obtain(event);
			break;
		case MotionEvent.ACTION_MOVE:
			float deltaX = event.getX() - downStart.getX();
			if(Math.abs(deltaX) > ViewConfiguration.getTouchSlop() * 2)
				return true;
			
			break;
			
		}
		return false;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		// check if we crossed an item
		float targetWidth = this.getWidth() / 4;
		float deltaX = event.getX() - downStart.getX(),
			deltaY = event.getY() - downStart.getY();
		
		boolean movedAcross = (Math.abs(deltaX) > targetWidth);
		boolean steadyHand = (Math.abs(deltaX / deltaY) > 2);
		
		if(movedAcross && steadyHand) {
			int crossed;
			
			if(deltaX > 0){
				crossed = 1;
			}else{
				crossed = 0;
			}
			
			// figure out which child view we crossed
			ListView list = (ListView)this.findViewById(R.id.wishList);
			int position = list.pointToPosition((int)downStart.getX(), (int)downStart.getY());
			
			// pass crossed event to any listeners
			for(OnCrossListener listener : listeners) {
				listener.onCross(position, crossed);
			}
			return true;
		}
		
		return false;
	}

	private List<OnCrossListener> listeners = new LinkedList<OnCrossListener>();
	
	public void addOnCrossListener(OnCrossListener listener) {
		listeners.add(listener);
	}
}
