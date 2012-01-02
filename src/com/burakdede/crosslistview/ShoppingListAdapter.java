package com.burakdede.crosslistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 
 * @author burak
 * @date 26 Aug 2011
 * 
 * Database adapter for some generic
 * database operations like CREATE,UPDATE,SELECT
 */
public class ShoppingListAdapter {

	private final Context context;
	private SQLiteDatabase db;
	private ShoppingListDbOpenHelper dbHelper;
	
	private static final String DATABASE_NAME = "shopscanner.db";
	private static final String DATABASE_TABLE = "shop_list";
	private static final int DATABASE_VERSION = 1;
	
	public static final String KEY_ID = "_id";
	public static final String PRODUCT_TITLE = "product_title";
	public static final String PRODUCT_PRICE = "product_price";
	public static final String PRODUCT_SOURCE = "product_source";
	public static final String PRODUCT_IMAGE = "product_image";
	public static final String PRODUCT_COMP = "product_completed";
	
	public ShoppingListAdapter(Context context) {
		this.context = context;
		dbHelper = new ShoppingListDbOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void close(){
		db.close();
	}
	
	/**
	 * @throws SQLiteException
	 * 
	 * Create/Open database for
	 * reading/writing
	 */
	public void open() throws SQLiteException{
		db = dbHelper.getWritableDatabase();
		db = dbHelper.getReadableDatabase();
	}
	
	/**
	 * 
	 * @param item
	 * @return KEY_ID
	 */
	public long insertNewItem(ShopListItem item){
		
		ContentValues taskValues = new ContentValues();
		
		taskValues.put(PRODUCT_TITLE, item.getProductTitle());
		taskValues.put(PRODUCT_PRICE, item.getProductPrice());
		taskValues.put(PRODUCT_SOURCE, item.getProductSource());
		taskValues.put(PRODUCT_IMAGE, item.getProductImage());
		taskValues.put(PRODUCT_COMP, item.getProductCompl());
		
		return db.insert(DATABASE_TABLE, null, taskValues);
	}
	
	public boolean updateItemByUniqueId(int key_id, int crossed){
		
		String whereClause = KEY_ID + "=" + key_id;
		
		ContentValues cv = new ContentValues();
		cv.put(PRODUCT_COMP, Integer.valueOf(crossed));
		
		return db.update(DATABASE_TABLE, cv, whereClause, null) > 0;
	}
	
	/**
	 * Get all shopping items from table
	 * @return {@link Cursor}
	 */
	public Cursor getAllItems() {
		return db.query(DATABASE_TABLE, new String [] { KEY_ID, PRODUCT_IMAGE , PRODUCT_TITLE,
				PRODUCT_SOURCE, PRODUCT_PRICE, PRODUCT_COMP }, null, null, null, null, null, null);
	}
	
	
	public boolean deleteItemByUniqueId(int key_id){
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + key_id, null) > 0;
	}
	
	/**
	 * 
	 * @author burak
	 * @date 26 Aug 2011
	 * 
	 * Databaes create and upgrade operations
	 */
	private static class ShoppingListDbOpenHelper extends SQLiteOpenHelper{

		public ShoppingListDbOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
			PRODUCT_TITLE + " text not null ," + PRODUCT_PRICE + " text ,"+ PRODUCT_SOURCE + " text ," + PRODUCT_IMAGE + " text ," + PRODUCT_COMP + " integer );";

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

}
