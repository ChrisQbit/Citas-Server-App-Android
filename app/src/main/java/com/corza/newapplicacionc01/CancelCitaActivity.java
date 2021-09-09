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
import android.widget.TextView;
import android.widget.Toast;

import com.corza.newapplicacionc01.models.historial_model;
import com.corza.newapplicacionc01.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class CancelCitaActivity extends AppCompatActivity
{

	  String nombre;
	  String token;
	  String Id;
	  String mobil;
	  String email;
	  String id_cita;
	  String txt_fecha;
	  TextView tx;
	  Button btn;
	  EditText edt;


	  LoginActivity log_var;
	  OkHttpClient client = new OkHttpClient();
	  String json;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_cancel_cita);
		    setTitle("Detalle de Cita");

		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");
		    mobil = intent.getStringExtra("mobil");
		    email = intent.getStringExtra("email");
		    id_cita= intent.getStringExtra("id_cita");
		    txt_fecha = intent.getStringExtra("fecha_txt");
		    tx = findViewById(R.id.txt_cita);
		    tx.setText(txt_fecha);
		    if(tx.getText().toString().contains("Atendida")){
		    	  btn = findViewById(R.id.cancel_btn);
		    	  edt = findViewById(R.id.editText);
				 btn.setActivated(false);
				 btn.setText("CITA ATENDIDA");
				 edt.setActivated(false);
		    }

		    if(tx.getText().toString().contains("Cancelada")){
				 btn = findViewById(R.id.cancel_btn);
				 btn.setActivated(false);
				 btn.setText("CITA CANCELADA");
		    }
		    if(tx.getText().toString().contains("Pendiente")){
				 btn = findViewById(R.id.cancel_btn);
				 btn.setOnClickListener(new View.OnClickListener()
				 {
					   @Override
					   public void onClick (View v)
					   {
							new CancelCitaActivity.CallTaskCancelar().execute();
							// Aqui lanzo toast de que ya se guardo la info
							Toast.makeText(getApplicationContext(),
								 "Se Cancelo la Cita.", Toast.LENGTH_LONG)
								 .show();
					   }
				 });
		    }
	  }








	  private class CallTaskCancelar extends AsyncTask<URL, Integer, Long>
	  {
		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, "{\n" +
					  "    \"user_id\": \""+Id+"\",\n" +
					  "    \"cita_id\": \""+id_cita+"\"\n" +
					  "}");
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/cancel_cita")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();


					   System.out.println("************* CANCEL RESPUESTA *************************\n"
					   + json);

				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }






}
