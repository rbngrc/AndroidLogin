package com.example.pmddmm_eval2_ruben_garcia_diez;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button btnLogin, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Context context = this;

        username = (EditText)findViewById(R.id.etUser);
        password = (EditText)findViewById(R.id.etPassword);
        repassword = (EditText)findViewById(R.id.etRepassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(RegisterActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    if(pass.equals(repass)){
                        Boolean userCheckResult = compruebaUsuario(user, pass);
                        if (userCheckResult == false){
                            registraUsuario(user, pass);
                            Toast.makeText(RegisterActivity.this, "Registrado correctamente", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context ,MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "El usuario ya existe. Logeate.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean compruebaUsuario(String user, String pass){

        FileInputStream fileInputStream = null;
        String FILE_NAME = "users.txt";
        boolean existe = false;

        try {
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String st;
            StringBuilder stringBuilder = new StringBuilder();

            while ((st = bufferedReader.readLine()) != null) {
                String[] word = st.split(":");

                if (word[0].equals(user) && word[1].equals(pass)) {
                    existe = true;
                } else {
                    existe = false;
                }

            }

        } catch (Exception e) {
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

        return existe;
    }

    public void registraUsuario(String user, String pass){
        String FILE_NAME = "users.txt";
        String completo = user + ":" + pass;

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND);
            fileOutputStream.write(completo.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
