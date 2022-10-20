package com.nah.adv_adr_prj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.nah.adv_adr_prj.services.CheckLoginServices;
import com.nah.adv_adr_prj.sqlitehelper.UserDao;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAct extends AppCompatActivity {

    IntentFilter intentFilter;
    CallbackManager callbackManager;
    LoginButton btLoginFb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout textInput = findViewById(R.id.textTenTaiKhoan);
        TextInputLayout textInput2 = findViewById(R.id.textMatKhau);
        MaterialButton btnLogin = findViewById(R.id.btDangNhap);

        btLoginFb = findViewById(R.id.btLoginFb);

        callbackManager = CallbackManager.Factory.create();


        //check người dùng đã đăng nhập trước đó hay chưa?
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            getUserProfile(accessToken);
            Toast.makeText(this, "Đã đăng nhập", Toast.LENGTH_SHORT).show();
        }

        //nút đăng nhập/logout
        btLoginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginAct.this, "Hủy đăng nhập", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginAct.this, "Lỗi trong quá trình đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });




        UserDao dao = new UserDao(this);

        intentFilter = new IntentFilter();
        intentFilter.addAction("checkLogin");
        intentFilter.addAction("...");



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textInput.getEditText().getText().toString();
                String password = textInput2.getEditText().getText().toString();

                // service required
                Intent i = new Intent(LoginAct.this, CheckLoginServices.class);
                Bundle bd = new Bundle();
                bd.putString("username", username);
                bd.putString("password", password);
                i.putExtras(bd);
                startService(i);

//                if (dao.checkLogin(username, password)) {
//                    // lưu sharepreferences
//                    SharedPreferences sharedPreferences = getSharedPreferences("Info", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username",username);
//                    editor.commit();
//                    startActivity(new Intent(LoginAct.this,MainActivity.class));
//                } else {
//                    Toast.makeText(LoginAct.this, "Username or Password invalid", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }


    public BroadcastReceiver myBR = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            switch (i.getAction()){
                case "checkLogin":
                    Bundle bd = i.getExtras();
                    boolean check = bd.getBoolean("check");
                    if (check == true) {
                        startActivity(new Intent(LoginAct.this, MainActivity.class));
                    } else {
                        Toast.makeText(context, "Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBR, intentFilter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void getUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("id");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Toast.makeText(LoginAct.this, name + " - " + email + " - " + image, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

}