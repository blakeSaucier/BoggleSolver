package io.neilharvey.bogglebuddy;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DatabaseDictionary implements Dictionary {

    private static final String TAG = "DatabaseDictionary";
    private static final String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
    private static final String DATABASE_NAME = "dictionary";
    private static final String FTS_VIRTUAL_TABLE = "FTSdictionary";
    private static final int DATABASE_VERSION = 2;
    private static final HashMap<String, String> columnMap = buildColumnMap();

    private final DictionaryOpenHelper dictionaryOpenHelper;

    public DatabaseDictionary(Context context) {
        dictionaryOpenHelper = new DictionaryOpenHelper(context);
    }

    private static HashMap<String, String> buildColumnMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_WORD, KEY_WORD);
        map.put(BaseColumns._ID, "rowid AS " + BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
        return map;
    }

    @Override
    public boolean IsWord(String word) {
        String selection = KEY_WORD + " = ?";
        return HasResults(word, selection);
    }

    @Override
    public boolean IsPrefix(String prefix) {
        String selection = KEY_WORD + " LIKE ?";
        return HasResults(prefix + "%", selection);
    }

    private boolean HasResults(String word, String selection) {
        String[] selectionArgs = new String[]{word};
        String[] columns = new String[]{KEY_WORD};
        Cursor cursor = query(selection, selectionArgs, columns);

        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);
        builder.setProjectionMap(columnMap);

        Cursor cursor = builder.query(
                dictionaryOpenHelper.getReadableDatabase(),
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private static class DictionaryOpenHelper extends SQLiteOpenHelper {

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " Using fts3 (" + KEY_WORD + ");";
        private final Context helperContext;

        public DictionaryOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(FTS_TABLE_CREATE);
            loadDictionary(db);
        }

        private void loadDictionary(SQLiteDatabase db) {
//            new Thread(new Runnable() {
//                public void run() {
            try {
                loadWords(db);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//                }
//            });
        }

        private void loadWords(SQLiteDatabase db) throws IOException {
            Log.d(TAG, "Loading words...");
            final Resources resources = helperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.wordlist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    long id = addWord(db, line);
                    if (id < 0) {
                        Log.e(TAG, "unable to add words: " + line);
                    }
                }
            } finally {
                reader.close();
            }
            Log.d(TAG, "DONE loading words.");
        }

        private long addWord(SQLiteDatabase db, String word) {
            Log.d(TAG, "Adding word " + word);
            ContentValues values = new ContentValues();
            values.put(KEY_WORD, word);
            return db.insert(FTS_VIRTUAL_TABLE, null, values);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }
}
