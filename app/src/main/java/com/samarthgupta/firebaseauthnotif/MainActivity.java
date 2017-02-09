package com.samarthgupta.firebaseauthnotif;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button bt1, bt2, bt3;
    FirebaseAuth auth;
    ImageView iv1;
    Bitmap bitmap;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref=firebase.getReference();
        auth = FirebaseAuth.getInstance();
        bt3 = (Button) findViewById(R.id.button6);
        bt2 = (Button) findViewById(R.id.button5);
        bt1 = (Button) findViewById(R.id.button4);
        iv1=(ImageView)findViewById(R.id.imageView);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                // this listener will be called when there is change in firebase user session
                FirebaseUser user = auth.getCurrentUser();
                if (user == null) {
                    auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            convertAndSave(bitmap);
            }
        });
    }

    private void convertAndSave(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String imageencoded = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        ref.child(auth.getCurrentUser().getUid()).child("Image URL").setValue(imageencoded);
        Toast.makeText(getApplicationContext(),"Uploaded successfully",Toast.LENGTH_LONG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap image = (Bitmap)bundle.get("data");
            bitmap = image;
            iv1.setImageBitmap(image);
        }
    }
}