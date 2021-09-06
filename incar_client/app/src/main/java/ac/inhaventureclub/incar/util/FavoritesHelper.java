package ac.inhaventureclub.incar.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ac.inhaventureclub.incar.object.FavoriteObject;

public class FavoritesHelper extends SQLiteOpenHelper {

    // 얘는 생성자인데, instance를 제공해준다.
    // instance는 기본적으로
    // java는 method=class 영역이라고 불리는 메모리 공간이 있어. 메모리를 이만큼 잡아두고 여기에 class 내용들을 여기에 넣어놔
    // 그리고 heap이라는 영역에다가 object를 만들어줘.
    // 이 object라는 개념은 class의 정보와 heap에 있는 instance(static하지 않은, method하지 않은 환경).
    // public, static, int ... 이 static으로 들어가는 모든 함수는 class 영역에 저장이 된다.
    // heap에 있는 instance들이 공통적으로 class 부분을 바라봐.
    // object는 instance와 class부분의 영역을 합쳐주는 놈이야. heap영역에 얘가 요청한 부분을 그대로 잡아서 하여간 이것저것 하기위해서 만들어놓는 영역이 heap의 영역.
    // 비유하자면 게임 몬스터들이 instance고 이걸 생성자가 만들어줘.
    // 그래서 생성자 구조는 함수 이름이랑 returen이름이랑 똑같아.
    // 함수명과 return명이 동일시 될 떄가 일케 생기게되는거야.
    // 생성자가 필요한거 보자 ==> 파라미터.
    // SQLightOpenHelper가 필요하니까 저렇게 쓰여진거야.
    // f3누르면 자세히 볼 수 있음. m은 member라는 의미야.

    //context:기계 상태/기계 data값.      name: database file이름.
    // SQLiteDatabase.CursorFactory: singleton pattern은 대표적으로 탐색기. 200개 열어도 파일 변경은 모두 동일하게 적용되잖아. public으로 된 생성자를 private으로 바꿔.
    //                               factory pattern은 instance를 new로 해서 생성(java에게 맡기지)하지 않고
    //              cursor를 생성하는데에 필요한 factory.
    // version: db의 버전. 우리가 정하는거야.  version==1.0이면 onCreate로 떨어져. 근데 그 담이 문젠게 version을 1.1로 update하는 경우 onUpgrade로 떨어져
    // sqlite는 어느정도 크기를 차지할지 모름. 원래는 아예 data공간을 빡 차지하는데, 얘는 그렇지 않고 file database뭐시기 구조를 가짐 ==>name

