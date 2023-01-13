package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText[] texts = new EditText[2];
    Button login_btn, join_btn;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texts[0] = findViewById(R.id.id);
        texts[1] = findViewById(R.id.pw);
        login_btn = findViewById(R.id.login_btn);
        join_btn = findViewById(R.id.join_btn);
        msg = findViewById(R.id.login_msg);

        int[] checkbit = {1, 1};

        login_btn.setOnClickListener(new View.OnClickListener() {
            String s_texts[] = new String[texts.length];
            @Override
            public void onClick(View view) {
                msg.setText("");
                boolean check = true;
               s_texts[0] = texts[0].getText().toString();
               s_texts[1] = texts[1].getText().toString();
               for(int i = 0; i < texts.length; i++){
                   if(s_texts[i].length() <= 0){
                       checkbit[i] = 0;
                       check = false;
                   }
               }
               if(check == false){
                   for(int i = 0; i < texts.length; i++){
                       if(checkbit[i] == 0){
                           msg.append(texts[i].getHint() + ", ");
                       }

                   }msg.append("를 입력해주세요");
               }else{
                   // 로그인 시도
                   String l_check = "2";
                   String id_t = texts[0].getText().toString();
                   String pwd = texts[1].getText().toString();
                    JoinConnect task = new JoinConnect("login");
                   try {
                       l_check = task.execute(id_t, pwd).get().trim();
                       Log.i("l_check.length : ", String.valueOf(l_check.length()));
                       boolean ch = l_check.equals("true");
                       Log.i("equals check", String.valueOf(ch));
                       if(l_check.equals("true") ){
                           Log.i("equals check", "111");
                           Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                           startActivity(intent2);
                       }else{
                           Log.i("equals check", "222");
                           AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                           ad.setIcon(R.mipmap.ic_launcher);
                           ad.setTitle("로그인");
                           ad.setMessage("아이디 혹은 비밀번호가 틀렸습니다.");
                           ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   texts[0].setText("");
                                   texts[1].setText("");
                               }
                           });
                           ad.show();
                       }
                   } catch (ExecutionException e) {
                       e.printStackTrace();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                    Log.i("l_check", l_check);
               }

            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

    }
}
