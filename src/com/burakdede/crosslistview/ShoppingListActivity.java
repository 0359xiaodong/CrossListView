package com.burakdede.crosslistview;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ShoppingListActivity extends Activity implements OnCrossListener{
	
	private SimpleCursorAdapter cursorAdapter;
	private ShoppingListAdapter dbAdapter;
	private Cursor cursor;
	private ListView shoppingList;
	private CrossView crossView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_list);
		
		crossView = (CrossView) findViewById(R.id.crossview);
		shoppingList = (ListView) findViewById(R.id.wishList);
		shoppingList.setEmptyView(findViewById(R.layout.empty_loading));
		crossView.addOnCrossListener(this);
        
        Toast.makeText(this, "Just swipe left to right to cross of item, swipe right to left for reverse operation", Toast.LENGTH_LONG).show();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	//open connection and get all the items from database to list
    	dbAdapter = new ShoppingListAdapter(this);        
        dbAdapter.open();
    	
        populateDummyDatabaseWithDummyData();
        
        cursor = dbAdapter.getAllItems();
        cursor.moveToFirst();
        
    	int[] to = new int[] { R.id.title, R.id.source, R.id.price, R.id.completed};
        String[] columns = new String [] {ShoppingListAdapter.PRODUCT_TITLE,
        		ShoppingListAdapter.PRODUCT_SOURCE ,ShoppingListAdapter.PRODUCT_PRICE, ShoppingListAdapter.PRODUCT_COMP};
        
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.shopping_list_item, cursor, columns, to);
        cursorAdapter.setViewBinder(new CrossBinder());
        shoppingList.setAdapter(cursorAdapter);
    }

	private void populateDummyDatabaseWithDummyData() {
		
		// just dummy data pushing into database
		for (int i = 0; i < 10; i++) {
			dbAdapter.insertNewItem(new ShopListItem("Title ", "Product Price ",
					"Source", "", 0));
		}
	}

	@Override
	public void onCross(int position, int crossed) {
		
		int id;
		int current;
		
		synchronized(cursor) {
			cursor.moveToPosition((int)position);
			id = Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListAdapter.KEY_ID)));
			current = Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ShoppingListAdapter.PRODUCT_COMP)));
		}
		
		// same value coming from database its right just go on
		if(current == crossed) return;

		// change database state of item
		dbAdapter.updateItemByUniqueId(id, crossed);
		cursor.requery();

		// and refresh the child view for any changes
		int viewIndex = position - shoppingList.getFirstVisiblePosition();
		View child = shoppingList.getChildAt(viewIndex);
		if(child != null) child.invalidate();
	}
	
	@Override
	protected void onStop() {
		// clean up all the mess
		super.onStop();
        cursor.close();
        dbAdapter.close();
	}
}