    public FavoritesHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE  Favorites  (" +

                        "F_INDEX INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "WHEN_GO TEXT," +
                        "DEPARTURE_IDX INTEGER," +
                        "DEPARTURE_DETAIL TEXT," +
                        "DEPARTURE_X TEXT," +
                        "DEPARTURE_Y TEXT," +
                        "ARRIVE_IDX INTEGER," +
                        "ARRIVE_DETAIL TEXT," +
                        "ARRIVE_X TEXT," +
                        "ARRIVE_Y TEXT," +
                        "IS_GUEST INTEGER," +
                        "PRICE INTEGER," +
                        "CAR_TYPE TEXT," +
                        "EXPLANATION TEXT," +
                        "FLAG INTEGER NOT NULL," +
                        "USER_ID TEXT NOT NULL," +
                        "REG_TIME TEXT NOT NULL," +
                        "REMARK TEXT" +
                        ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String favorites = "favorites";

    public ArrayList<FavoriteObject> selectAll(){
        Cursor cursor = getReadableDatabase().query(favorites, null, null,null,null,null,null);
        // WHERE column=?  // asc: desc =오름차순: 내림차순 //

        ArrayList<FavoriteObject> favoriteObjectsList = new ArrayList<>();
        while(cursor.moveToNext()){ // data의 마지막 줄을 읽을 때 까지 계속 내린다.
            // 항상 맨 처음에 column명에 cursur가 놓여있고 그 다음 줄에 실제 data가 있어. 그니까 그 다음 줄로 내려가야 data를 가져올 수 있겠지.

            FavoriteObject favoriteObject = new FavoriteObject();

            favoriteObject.F_INDEX = cursor.getInt(cursor.getColumnIndex("F_INDEX"));
            favoriteObject.WHEN_GO = cursor.getString(cursor.getColumnIndex("WHEN_GO"));
            favoriteObject.DEPARTURE_IDX = cursor.getInt(cursor.getColumnIndex("DEPARTURE_IDX"));
            favoriteObject.DEPARTURE_DETAIL = cursor.getString(cursor.getColumnIndex("DEPARTURE_DETAIL"));
            favoriteObject.DEPARTURE_X = cursor.getString(cursor.getColumnIndex("DEPARTURE_X"));
            favoriteObject.DEPARTURE_Y = cursor.getString(cursor.getColumnIndex("DEPARTURE_Y"));
            favoriteObject.ARRIVE_IDX = cursor.getInt(cursor.getColumnIndex("ARRIVE_IDX"));
            favoriteObject.ARRIVE_DETAIL = cursor.getString(cursor.getColumnIndex("ARRIVE_DETAIL"));
            favoriteObject.ARRIVE_X = cursor.getString(cursor.getColumnIndex("ARRIVE_X"));
            favoriteObject.ARRIVE_Y = cursor.getString(cursor.getColumnIndex("ARRIVE_Y"));
            favoriteObject.IS_GUEST = cursor.getInt(cursor.getColumnIndex("IS_GUEST"));
            favoriteObject.PRICE = cursor.getInt(cursor.getColumnIndex("PRICE"));
            favoriteObject.CAR_TYPE = cursor.getString(cursor.getColumnIndex("CAR_TYPE"));
            favoriteObject.EXPLANATION = cursor.getString(cursor.getColumnIndex("EXPLANATION"));
            favoriteObject.FLAG = cursor.getInt(cursor.getColumnIndex("FLAG"));
            favoriteObject.USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
            favoriteObject.REG_TIME=cursor.getString(cursor.getColumnIndex("REG_TIME"));
            favoriteObject.REMARK=cursor.getString(cursor.getColumnIndex("REMARK"));

            favoriteObjectsList.add(favoriteObject);
        }

        return favoriteObjectsList;
    }

    public long insertFavorites(FavoriteObject favoriteObject){

        getWritableDatabase().beginTransaction();

        ContentValues contentValues = new ContentValues();

        // F_INDEX는 자동으로 늘어나게 설정해놓음. (AI)
        contentValues.put("WHEN_GO",favoriteObject.WHEN_GO);
        contentValues.put("DEPARTURE_IDX",favoriteObject.DEPARTURE_IDX);
        contentValues.put("DEPARTURE_DETAIL",favoriteObject.DEPARTURE_DETAIL);
        contentValues.put("DEPARTURE_X",favoriteObject.DEPARTURE_X);
        contentValues.put("DEPARTURE_Y",favoriteObject.DEPARTURE_Y);
        contentValues.put("ARRIVE_IDX",favoriteObject.ARRIVE_IDX);
        contentValues.put("ARRIVE_DETAIL",favoriteObject.ARRIVE_DETAIL);
        contentValues.put("ARRIVE_X",favoriteObject.ARRIVE_X);
        contentValues.put("ARRIVE_Y",favoriteObject.ARRIVE_Y);
        contentValues.put("IS_GUEST",favoriteObject.IS_GUEST);
        contentValues.put("PRICE",favoriteObject.PRICE);
        contentValues.put("CAR_TYPE",favoriteObject.CAR_TYPE);
        contentValues.put("EXPLANATION",favoriteObject.EXPLANATION);
        contentValues.put("FLAG",favoriteObject.FLAG);
        contentValues.put("USER_ID",favoriteObject.USER_ID);
        contentValues.put("REG_TIME",favoriteObject.REG_TIME);
        contentValues.put("REMARK",favoriteObject.REMARK);

        long ret = getWritableDatabase().insert(favorites,null,contentValues); // 얘는 항상 아래꺼의 위에 있어야해.
        // Long 타입으로 바뀐 갯수를 return함.

        getWritableDatabase().setTransactionSuccessful(); // == commit;
        getWritableDatabase().endTransaction(); // 나 이제 점유 안할거야. 끝이야.
        //사실 안드로이드는 자동 commit임. 안써도 되긴 함ㅎㅎ.. 그래도 써야할 때가 있긴해
        // 어느 때냐면, db쓸라고 준비를 다 해놓고 실제로는 user가 확인을 누르기 전까지는 동작하지 않게 한다던가 이럴 때
        // 자동으로 data를 입히지 못하게 하려고 쓰기도 함.

        return ret;
    }

    public long updateFavorites(FavoriteObject favoriteObject){

        ContentValues contentValues = new ContentValues();

        contentValues.put("WHEN_GO",favoriteObject.WHEN_GO);
        contentValues.put("DEPARTURE_IDX",favoriteObject.DEPARTURE_IDX);
        contentValues.put("DEPARTURE_DETAIL",favoriteObject.DEPARTURE_DETAIL);
        contentValues.put("DEPARTURE_X",favoriteObject.DEPARTURE_X);
        contentValues.put("DEPARTURE_Y",favoriteObject.DEPARTURE_Y);
        contentValues.put("ARRIVE_IDX",favoriteObject.ARRIVE_IDX);
        contentValues.put("ARRIVE_DETAIL",favoriteObject.ARRIVE_DETAIL);
        contentValues.put("ARRIVE_X",favoriteObject.ARRIVE_X);
        contentValues.put("ARRIVE_Y",favoriteObject.ARRIVE_Y);
        contentValues.put("IS_GUEST",favoriteObject.IS_GUEST);
        contentValues.put("PRICE",favoriteObject.PRICE);
        contentValues.put("CAR_TYPE",favoriteObject.CAR_TYPE);
        contentValues.put("EXPLANATION",favoriteObject.EXPLANATION);
        contentValues.put("FLAG",favoriteObject.FLAG);
        contentValues.put("USER_ID",favoriteObject.USER_ID);
        contentValues.put("REG_TIME",favoriteObject.REG_TIME);
        contentValues.put("REMARK",favoriteObject.REMARK);

        return getWritableDatabase().update(favorites,contentValues,"F_INDEX=?",new String[] {Integer.toString(favoriteObject.F_INDEX)});

    }

    public long deleteFavorites(int f_index){
        return  getWritableDatabase().delete(favorites,"F_INDEX=?",new String[]{String.valueOf(f_index)});
        // String.valueOf() == Integer.toString()
    }



}
