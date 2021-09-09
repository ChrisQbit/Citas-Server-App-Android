package com.corza.newapplicacionc01;

import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.corza.newapplicacionc01.models.address_model;
import com.corza.newapplicacionc01.models.logintoken_model;
import com.corza.newapplicacionc01.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import com.google.gson.Gson;

public class AddressActivity extends AppCompatActivity
{
	LoginActivity log_var;
	String token ;
	address_model adm = new address_model();

	  EditText ed0;
	EditText ed1;
	  EditText ed2;
	  EditText ed3;

	  String nombre = "" ;
	  String Id = "";


	  OkHttpClient client = new OkHttpClient();
	  EditText ed_calle;
	  EditText ed_col;
	  EditText ed_cp;

	  String json;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_address);
		    Toolbar toolbar = findViewById(R.id.toolbar);
		    setSupportActionBar(toolbar);
		    setTitle("Dirección de Envío");

		    final Button btn = findViewById(R.id.button_first);

			ed_calle = findViewById(R.id.calle_numero);
			ed_col = findViewById(R.id.colonia_estado);
			ed_cp = findViewById(R.id.cp);



		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");



		    //setActivityBackgroundColor();
		    ed1 = findViewById(R.id.calle_numero);
		    ed2 = findViewById(R.id.colonia_estado);
		    ed3 = findViewById(R.id.cp);
		    ed0 = findViewById(R.id.username);
		    adm.setColonia_estado("");
		    adm.setCodigo_postal("");
		    adm.setCodigo_postal("");







		    FloatingActionButton fab = findViewById(R.id.fab);

		    try
		    {
				 new CallTask().execute().get();
		    }
		    catch (ExecutionException e)
		    {
				 e.printStackTrace();
		    }
		    catch (InterruptedException e)
		    {
				 e.printStackTrace();
		    }

		    fab.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View view)
				 {


				 	  // AQui llamamos el guardado mas bien
					   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						    .setAction("Action", null).show();
				 }
		    });



		    btn.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
					   new AddressActivity.CallTaskUpdate().execute();
					   // Aqui lanzo toast de que ya se guardo la info
					   Toast.makeText(getApplicationContext(),
						    "Dirección Actualizada! ", Toast.LENGTH_LONG)
						    .show();
				 }
		    });
	  }








	  public void setActivityBackgroundColor() {
		    View view = this.getWindow().getDecorView();
		    view.setBackgroundColor(Color.BLACK);
	  }





	  private class CallTask extends AsyncTask<URL, Integer, Long>
	  {

		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, "{\n" +
					  "    \"user_id\": \""+Id+"\",\n" +
					  "    \"token\": \""+token+"\"\n" +
					  "}");
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/getAdress")
					  .post(formBody)
					  .build();

				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.

					   token = response.body().string();

					   address_model data = new Gson().fromJson(token, address_model.class);
					   adm.setCalle_numero(data.getCalle_numero());
					   adm.setCodigo_postal(data.getCodigo_postal());
					   adm.setColonia_estado(data.getColonia_estado());

					   runApplyInfo();


				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }


	  }





	  private void runApplyInfo ()
	  {
		    new Thread()
		    {
				 public void run ()
				 {
					   int i = 0;
					   while (i++ < 500)
					   {
							try
							{
								  runOnUiThread(new Runnable()
								  {
									    @Override
									    public void run ()
									    {


											 ed1.setText(adm.getCalle_numero());
											 ed2.setText(adm.getColonia_estado());
											 ed3.setText(adm.getCodigo_postal());
											 ed0.setText(nombre);
									    }
								  });
								  Thread.sleep(1);
							}
							catch (InterruptedException e)
							{
								  e.printStackTrace();
							}
					   }
				 }
		    }.start();
	  }







	  public String json_post(){
		    String json = "{"+"\"calle\":"+"\""+ed_calle.getText().toString()+"\",";
		    json += "\"user_id\":"+"\""+Id+"\",";
		    json += "\"colonia\":"+"\""+ed_col.getText().toString()+"\",";
		    json += "\"cp\":"+"\""+ed_cp.getText().toString()+"\"";
		    json += "}";

		    return json;
	  }





	  private class CallTaskUpdate extends AsyncTask<URL, Integer, Long>
	  {

		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {


				 System.out.println("************* RESPIESTA *************\n"+token+"<<<<"+Id);

				 System.out.println(json_post());

				 RequestBody formBody = RequestBody.create(JSON, json_post());
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/update_address")
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
