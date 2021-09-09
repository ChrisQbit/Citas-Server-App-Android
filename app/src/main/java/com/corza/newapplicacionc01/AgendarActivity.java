package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.corza.newapplicacionc01.models.address_model;
import com.corza.newapplicacionc01.models.citas_model;
import com.corza.newapplicacionc01.models.empresas_model;
import com.corza.newapplicacionc01.ui.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AgendarActivity extends AppCompatActivity
{

	  LoginActivity log_var;
	  OkHttpClient client = new OkHttpClient();

	  String json;

	  String nombre;
	  String token;
	  String Id;

	  String  [] fechas = {"", "", "", "", "", "", "","",""};
	  String  [] empresas = {"", "", ""};

	  Spinner dropdown;
	  Spinner dropdown2;
	  Button btn_agendar;
	  String id_empresa;
	  String alt = "";
	  String empresa;
	  String fecha;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_agendar);
		    setTitle("Agedar mi Cita");

		    id_empresa = log_var.id_empresa;
		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");


		    //get the spinner from the xml.
		    dropdown = findViewById(R.id.spinner1);

		    //get the spinner from the xml.
		    dropdown2 = findViewById(R.id.spinner2);

		    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fechas);
			//set the spinners adapter to the previously created one.
		    dropdown.setAdapter(adapter);




		    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, empresas);
		    //set the spinners adapter to the previously created one.
		    dropdown2.setAdapter(adapter2);

		    dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				 @Override
				 public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					   alt = "_v2";
					   id_empresa = dropdown2.getSelectedItem().toString();
					   for(int i =0; i <= fechas.length - 1; i++){
					   	  fechas[i] = "";
					   }
					   connectCitas();
					   new_adapter(fechas);

				 }

				 @Override
				 public void onNothingSelected(AdapterView<?> parentView) {
					   // your code here
				 }



		    });

		    try
		    {
				 new CallTaskEmpresas().execute().get();
		    }
		    catch (ExecutionException e)
		    {
				 e.printStackTrace();
		    }
		    catch (InterruptedException e)
		    {
				 e.printStackTrace();
		    }


		    connectCitas();


		    btn_agendar = findViewById(R.id.btn_confirmar_cita);
		    btn_agendar.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
					   try
					   {
					   	  empresa = dropdown2.getSelectedItem().toString();
							   fecha = dropdown.getSelectedItem().toString();


							new CallTaskAgendar().execute().get();
					   }
					   catch (ExecutionException e)
					   {
							e.printStackTrace();
					   }
					   catch (InterruptedException e)
					   {
							e.printStackTrace();
					   }

					   Toast.makeText(getApplicationContext(),
						    "Exito al Agendar.", Toast.LENGTH_LONG)
						    .show();
				 }
		    });

	  }

	  public void new_adapter(String [] fechas_v2){
		    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fechas_v2);
		    //set the spinners adapter to the previously created one.
		    dropdown.setAdapter(adapter);
	  }

	  public void connectCitas(){
		    try
		    {
				 new AgendarActivity.CallTask().execute().get();
		    }
		    catch (ExecutionException e)
		    {
				 e.printStackTrace();
		    }
		    catch (InterruptedException e)
		    {
				 e.printStackTrace();
		    }
	  }






	  private class CallTask extends AsyncTask<URL, Integer, Long>
	  {

		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, "{\n" +
					  "    \"user_id\": \""+Id+"\",\n" +
					  "    \"id_empresa\": \""+id_empresa+"\"\n" +
					  "}");
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/get_citas"+alt)
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();

					   citas_model[] data = new Gson().fromJson(json, citas_model[].class);

					   for (int i = 0; i <data.length; i ++) {
							System.out.println (data [i]);
							fechas[i] = data[i].getFecha_cita() + " - " +data[i].getHora();
					   }
				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }


	  }








	  private class CallTaskEmpresas extends AsyncTask<URL, Integer, Long>
	  {

		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, "{\n" +
					  "    \"user_id\": \""+Id+"\",\n" +
					  "    \"id_empresa\": \""+log_var.id_empresa+"\"\n" +
					  "}");
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/get_empresas")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();

					   System.out.println(json);

					   empresas_model[] data = new Gson().fromJson(json, empresas_model[].class);

					   for (int i = 0; i <data.length; i ++) {
							System.out.println (data [i]);
							empresas[i] = data[i].getNombre();
					   }
				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }






	  private class CallTaskAgendar extends AsyncTask<URL, Integer, Long>
	  {

		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {

		    	  String send_json = "{" +
				   "    \"user_id\": \""+Id+"\"," +
				   "    \"id_empresa\": \""+empresa+"\"," +
				   "    \"fecha_hora\": \""+fecha+"\"" +
				   "}";
		    	     RequestBody formBody = RequestBody.create(JSON, send_json);

				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/agendar")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();


				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }

}