package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.corza.newapplicacionc01.ui.login.LoginActivity;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RegistroActivity extends AppCompatActivity
{


	  Button bt ;

	  LoginActivity log_var;
	  OkHttpClient client = new OkHttpClient();

	  String json;
	  EditText txnombre;
	  EditText txmail;
	  EditText txpass;
	  EditText txmobil;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_registro);
		    setTitle("Registro de Usuario");

		    txnombre = findViewById(R.id.txnombre);
		    txmail = findViewById(R.id.txmail);
		    txpass = findViewById(R.id.txpass);
		    txmobil = findViewById(R.id.txmobil);

		    bt = findViewById(R.id.registro);
		    bt.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
				 	  bt.setActivated(false);
				 	  new CallTaskRegistro().execute();
					   // Aqui mando llamar el registro api
					   finish();
					   Toast.makeText(getApplicationContext(),
						    "Exito. Ahora Accede.", Toast.LENGTH_LONG)
						    .show();
				 }
		    });
	  }






	  private class CallTaskRegistro extends AsyncTask<URL, Integer, Long>
	  {
		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, json_post());
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/registro_app")
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




	  public String json_post(){
		    String json = "{"+
				  "\"nombre\":"+"\""+txnombre.getText().toString()+"\",";
		    json += "\"email\":"+"\""+txmail.getText().toString()+"\",";
		    json += "\"password\":"+"\""+txpass.getText().toString()+"\",";
		    json += "\"mobil\":"+"\""+txmobil.getText().toString()+"\"}";
		    return json;
	  }
}
