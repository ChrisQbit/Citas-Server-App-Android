package com.corza.newapplicacionc01.ui.login;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.corza.newapplicacionc01.HomeActivity;
import com.corza.newapplicacionc01.R;

import com.corza.newapplicacionc01.RegistroActivity;
import com.corza.newapplicacionc01.data.LoginDataSource;
import com.corza.newapplicacionc01.models.logintoken_model;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.work.Data;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity
{

	  logintoken_model login_model = new logintoken_model();

	  EditText mail;
	  EditText pass;
	  String mailS;
	  String passS;

	  OkHttpClient client = new OkHttpClient();
	  String token;
	  public static final String url = "https://707a-189-203-137-54.ngrok.io";
	  public static final String id_empresa = "1";
	  public  String name_ = "";
	  public  String ide_ = "";
	  Boolean status_con = true;


	  @Override
	  public void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_login);
		    setTitle("Acceso a Cuenta DaterPlus+");

		    final Button loginButton = findViewById(R.id.login);
		    final ProgressBar loadingProgressBar = findViewById(R.id.loading);
		    final Button btnReg = findViewById(R.id.registro);



		    mail = findViewById(R.id.username);
		    pass = findViewById(R.id.password);

		    login_model.setToken("");
		    login_model.setMovil("");
		    login_model.setName("");
		    login_model.setId("");

		    loginButton.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
					   try
					   {
					   	  mailS = mail.getText().toString();
					   	  passS = pass.getText().toString();
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
				 }
		    });

		    btnReg.setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View v)
				 {
					   // Aqui abrimos activity de registro
					   goRegistro();
				 }
		    });
	  }


	  public void goRegistro(){
		    Intent intent = new Intent(this, RegistroActivity.class);
		    startActivity(intent);
	  }



	  private void goHome ()
	  {
				name_ = login_model.getName();
				ide_ = login_model.getId();

				 Intent intent = new Intent(this, HomeActivity.class);
				 Bundle b = new Bundle();
				 b.putString("nombre", login_model.getName()); //Your id
		           b.putString("movil", login_model.getMovil()); //Your id
		           b.putString("token", login_model.getToken()); //Your id
		           b.putString("id", login_model.getId()); //Your id
		           b.putString("email", login_model.getEmail()); //Your id
				 intent.putExtras(b); //Put your id to your next Intent
				 startActivity(intent);
	  }







	  private class CallTask extends AsyncTask<URL, Integer, Long>
	  {
		    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		    protected Long doInBackground(URL... urls) {
				 RequestBody formBody = RequestBody.create(JSON, "{\n" +
					  "    \"mail\": \""+mailS+"\",\n" +
					  "    \"password\": \""+passS+"\"\n" +
					  "}");
				 Request request = new Request.Builder()
					  .url(url+"/api/login_app")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   token = response.body().string();
					   runLOGIN();
				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }




	  private void runLOGIN ()
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
											 if (status_con) {

 											 	   System.out.println(token);
												   logintoken_model data = new Gson().fromJson(token, logintoken_model.class);

												   login_model.setName(data.getName());
												   login_model.setMovil(data.getMovil());
												   login_model.setToken(data.getToken());
												   login_model.setId(data.getId());
												   login_model.setEmail(data.getEmail());

												   goHome();
												   status_con = false;
											 }
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


}
