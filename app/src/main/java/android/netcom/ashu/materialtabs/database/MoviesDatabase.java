package android.netcom.ashu.materialtabs.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.netcom.ashu.materialtabs.POJO.Movies;
import android.netcom.ashu.materialtabs.logging.L;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by KamaL on 30-08-2016.
 */
public class MoviesDatabase {

    private MoviesHelper mHelper;
    private SQLiteDatabase mDataBase;
    Context context;
    public MoviesDatabase(Context context){
        mHelper = new MoviesHelper(context);
        mDataBase = mHelper.getWritableDatabase();
    }

    public void insertMovies(ArrayList<Movies> movieList, boolean clearPrevious){

        if(clearPrevious){
            deleteAll();
        }
        String sql = "INSERT INTO " + MoviesHelper.TABLE_BOX_OFFICE + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = mDataBase.compileStatement(sql);
        mDataBase.beginTransaction();

        for(int i=0; i< movieList.size(); i++){
            Movies currentMovies = movieList.get(i);
            statement.clearBindings();
            statement.bindString(2, currentMovies.getTitle());
            statement.bindLong(3, currentMovies.getReleaseDateTheater()==null? -1 : currentMovies.getReleaseDateTheater().getTime());
            statement.bindLong(4, currentMovies.getAudience_score());
            statement.bindString(5, currentMovies.getSynopsis());
            statement.bindString(6, currentMovies.getThumbnail());
            statement.bindString(7, currentMovies.getUrlSelf());
            statement.bindString(8, currentMovies.getUrlCast());
            statement.bindString(9, currentMovies.getUrlReviews());
            statement.bindString(10, currentMovies.getUrlSimilar());

            statement.execute();
        }
        L.p("inserting entries " + movieList.size() + new Date(System.currentTimeMillis()));
        mDataBase.setTransactionSuccessful();
        mDataBase.endTransaction();
    }

    public ArrayList<Movies> readMovies(){
        ArrayList<Movies> movieList = new ArrayList<>();
        String[] columns = {MoviesHelper.COLUMN_UID,
                MoviesHelper.COLUMN_TITLE,
                MoviesHelper.COLUMN_RELEASE_DATE,
                MoviesHelper.COLUMN_AUDIENCE_SCORE,
                MoviesHelper.COLUMN_SYNOPSIS,
                MoviesHelper.COLUMN_URL_THUMBNAIL,
                MoviesHelper.COLUMN_URL_SELF,
                MoviesHelper.COLUMN_URL_CAST,
                MoviesHelper.COLUMN_URL_REVIEWS,
                MoviesHelper.COLUMN_URL_SIMILAR
        };

        Cursor cursor = mDataBase.query(MoviesHelper.TABLE_BOX_OFFICE, columns, null, null,null, null,null);
        if(cursor != null && cursor.moveToFirst()){
            L.p("loading entries : " + cursor.getCount() + new Date(System.currentTimeMillis()));
            do{
                Movies movies = new Movies();
                movies.setTitle(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_TITLE)));
                long movieReleaseDateTimeMills = cursor.getInt(cursor.getColumnIndex(MoviesHelper.COLUMN_RELEASE_DATE));
                movies.setReleaseDateTheater(movieReleaseDateTimeMills != -1 ? new Date(movieReleaseDateTimeMills) : null);
                movies.setAudience_score(cursor.getInt(cursor.getColumnIndex(MoviesHelper.COLUMN_AUDIENCE_SCORE)));
                movies.setSynopsis(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_SYNOPSIS)));
                movies.setThumbnail(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_URL_THUMBNAIL)));
                movies.setUrlSelf(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_URL_SELF)));
                movies.setUrlCast(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_URL_CAST)));
                movies.setUrlReviews(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_URL_REVIEWS)));
                movies.setUrlSimilar(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLUMN_URL_SIMILAR)));

                movieList.add(movies);
            }while (cursor.moveToNext());
        }

        return movieList;
    }

    public void deleteAll(){
        mDataBase.delete(MoviesHelper.TABLE_BOX_OFFICE, null, null);
    }
    private static class MoviesHelper extends SQLiteOpenHelper{

        public static final String TABLE_BOX_OFFICE = "movies_box_office";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_AUDIENCE_SCORE = "audience_score";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_URL_THUMBNAIL = "url_thumbnail";
        public static final String COLUMN_URL_SELF = "url_self";
        public static final String COLUMN_URL_CAST = "url_cast";
        public static final String COLUMN_URL_REVIEWS = "url_reviews";
        public static final String COLUMN_URL_SIMILAR = "url_similar";
        private static final String CREATE_TABLE_BOX_OFFICE = "CREATE TABLE " + TABLE_BOX_OFFICE + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_RELEASE_DATE + " INTEGER,"+
                COLUMN_AUDIENCE_SCORE + " INTEGER," +
                COLUMN_SYNOPSIS + " TEXT," +
                COLUMN_URL_THUMBNAIL + " TEXT," +
                COLUMN_URL_SELF + " TEXT," +
                COLUMN_URL_CAST + " TEXT," +
                COLUMN_URL_REVIEWS + " TEXT," +
                COLUMN_URL_SIMILAR + " TEXT" +
                ");";

        private static final String DB_NAME = "movies_db";
        private static final int DB_VERSION = 1;
        private Context context;

        public MoviesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_BOX_OFFICE);
                L.m(context, "create table box office executed");
            } catch (SQLException e) {
                L.t(context, e+"");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL("DROP TABLE " + TABLE_BOX_OFFICE + " IF EXISTS;");
                onCreate(db);
                L.m(context, "upgrade table boxoffice executed");
            } catch (SQLException e) {
                L.t(context, e+"");
            }
        }
    }
}
