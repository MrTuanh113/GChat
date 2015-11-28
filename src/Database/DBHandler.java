package Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHandler extends SQLiteOpenHelper{
private static final int DATABASE_VERSION=1;
private static final String DATABASE_NAME="qlreq.db";
private static final String TABLE="tbl_req";
private static final String TABLE1="tbl_friend";
private static final String KEY_ID="id";
private static final String KEY_USER_ID="user_id";
private static final String KEY_USER_NAME="user_name";

	public DBHandler(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_REQUIRED_MANAGER_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " TEXT,"
				+ KEY_USER_NAME + " TEXT" + ")";
		String CREATE_FRIEND_MANAGER_TABLE = "CREATE TABLE " + TABLE1 + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " TEXT,"
				+ KEY_USER_NAME + " TEXT" + ")";
		
//		String CREATE_REQUIRED_MANAGER_TABLE ="CREATE TABLE "+TABLE+"("
//		+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_USER_ID+ " TEXT,"
//		+KEY_USER_NAME +" TEXT"+")";
		db.execSQL(CREATE_REQUIRED_MANAGER_TABLE);
		db.execSQL(CREATE_FRIEND_MANAGER_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
		onCreate(db);
	}
	public void add(User user){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user.getUser_id()); // Contact Name
		values.put(KEY_USER_NAME, user.getUser_name()); // Contact Phone

		// Inserting Row
		db.insert(TABLE, null, values);
		db.close(); // Closing database connection
		Log.d("kakakakakak:  ", user.getUser_name());
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(KEY_USER_ID, user.getUser_id());
//		values.put(KEY_USER_NAME, user.getUser_name());
//		db.insert(TABLE, null, values);
//		db.close();
//		 Log.d("kakakakakak:  ", user.getUser_name());
		
	}
	public void addFriend(User user){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user.getUser_id()); // Contact Name
		values.put(KEY_USER_NAME, user.getUser_name()); // Contact Phone

		// Inserting Row
		db.insert(TABLE1, null, values);
		db.close(); // Closing database connection
		Log.d("kakakakakak:  ", user.getUser_name());

		
	}
	// Truy vấn 1 yêu cầu người dùng theo id
	public User getUser(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE, new String[] {KEY_ID,KEY_USER_ID,KEY_USER_NAME}, KEY_ID+"=?",new String[]{String.valueOf(id)} , null, null, null);
		if(cursor != null)
			cursor.moveToFirst();
		
		User user = new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
		cursor.close();
	return user;
	
	
	}
	public User getUserbyPos(int pos){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE, null, null, null, null, null, null);
		c.moveToFirst();
		for(int i=0;i<pos;i++){
			c.moveToNext();
			
		}
		
		User user = new User(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2));
		c.close();
		return user;
	}
	//Truy vấn 1 bạn bè
	public User getUserFriend(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.query(TABLE1, new String[] {KEY_ID,KEY_USER_ID,KEY_USER_NAME}, KEY_ID+"=?",new String[]{String.valueOf(id)} , null, null, null);
		if(cursor != null)
			cursor.moveToFirst();
		
		User user = new User(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
		cursor.close();
	return user;
	
	
	}
	public User getFriendbyPos(int pos){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.query(TABLE1, null, null, null, null, null, null);
		c.moveToFirst();
		for(int i=0;i<pos;i++){
			c.moveToNext();
			
		}
		
		User user = new User(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2));
		c.close();
		return user;
	}
	
	
	
	
	
	
	//Truy vấn tấ cả danh sách yêu cầu kết bạn
	public List<User> getAllRequired(){
		List<User> user_list = new ArrayList<User>();
		String selectQuery = "SELECT * FROM "+TABLE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			do{
				User user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setUser_id(cursor.getString(1));
				user.setUser_name(cursor.getString(2));
				
				user_list.add(user);
			}while(cursor.moveToNext());
			
				
			}
		cursor.close();
		return user_list;
		}
	//Truy vấn tất cả bạn bè
	public List<User> getAllFriend(){
		List<User> user_list = new ArrayList<User>();
		String selectQuery = "SELECT * FROM "+TABLE1;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()){
			do{
				User user = new User();
				user.setId(Integer.parseInt(cursor.getString(0)));
				user.setUser_id(cursor.getString(1));
				user.setUser_name(cursor.getString(2));
				
				user_list.add(user);
			}while(cursor.moveToNext());
			
				
			}
		cursor.close();
		
		return user_list;
		}
	//Đếm số lượng kết bạn 
	
		public int getCount(){
			String countQuery = "SELECT * FROM "+TABLE;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			
			int a =cursor.getCount();
			cursor.close();
			return a;
			
		}
		//Truy vấn tất cả bạn bè
		public int getCountFriend(){
			String countQuery = "SELECT * FROM "+TABLE1;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(countQuery, null);
			int a =cursor.getCount();
			cursor.close();
			return a;
			
		}
		// Sửa thông tin kết bạn
		public int updateUser_Required(User user){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_USER_ID, user.getUser_id());
			values.put(KEY_USER_NAME, user.getUser_name());
			return db.update(TABLE, values, KEY_ID+"=?", new String[]{String.valueOf(user.getId())});
			
			
		}
		// Sửa thông tin bạn bè
		public int updateUser_Friend(User user){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_USER_ID, user.getUser_id());
			values.put(KEY_USER_NAME, user.getUser_name());
			return db.update(TABLE1, values, KEY_ID+"=?", new String[]{String.valueOf(user.getId())});
			
			
		}
		//Xóa yêu cầu
		public void delete(User user){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE, KEY_ID+" =? ", new String[]{String.valueOf(user.getId())});
			db.close();
		}
		public void delete(String id){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE, KEY_ID+" =? ", new String[]{id});
			db.close();
		}
		// Xóa bạn bè
		public void deleteFriend(User user){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE1, KEY_ID+" =? ", new String[]{String.valueOf(user.getId())});
			db.close();
		}
	}
	


