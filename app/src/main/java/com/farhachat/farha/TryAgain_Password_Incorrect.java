package com.farhachat.farha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TryAgain_Password_Incorrect extends AppCompatActivity {
TextView back_To_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_again__password__incorrect);
        back_To_Login = findViewById(R.id.back_to_login);
        back_To_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TryAgain_Password_Incorrect.this,LoginActivity.class));
                finish();
            }
        });
    }

}
