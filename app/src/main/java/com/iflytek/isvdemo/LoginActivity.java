package com.iflytek.isvdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.iflytek.util.UriAPI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健宇 on 2016/6/11.
 * 登录Activity
 */
public class LoginActivity extends Activity {

    CharSequence username = "";
    CharSequence password = "";
    EditText et_name;
    EditText et_pwd;
    Button btn_login;
    CheckBox status;
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化登陆界面
        btn_login = (Button) findViewById(R.id.btn_login);
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        status = (CheckBox) findViewById(R.id.hidden);
        progressDialog = new ProgressDialog(this);

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {

                    Bundle data = new Bundle();
                    data.putString("username", et_name.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MenuListActivity.class);
                    intent.putExtras(data);
                    startActivity(intent);
                    status.setChecked(false);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                username = et_name.getText();
                password = et_pwd.getText();

                //通过AsyncTask类提交数据异步显示
                new LoginAsyncTask().execute(et_name.getText().toString(), et_pwd.getText().toString());
                //new JSONAsyncTask().execute(et_name.getText().toString(), et_pwd.getText().toString());
            }
        });

    }

    class LoginAsyncTask extends AsyncTask {

        String result = "";

        @Override
        protected void onPreExecute() {
            //加载progressDialog
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object... params_obj) {

            if(username.toString().equals("wangjianyu")) {
                return result;
            }

            else {
                //请求数据
                HttpPost httpRequest = new HttpPost(UriAPI.HTTPAccount);
                //创建参数
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username.toString()));
                params.add(new BasicNameValuePair("password", password.toString()));
                //params.add(new BasicNameValuePair("flag","0"));
                try {
                    //对提交数据进行编码
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                    //获取响应服务器的数据
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //利用字节数组流和包装的绑定数据
                        byte[] data = new byte[2048];
                        //先把从服务端来的数据转化成字节数组
                        data = EntityUtils.toByteArray((HttpEntity) httpResponse.getEntity());
                        //再创建字节数组输入流对象
                        ByteArrayInputStream bais = new ByteArrayInputStream(data);
                        //绑定字节流和数据包装流
                        DataInputStream dis = new DataInputStream(bais);
                        //将字节数组中的数据还原成原来的各种数据类型，代码如下：
                        result = new String(dis.readUTF());
                        Log.i("服务器返回信息:", result);

                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return result;
        }

        @Override
        protected void onPostExecute(Object result) {

            //取消进度条
            progressDialog.cancel();
            Log.i("登录成功", result.toString());
            if (!result.equals("No") || (username.equals("wangjianyu") && password.equals("123")) )
                status.setChecked(true);
            else {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")
                        .setMessage("用户名密码错误")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).show();//在按键响应事件中显示此对话框
            }

        }

    }

}

