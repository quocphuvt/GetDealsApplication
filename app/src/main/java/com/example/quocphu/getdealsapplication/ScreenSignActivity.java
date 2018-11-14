package com.example.quocphu.getdealsapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.net.URL;

public class ScreenSignActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInButton btn_google;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView iv;
    private TextView tv_test;
    ProfilePictureView profilePictureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_sign);
        iv = findViewById(R.id.iv_logo);
        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { //Hàm trả về kết quả
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginResult", "facebook:onSuccess:" + loginResult.getAccessToken().getToken());
                handleFacebookAccessToken(loginResult.getAccessToken());
                // call this when login success
                String userID = loginResult.getAccessToken().getUserId();
//                String imgUrl = "https://graph.facebook.com/" + userID + "/picture?type=large";
//                Picasso.with(getApplicationContext()).load(imgUrl).into(iv);  Lấy ảnh từ profile thông qua thư viện Picasso
                xulysaukhilogin(loginResult);


            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("ErrorFacebook", "facebook:onError", error);
                // ...
            }
        });
    }
    //Hàm kiểm tra xem có user nào đang đăng nhập lúc bắt đầu chạy ứng dụng không
    @Override
    public void onStart() {
        super.onStart();
        // Kiểm tra nếu user google đã đăng nhập thì làm gì.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Toast.makeText(this, "User signing", Toast.LENGTH_SHORT).show();

    }
    //Hàm gọi cửa sổ đăng nhập Google Account
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 999);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 999) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Đăng nhập google thành công, xác thực với Firbase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Đăng nhập google thất bại, cập nhật giao diện
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }else{
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);}
    }
    //Hàm xử lí xác thực google với Firbase
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Toast.makeText(this, "firebaseAuthWithGoogle:" + acct.getId(), Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(ScreenSignActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ScreenSignActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    //Xử lí hàm xác thực facebook với Firebase
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookToken", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FacebookLoginSuccess", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookLoginFails", "signInWithCredential:failure", task.getException());
                            Toast.makeText(ScreenSignActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
//    FirebaseAuth.getInstance().signOut(); Đăng xuất account
public void xulysaukhilogin(LoginResult loginResult)
{
    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback()
    {
        @Override
        public void onCompleted(JSONObject object, GraphResponse response)
        {
            Log.v("LoginActivity", response.toString());
            // Application code
            try {
                String name = object.getString("name");
                Toast.makeText(ScreenSignActivity.this, ""+name, Toast.LENGTH_SHORT).show();
            } catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(ScreenSignActivity.this, "loi"+ e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    });
    Bundle parameters = new Bundle();
    parameters.putString("fields", "id,name,email ");
    request.setParameters(parameters);request.executeAsync();

}



}