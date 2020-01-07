package com.example.kangdong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase sqlDB;
    private DBHelper dbHelper;
    private TextView textView, textView2, textResult;
    private EditText edt_find, edt_text, edt_price;
    private Cursor cursor;


    Button bt_input, bt_delete, bt_change ,bt_serch, bt_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);


        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textResult = (TextView)findViewById(R.id.textResult);

        bt_input = (Button)findViewById(R.id.Bt_input);
        bt_delete = (Button)findViewById(R.id.Bt_delete);
        bt_serch = (Button)findViewById(R.id.Bt_serch);
        bt_check = (Button)findViewById(R.id.Bt_check);
        bt_change = (Button)findViewById(R.id.Bt_change);

        edt_price = (EditText)findViewById(R.id.edt_price);
        edt_text = (EditText)findViewById(R.id.edt_name);
        edt_find = (EditText)findViewById(R.id.edt_find);

        //추가
        bt_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    sqlDB = dbHelper.getWritableDatabase();
                    if(edt_text.getText().toString().length()>0 && edt_price.getText().toString().length()>0) {

                        sqlDB.execSQL("INSERT INTO KTB VALUES('"+edt_text.getText().toString()+"',"
                                +edt_price.getText().toString()+");");

                        Toast.makeText(getApplicationContext(),"입력완료",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"상품명 또는 가격을 입력해 주세요",Toast.LENGTH_SHORT).show();
                    }
                }catch (android.database.sqlite.SQLiteConstraintException e){
                    Toast.makeText(getApplicationContext(),"중복된 상품명이 있습니다.",Toast.LENGTH_SHORT).show();
                }catch (java.lang.NullPointerException e){
                    Toast.makeText(getApplicationContext(),"상품명을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }finally {
                    sqlDB.close();
                }
                bt_check.callOnClick();
            }
        });
        // 수정
        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(edt_text.getText().toString().length()>0 && edt_price.getText().toString().length()>0){
                        sqlDB = dbHelper.getWritableDatabase();
                        sqlDB.execSQL("UPDATE KTB SET kPrice ='"+edt_price.getText().toString()+"' WHERE kName = '"+edt_text.getText().toString()+"';");

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"변경 값을 입력해 주세요",Toast.LENGTH_SHORT).show();
                    }
                }finally {
                    sqlDB.close();
                }
                bt_check.callOnClick();
            }
        });
        //삭제
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sqlDB = dbHelper.getWritableDatabase();
                    sqlDB.execSQL("DELETE FROM KTB WHERE kName ='"+edt_text.getText().toString()+"';");
                    Toast.makeText(getApplicationContext(),"삭제 되었습니다",Toast.LENGTH_SHORT).show();
                }
                finally {
                    sqlDB.close();
                }
                bt_check.callOnClick();
            }
        });
        //조회
        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    sqlDB = dbHelper.getReadableDatabase();
                    cursor = sqlDB.rawQuery("SELECT * FROM KTB ORDER BY kName",null);

                    String name = "상품 이름" + "\r\n" + "------------" + "\r\n";
                    String price = "가격" + "\r\n" + "------------" + "\r\n";

                    while (cursor.moveToNext()){
                        name += cursor.getString(0) + "\r\n";
                        price += cursor.getString(1) + " 원" + "\r\n";
                    }

                    textView.setText(name);
                    textView2.setText(price);
                }
                finally {
                    cursor.close();
                    sqlDB.close();
                }
            }
        });
        //검색
        bt_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    sqlDB = dbHelper.getReadableDatabase();
                    cursor = sqlDB.rawQuery("SELECT * FROM KTB WHERE kName = '"+edt_find.getText().toString()+"';",null);

                    String result = "";

                    while (cursor.moveToNext()) {
                        result = cursor.getString(0) +"     "+ cursor.getString(1) + " 원";
                    }

                    textResult.setText(result);
                }finally {
                    cursor.close();
                    sqlDB.close();
                }
            }
        });

    }

}