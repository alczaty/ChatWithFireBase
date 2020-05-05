package com.aty.loginandregister;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {
    private EditText email, password;
    CircularProgressButton login;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.cirLoginButton);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onLogin(View view) {
        signInWithEmailAndPassword();
    }

    public void onNeedUserClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    //    public void  updateUI(FirebaseUser account){
//        if(account != null){
//            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
//
//        }else {
//            Toast.makeText(this,"U Didnt signed in", Toast.LENGTH_SHORT).show();
//        }
//    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    public void signInWithEmailAndPassword() {
        final String emailtxt = email.getText().toString();
        final String passwordtxt = password.getText().toString();
        if (emailtxt.equals("") || passwordtxt.equals(""))
            return;
        mAuth.signInWithEmailAndPassword(emailtxt, passwordtxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LOGIN", "Login successful");
                            Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                         startActivity(intent);
                        } else {

                            Log.w("LOGIN", "error login" + task.getException().getMessage());
                            Toast.makeText(MainActivity.this, "FAILED LOGIN", Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                email.setError("Invalid Email Id");
                                email.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Log.d("login " , "email :" + email);
                                password.setError("Invalid Password");
                                password.requestFocus();
                            } catch (Exception e) {
                                Log.e("login ", e.getMessage());
                            }
                        }
                    }
                });
    }
}
