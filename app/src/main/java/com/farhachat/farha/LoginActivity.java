package com.farhachat.farha;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout Email_Address, Password;
    private FirebaseAuth auth;
    private TextView farha, login_text, or_text, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        login_text = findViewById(R.id.login_text);
        farha = findViewById(R.id.farha);
        or_text = findViewById(R.id.or_text);
        Email_Address = findViewById(R.id.Email_Address);
        Password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Paint font.ttf");
        farha.setTypeface(custom_font);
        login_text.setTypeface(custom_font);
        or_text.setTypeface(custom_font);

        auth = FirebaseAuth.getInstance();

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//                finish();
//            }
//        });


    }

    public void btn_click_login(View V) {
        String txt_email_address, txt_pass;
        txt_email_address = Email_Address.getEditText().getText().toString().trim();
        txt_pass = Password.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(txt_email_address) || TextUtils.isEmpty(txt_pass)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(txt_email_address, txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    @OnClick({R.id.forgot_pass, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forgot_pass:
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                finish();
                break;
            case R.id.register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                break;
        }
    }
}












