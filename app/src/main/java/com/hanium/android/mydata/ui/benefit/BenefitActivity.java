package com.hanium.android.mydata.ui.benefit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hanium.android.mydata.R;

import java.util.ArrayList;

public class BenefitActivity extends AppCompatActivity {

    ListView lvContacts = null;
//    ContactDBHelper helper;

    ArrayList<BenefitModel> data = new ArrayList<>();

    Cursor cursor;
    //	SimpleCursorAdapter adapter;
    BenefitAdapter adapter;
    final static int UPDATE_ACTIVITY_CODE = 200;
    final static String TAG = "BenefitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_benefit_list);
        lvContacts = (ListView)findViewById(R.id.benefitListView);

        //임시 데이터 만들기
        data.add(new BenefitModel("카페", "스타벅스", "적립"));
        data.add(new BenefitModel("카페", "스타벅스2", "적립"));
        data.add(new BenefitModel("카페", "스타벅스3", "적립"));
        data.add(new BenefitModel("카페", "스타벅스4", "적립"));
        data.add(new BenefitModel("카페", "스타벅스5", "적립"));
        data.add(new BenefitModel("카페", "스타벅스6", "적립"));

        BenefitAdapterSample test = new BenefitAdapterSample(data);

        lvContacts.setAdapter(test);


        Log.d(TAG, "in BenefitActivity");

        // mycursor
//        adapter = new BenefitAdapter(this, R.layout.adapter_benefit, null);
//        lvContacts.setAdapter(adapter);

//        		리스트 뷰 클릭 처리
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Go to Detail Activity
                Intent i = new Intent(BenefitActivity.this, BenefitDetailActivity.class);
                // Send the position number to Detail Activity too.
                i.putExtra("position", position);
                // Run the process
                startActivity(i);
            }
        });
        /*
//        		리스트 뷰 클릭 처리

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SQLiteDatabase db = helper.getReadableDatabase();
                cursor = db.rawQuery("select * from contact_table where _id=" + id, null);
                ContactDto dto = new ContactDto();
                while(cursor.moveToNext()){
                    dto.setId(cursor.getLong(cursor.getColumnIndex(ContactDBHelper.COL_ID)));
                    dto.setSong(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_SONG)));
                    dto.setSinger(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_SINGER)));
                    dto.setGenre(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_GENRE)));
                    dto.setDate(cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_DATE)));
                }
                intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
                intent.putExtra("dto", dto);
                startActivityForResult(intent, UPDATE_ACTIVITY_CODE);
            }
        });

//		리스트 뷰 롱클릭 처리
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String song = cursor.getString(cursor.getColumnIndex(ContactDBHelper.COL_SONG));
//                AlertDialog.Builder builder = new AlertDialog.Builder(AllContactsActivity.this);
//
//                builder.setTitle("노래 삭제")
//                        .setMessage(song + "를 삭제하시겠습니까?")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                SQLiteDatabase db = helper.getWritableDatabase();
//                                String whereClause = "_id=?";
//                                String[] whereArgs = new String[] {Long.toString(id)};
//                                db.delete(ContactDBHelper.TABLE_NAME, whereClause, whereArgs);
//
//                                cursor = db.rawQuery("select * from contact_table", null);
//                                adapter.changeCursor(cursor);
//                            }
//                        })
//                        .setNegativeButton("취소", null)
//                        .show();

                return true;
            }
        });
*/

    }
/*
    @Override
    protected void onResume() {
        super.onResume();
//        DB에서 데이터를 읽어와 Adapter에 설정
//        SQLiteDatabase db = helper.getReadableDatabase();
//        cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);
//
//        adapter.changeCursor(cursor);
//        helper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }

*/
}
