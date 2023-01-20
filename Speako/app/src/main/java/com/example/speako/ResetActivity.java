package com.example.speako;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {

    Button btnChange;
    EditText Password,cnfrmPass;
    TextView resetmailText;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        DB=new DBHelper(this);
        btnChange=findViewById(R.id.btnChange);
        Password=findViewById(R.id.resetPassword);
        cnfrmPass=findViewById(R.id.resetConfirmPassword);
        resetmailText=findViewById(R.id.resetmailTxt);
        Intent intent=getIntent();
        resetmailText.setText(intent.getStringExtra("mail"));

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(checkCredential()) {
                   String Mail=resetmailText.getText().toString();
                   String password=Password.getText().toString();
                   String cnfrmpass=cnfrmPass.getText().toString();
                   Boolean checkpassupdate = DB.updatePass(Mail, password);
                   if (checkpassupdate == true) {
                       Toast.makeText(ResetActivity.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                       startActivity(intent);
                   } else {
                       Toast.makeText(ResetActivity.this, "Unable to Update Password", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });
    }

    private boolean checkCredential(){
        String Mail=resetmailText.getText().toString();
        String password=Password.getText().toString();
        String cnfrmpass=cnfrmPass.getText().toString();

        if(password.isEmpty() || password.length()<8){
            showError(Password,"Invalid Password! must be 8 digits");
        }
        else if(cnfrmpass.isEmpty() || !cnfrmpass.equals(password)){
            showError(cnfrmPass,"Passwords not match!");
        }
        else{
            return true;
        }
        return false;
    }

    private void showError(EditText in_fields, String s) {
        in_fields.setError(s);
        in_fields.requestFocus();
    }
}