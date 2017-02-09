package com.samarthgupta.firebaseauthnotif;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText email,pass;
    Button bt1;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText)findViewById(R.id.editText);
        pass = (EditText)findViewById(R.id.editText2);
        bt1=(Button)findViewById(R.id.button);

        auth=FirebaseAuth.getInstance();


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","IN");
                String em = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                auth.createUserWithEmailAndPassword(em,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("TAG","IN");
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration failed ",Toast.LENGTH_SHORT);
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"Registration Success"+Boolean.toString(task.isSuccessful()),Toast.LENGTH_LONG);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
            }
        });
    }
}
