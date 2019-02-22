package com.farhachat.farha;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextInputLayout userName,emailAddress,password,confirm_Password;
    private DatabaseReference reference;
    private TextView farha,registeration_text,or_text,register_with,login_activ;
    private Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //change font
        farha = findViewById(R.id.farha);
        farha = findViewById(R.id.farha);
        or_text = findViewById(R.id.or_text);
        registeration_text = findViewById(R.id.registeration_text);
        register_with = findViewById(R.id.register_with);
        userName = findViewById(R.id.Email_Address);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        login_activ = findViewById(R.id.login_activ);
        confirm_Password = findViewById(R.id.confirm_Password);
        custom_font = Typeface.createFromAsset(getAssets(),"Paint font.ttf");
        farha.setTypeface(custom_font);
        registeration_text.setTypeface(custom_font);
        or_text.setTypeface(custom_font);
        register_with.setTypeface(custom_font);
        registeration_text.setTypeface(custom_font);
        login_activ.setTypeface(custom_font);




        auth = FirebaseAuth.getInstance();


        login_activ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void register(final String username, String emailaddress, String pass, String confirmpass)
    {
            auth.createUserWithEmailAndPassword(emailaddress, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userid = firebaseUser.getUid();


                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userid);
                                hashMap.put("username", username);
                                hashMap.put("imageURl", "default");

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();

                                        }

                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



    }

    public void btn_click_register (View V)
    {
        String txt_username,txt_emailaddress,txt_pass,txt_confirmpass;
        txt_username = userName.getEditText().getText().toString().trim();
        txt_emailaddress = emailAddress.getEditText().getText().toString().trim();
        txt_pass = password.getEditText().getText().toString().trim();
        txt_confirmpass = confirm_Password.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(txt_username)|| TextUtils.isEmpty(txt_emailaddress)|| TextUtils.isEmpty(txt_pass)||TextUtils.isEmpty(txt_confirmpass))
        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else if(txt_pass.length()<6)
        {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.equals(txt_pass,txt_confirmpass))
        {
            Toast.makeText(this, "Password and Confirm Password not matched", Toast.LENGTH_SHORT).show();
        }
        else
        {
            register(txt_username,txt_emailaddress,txt_pass,txt_confirmpass);
        }

    }



}
