package startup.com.chatmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dishank on 4/23/2016.
 */
public class ChatDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "main";
    private static final String TABLE_CHAT = "Chat";
    //private static final String KEY_ID = "_id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final int DATABASE_VERSION=1;

    public ChatDBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_CHAT + "("
                 + NAME + " TEXT," + EMAIL + " TEXT" + ")";
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAT);

        onCreate(db);
    }

    public void addContact(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, userModel.getName()); // Contact Name
        values.put(EMAIL, userModel.getEmail()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CHAT, null, values);
        db.close(); // Closing database connection
    }

    /*public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHAT, new String[] {
                        NAME, EMAIL }, null,
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }*/

    public List<UserModel> getAllChats() {
        List<UserModel> chatList = new ArrayList<UserModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHAT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel chat = new UserModel();
                chat.setName(cursor.getString(0));
                chat.setEmail(cursor.getString(1));
                // Adding contact to list
                chatList.add(chat);
            } while (cursor.moveToNext());
        }

        // return contact list
        return chatList;
    }

    public int updateChat(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, userModel.getName());
        values.put(EMAIL, userModel.getEmail());


        // updating row
        return db.update(TABLE_CHAT, values, null,
                null);
    }

    // Deleting single contact
    public void deleteChat(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAT, null,
                null);
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CHAT);
        db.close();
    }

}
