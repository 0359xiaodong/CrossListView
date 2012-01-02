package com.burakdede.crosslistview;

import android.database.Cursor;
import android.graphics.Paint;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class CrossBinder implements ViewBinder{

	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		
		switch(view.getId()) {
			//cross the title textview with Paint flag
			case R.id.title:
				
				TextView title = (TextView)view.findViewById(R.id.title);
				
				int crossed = Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListAdapter.PRODUCT_COMP)));
				
				if(crossed == 1) {
					title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
				}
				return true;
		}
		return false;
	}
}
