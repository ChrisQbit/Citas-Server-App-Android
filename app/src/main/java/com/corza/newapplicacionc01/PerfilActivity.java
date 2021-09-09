package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.corza.newapplicacionc01.models.citas_model;
import com.corza.newapplicacionc01.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class PerfilActivity extends AppCompatActivity
{
	  String nombre;
	  String token;
	  String Id;
	  String mobil;
	  String email;
	  EditText ed_nombre;
	  EditText ed_mobil;
	  EditText ed_correo;
	  EditText ed_pass;
	  LoginActivity log_var;
	  OkHttpClient client = new OkHttpClient();
	  String json;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_perfil);
		    setTitle("Mi Perfil");
		    final Button loginButton = findViewById(R.id.button_first);
		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");
		    mobil = intent.getStringExtra("mobil");
		    email = intent.getStringExtra("email");
		    ed_nombre = findViewById(R.id.username);
		    ed_nombre.setText(nombre);
		    ed_mobil = findViewById(R.id.telefono);
		    ed_mobil.setText(mobil);
		    ed_correo = findViewById(R.id.correo);
		    ed_correo.setText(email);
		    ed_pass = findViewById(R.id.password);
		    loginButton.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
				 	  new CallTaskUpdate().execute();
					   // Aqui lanzo toast de que ya se guardo la info
					   Toast.makeText(getApplicationContext(),
						    "Información Actualizada!", Toast.LENGTH_LONG)
						    .show();
				 }
		    });
	  }

	  public String json_post(){
			    String json = "{"+"\"nombre\":"+"\""+ed_nombre.getText().toString()+"\",";
					 json += "\"user_id\":"+"\""+Id+"\",";
					 json += "\"mail\":"+"\""+ed_correo.getText().toString()+"\",";
					 json += "\"password\":"+"\""+ed_pass.getText().toString()+"\",";
					 json += "\"mobil\":"+"\""+ed_mobil.getText().toString()+"\"}";
			    return json;
	  }

	  private class CallTaskUpdate extends AsyncTask<URL, Integer, Long>
	  {
		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, json_post());
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/update_perfil")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();
					   System.out.println(json);
				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }
}
