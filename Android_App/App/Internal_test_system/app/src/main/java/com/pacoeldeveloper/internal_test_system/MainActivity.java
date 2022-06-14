package com.pacoeldeveloper.internal_test_system;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se borra la barra de título
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);



        // Obtenemos lo que se escribe en pantalla
        username = findViewById(R.id.edtUsuario);
        password = findViewById(R.id.edtPassword);
        login_button = findViewById(R.id.btnLogin);

        // Click del botón
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser("http://localhost/login_register/validar_usuario.php");
            }
        });
    }

    private void validateUser(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty() || username.toString().equals("Hello")){
                    Intent intent = new Intent(getApplicationContext(), PrincipalAppActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Wrong credentials... try harder",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Intent intent = new Intent(getApplicationContext(), PrincipalAppActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "No connnection to server", Toast.LENGTH_SHORT)
                //        .show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user", username.getText().toString());
                parameters.put("password", password.getText().toString());
                return parameters;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}