package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etname;
    private EditText etnum;
    private EditText etemail;
    private Button btnadd;
    private Button btnupd;
    private Button btndel;
    private Button btnsel;
    private TextView tvshow;
    private MyHelper myHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname=findViewById(R.id.nameBox);
        etnum=findViewById(R.id.pnBox);
        etemail=findViewById(R.id.emailBox);
        btnadd=findViewById(R.id.writeBtn);
        btnupd=findViewById(R.id.updateBtn);
        btnsel=findViewById(R.id.readBtn);
        btndel=findViewById(R.id.clearBtn);
        tvshow=findViewById(R.id.tv);
        myHelper=new MyHelper(this);
        btnadd.setOnClickListener(this);
        btnsel.setOnClickListener(this);
        btndel.setOnClickListener(this);
        btnupd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name;
        String number;
        String email;
        SQLiteDatabase db;
        ContentValues values;
        switch (v.getId()){
            case R.id.writeBtn:

                if(etname.length()==0||etnum.length()==0||etemail.length()==0){
                    Toast.makeText(this,"NULL VALUES",Toast.LENGTH_SHORT).show();
                }else{
                    name=etname.getText().toString();
                    number=etnum.getText().toString();
                    email=etemail.getText().toString();
                    db=myHelper.getWritableDatabase();
                    values=new ContentValues();
                    values.put("name",name);
                    values.put("number",number);
                    values.put("email",email);
                    db.insert("info",null,values);
                    Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
                    db.close();
                }

                break;
            case R.id.clearBtn:
                db=myHelper.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
                db.close();
                tvshow.setText("");
                break;
            case R.id.updateBtn:

                if(etname.length()==0||etnum.length()==0||etemail.length()==0){
                    Toast.makeText(this,"NULL VALUES",Toast.LENGTH_SHORT).show();
                }else {
                    name=etname.getText().toString();
                    number=etnum.getText().toString();
                    email=etemail.getText().toString();
                    db = myHelper.getWritableDatabase();
                    values = new ContentValues();
                    values.put("name", name);
                    values.put("email", email);
                    db.update("info", values, "number=?", new String[]{number});
                    db.close();
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.readBtn:
                db=myHelper.getWritableDatabase();
                Cursor cursor =db.query("info",null,null,null,null,null,null);
                if(cursor.getCount()==0){
                    tvshow.setText("*******"+"\n"+"No Records");
                    Toast.makeText(this,"NO DATA",Toast.LENGTH_SHORT).show();
                }else{
                    cursor.moveToFirst();
                    tvshow.setText("name："+cursor.getString(0)+"\n"+"number："+cursor.getString(1)+"\n"+"email："+cursor.getString(2)+"\n");
                }
                while(cursor.moveToNext()){
                    tvshow.append("\n"+"name："+cursor.getString(0)+"\n"+"number："+cursor.getString(1)+"\n"+"email："+cursor.getString(2)+"\n");
                cursor.close();
                db.close();
                }

                break;
        }
    }
}
