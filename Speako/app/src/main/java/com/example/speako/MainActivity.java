package com.example.speako;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {

private static final String FILE_NAME="example.txt";

    EditText txtSpeak;
    Button btnSpeech,btnSave,btnLoad,btnCopy,btnSignout;
    String input;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        txtSpeak =findViewById(R.id.txtSpeak);
        btnSpeech=findViewById(R.id.btnSpeech);
        btnSignout=findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        btnCopy=findViewById(R.id.btnCopy);
        ClipboardManager cm= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData cd=ClipData.newPlainText("label",txtSpeak.getText().toString());
            cm.setPrimaryClip(cd);
                Toast.makeText(MainActivity.this,"Text Copied",Toast.LENGTH_SHORT).show();
            }
        });
        btnLoad=findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(v);
            }
        });
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        btnSpeech.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                SpeakNow(view);
            }
        });
    }

    private void SpeakNow(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking....");
        startActivityForResult(intent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==111 && resultCode== RESULT_OK){
            txtSpeak.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
           input= (String) txtSpeak.getText().toString();


//            finish();
//            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
//            startActivity(intent);
        }
    }

public void save(View v) {

        String text=txtSpeak.getText().toString();
    FileOutputStream fos=null;
try{
    fos=openFileOutput(FILE_NAME, MODE_PRIVATE);
    fos.write(text.getBytes());
    txtSpeak.getText().clear();
    Toast.makeText(this,"Save to "+getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();
}catch(FileNotFoundException e){
    e.printStackTrace();
    }
catch(IOException e){
e.printStackTrace();
    }finally {
    if(fos !=null){
        try {
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    }


}
    public void load(View v){
        FileInputStream fis = null;
        try {
            fis=openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text=br.readLine())!=null){
                sb.append(text).append("\n");
            }
            txtSpeak.setText(sb.toString());

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis !=null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

}

