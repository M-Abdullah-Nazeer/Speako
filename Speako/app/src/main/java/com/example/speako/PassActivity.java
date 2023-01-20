package com.example.speako;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PassActivity extends AppCompatActivity {

    Button btnReset;
    EditText Mail;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        DB=new DBHelper(this);
        Mail=findViewById(R.id.resetMail);
        btnReset=findViewById(R.id.btnReset);



        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=Mail.getText().toString();
                Boolean checkuser= DB.checkmail(mail);
                if(checkuser==true){

                    Intent intent=new Intent(PassActivity.this,ResetActivity.class);
                    intent.putExtra("mail",mail);
                    Toast.makeText(PassActivity.this,"Email exist",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }else{
                    Toast.makeText(PassActivity.this,"User does not exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}