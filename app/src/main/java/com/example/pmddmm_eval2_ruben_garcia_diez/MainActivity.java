package com.example.pmddmm_eval2_ruben_garcia_diez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.etUser);
        password = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        SharedPreferences settings = getSharedPreferences("user", MODE_PRIVATE);
        String n = settings.getString("username", "");
        username.setText(n);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    compruebaUsuario(user, pass);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        username = (EditText)findViewById(R.id.etUser);

        String user = username.getText().toString();

        SharedPreferences settings = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", user);
        editor.apply();
    }

    public void compruebaUsuario(String user, String pass){

        FileInputStream fileInputStream = null;
        String FILE_NAME = "users.txt";

        try {
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String st;
            // TODO



            if (fileInputStream != null) {
                while ((st = bufferedReader.readLine()) != null) {
                    String[] word = st.split(":");

                    if (word[0].equals(user) && word[1].equals(pass)) {
                        Intent intent = new Intent(this, PrincipalMenu.class);
                        startActivity(intent);

                    } else if (word[0].equals(user) && !word[1].equals(pass)) {
                        Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, RegisterActivity.class);
                        startActivity(intent);
                    }

                }
            } else {
                Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}