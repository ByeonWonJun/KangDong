package com.example.kangdong;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list;
    private ListView listView;
    public EditText edt_find, editText, edt_price;
    private SearchAdapter searchAdapter;
    private ArrayList<String> arrayList;
    DBHelper dbHelper;
    SQLiteDatabase sqlDB;


    Button bt_input, bt_delete, bt_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        bt_input = (Button)findViewById(R.id.Bt_input);
        bt_delete = (Button)findViewById(R.id.Bt_delete);
        bt_change = (Button)findViewById(R.id.Bt_change);

        bt_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length()>0 && edt_price.getText().toString().length()>0) {

                    sqlDB = dbHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO groupTB VALUES('"+ editText.getText().toString()+ "' ,"+edt_price.getText().toString()+" );");

                    sqlDB.close();

                    Toast.makeText(getApplicationContext(),"입력됨", Toast.LENGTH_SHORT).show();


                }
            }
        });

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlDB = dbHelper.getReadableDatabase();

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTB;",null);

                String name ="";
                String price ="";

                while (cursor.moveToNext()){
                    arrayList.add(cursor.getString(0)+"        " + cursor.getString(1)+ "원");
                    //arrayList.add(cursor.getString(1));


                }

                //arrayList.add(name + "     :     " + price + "  원");
                arrayAdapter.notifyDataSetChanged();

                sqlDB.close();
                cursor.close();
            }
        });


        edt_price = (EditText)findViewById(R.id.edt_price);
        editText = (EditText)findViewById(R.id.edt_name);
        edt_find = (EditText)findViewById(R.id.edt_find);

        listView = (ListView)findViewById(R.id.listView);

        list = new ArrayList<String>();

        arrayList = new ArrayList<String>();
        arrayList.addAll(list);

        searchAdapter = new SearchAdapter(list,this);

        listView.setAdapter(searchAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = edt_find.getText().toString();
                search(text);
            }
        });


    }



    public void search(String charText){
        list.clear();

        if(charText.length() == 0){
            list.addAll(arrayList);
        }
        else
        {
            for(int i = 0; i<arrayList.size(); i++)
            {
                if(arrayList.get(i).toLowerCase().contains(charText))
                {
                    list.add(arrayList.get(i));
                }
            }
        }
        searchAdapter.notifyDataSetChanged();
    }
}
