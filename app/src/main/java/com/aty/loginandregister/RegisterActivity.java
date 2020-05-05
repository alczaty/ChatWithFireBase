package com.aty.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
  private   FirebaseAuth mAuth;
    CircularProgressButton register;
   private EditText email,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register );
      email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
mAuth = FirebaseAuth.getInstance();
        register= findViewById(R.id.cirRegisterButton);

    }

public  void registerUser(View view){
        createAccount();

}
    public void onLoginClick(View view){
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }


    public void createAccount(){
        String emailtxt,passwordtxt;
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailtxt,passwordtxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("CREATING", "Create user with email success");
                           Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                           startActivity(intent);
                        }else{

                            Log.w("CREATING", "Create user with email failure" );
                            Toast.makeText(RegisterActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
