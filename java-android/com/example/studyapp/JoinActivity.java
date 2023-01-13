package com.example.studyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity {

    EditText id, pw, pw_check, name, phone;
    Button id_Check_Btn, join_Btn, cancel_Btn;
    TextView id_Check_Text, pw_Check_Text, msg;

    // id, pw_check, name, phone
    int[] check = {0, 0, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        pw_check = (EditText) findViewById(R.id.pw_check);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        id_Check_Btn = (Button) findViewById(R.id.id_Check_Btn);
        join_Btn = (Button) findViewById(R.id.join_Btn);
        cancel_Btn = (Button) findViewById(R.id.cancel_Btn);

        id_Check_Text = (TextView) findViewById(R.id.id_Check_Text);
        pw_Check_Text = (TextView) findViewById(R.id.pw_check_text);
        msg = (TextView) findViewById(R.id.msg);

        EditText[] texts = {id, pw_check, name, phone};

        id_Check_Btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(id.getText().length() > 1) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(JoinActivity.this);
                    ad.setIcon(R.mipmap.ic_launcher);
                    ad.setTitle("아이디 중복확인");
                    ad.setMessage("사용가능한 아이디입니다.");

                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            id_Check_Text.setText("사용가능한 ID입니다.");
                            id_Check_Text.setTextColor(Color.parseColor("#1A47CC"));
                            check[0] = 1;
                        }
                    });

                    ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                }else{
                    id_Check_Text.setText("ID를 입력해야 합니다.");
                }
            }
        });

        pw_check.addTextChangedListener(new TextWatcher() {
            String pw_text, pw_check_txt;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 텍스트가 변할때마다
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pw_text = pw.getText().toString();
                pw_check_txt = pw_check.getText().toString();
                if(pw_text.equals(pw_check_txt)){
                    pw_Check_Text.setTextColor(Color.parseColor("#1A47CC"));
                    pw_Check_Text.setText("PASSWORD가 일치합니다.");
                    check[1] = 1;
                }else{
                    pw_Check_Text.setTextColor(Color.parseColor("#D30D0D"));
                    pw_Check_Text.setText("PASSWORD가 일치하지 않습니다.");
                }
            }

            // 텍스트가 변한 후에
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        join_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkbit = true;
                msg.setText("");
                for(int i = 0; i < check.length; i++){
                    if(check[i] == 0) {
                        checkbit = false;
                        msg.append(texts[i].getHint().toString() + ",  ");
                    }
                }
                if(checkbit == false)
                    msg.append("칸을 입력해주세요");
                else if(checkbit == true){
                   // 회원가입 성공
                    try {
                        String result;
                        String id_s = id.getText().toString();
                        String pw_s = pw_check.getText().toString();
                        String name_s = name.getText().toString();
                        String phone_s = phone.getText().toString();

                        JoinConnect task = new JoinConnect("join");

                        result = task.execute(id_s, pw_s, name_s, phone_s).get();

                        msg.setText(result);
                    } catch(Exception e){
                        Log.i("DBtest", ".....ERROR.....!");
                    }
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int len = name.getText().length();
                if(len >= 1){
                    check[2] = 1;
                }else if(len == 0){
                    check[2] = 0;
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int len = phone.getText().length();
                if(len >= 1){
                    check[3] = 1;
                }else if(len == 0){
                    check[3] = 0;
                }
            }
        });

        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}