package com.example.studyapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JoinConnect extends AsyncTask<String, Void, String> {
    String sendMsg, recieveMsg = "1";
    String type;

    public JoinConnect(){

    }

    public JoinConnect(String type){
        this.type = type;
    }



    @Override
    protected String doInBackground(String... strings) {
        if(type == "join"){
            try{
                String str;

                // url설정
                URL url = new URL("http://192.168.55.89:8080/TestJSP/Join.jsp");
                // 설정한 url을 통해 http통신 연결을 한다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                // 전송할 데이터 GET방식으로 작성
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1] + "&name=" + strings[2] + "&phone=" + strings[3];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while((str = reader.readLine()) != null){
                        buffer.append(str);
                    }
                    recieveMsg = buffer.toString();
                }else{
                    //통신 실패
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == "login"){ // 로그인 시
            try{
                String str;

                // url설정
                URL url = new URL("http://192.168.55.89:8080/TestJSP/Login.jsp");
                // 설정한 url을 통해 http통신 연결을 한다.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                // 전송할 데이터 GET방식으로 작성
                sendMsg = "id=" + strings[0] + "&pw=" + strings[1];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while((str = reader.readLine()) != null){
                        buffer.append(str);
                    }
                    recieveMsg = buffer.toString();
                }else{
                    //통신 실패
                    Log.e("Error", "Connection Failed");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recieveMsg;
    }
}

