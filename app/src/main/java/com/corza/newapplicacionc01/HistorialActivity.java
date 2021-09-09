package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.corza.newapplicacionc01.models.citas_model;
import com.corza.newapplicacionc01.models.historial_model;
import com.corza.newapplicacionc01.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistorialActivity extends AppCompatActivity
{

	  String nombre;
	  String token;
	  String Id;
	  String mobil;
	  String email;

	  LoginActivity log_var;
	  OkHttpClient client = new OkHttpClient();
	  String json;
	  String  [] fechas = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
	  FrameLayout frame;
	  ListView listview ;
	  historial_model[] data ;

	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_historial);
		    setTitle("Mi Historial");
		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");
		    mobil = intent.getStringExtra("mobil");
		    email = intent.getStringExtra("email");
			frame =  findViewById(R.id.frame);
			listview = findViewById(R.id._list);
				 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					   @Override
					   public void onItemClick(AdapterView<?> parent, View view,
										  int position, long id) {
						//	Toast.makeText(getApplicationContext(),
						//		 "Click ListItem Number " + position, Toast.LENGTH_LONG)
						//		 .show();
							GoCancel(position);
					   }
				 });
		    try
		    {
				 new HistorialActivity.CallTask().execute().get();
				 //ArrayAdapter<String> adapter =
					// new ArrayAdapter<String>(this, R.layout.lista_01, fechas);
				 //listview.setAdapter(adapter);
				 final StableArrayAdapter adapter = new StableArrayAdapter(this,
					  android.R.layout.simple_list_item_1, Arrays.asList(fechas));
				 listview.setAdapter(adapter);
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
				 RequestBody formBody = RequestBody.create(JSON,
					  "{\"user_id\": \""+Id+"\"}");
				 Request request = new Request.Builder()
					  .url(log_var.url+"/api/get_historial")
					  .post(formBody)
					  .build();
				 try {
					   Response response = client.newCall(request).execute();
					   // Do something with the response.
					   json = response.body().string();
					   System.out.println(json);
					   data = new Gson().fromJson(json, historial_model[].class);
					   String status = "";
					   for (int i = 0; i <data.length; i ++) {
							if(Arrays.asList(data[i].getStatus()).contains("0")){
								  status="Atendida:";
							}else{
								  if(Arrays.asList(data[i].getStatus()).contains("3")){
									    status="Cancelada:";
								  }else{
									    status="Pendiente:";
								  }
							}
							fechas[i] = status + "\n" +
								 data[i].getFecha_cita() + " " +data[i].getHora();
						}
				 } catch (IOException e) {
					   e.printStackTrace();
				 }
				 return  null;
		    }
	  }


















	  private class StableArrayAdapter extends ArrayAdapter<String> {

		    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		    public StableArrayAdapter(Context context, int textViewResourceId,
								List<String> objects) {
				 super(context, textViewResourceId, objects);
				 for (int i = 0; i < objects.size(); ++i) {
					   mIdMap.put(objects.get(i), i);
				 }
		    }

		    @Override
		    public long getItemId(int position) {
				 String item = getItem(position);
				 return mIdMap.get(item);
		    }

		    @Override
		    public boolean hasStableIds() {
				 return true;
		    }

	  }












	  public void GoCancel(int id_cita) {
		    Intent intent = new Intent(HistorialActivity.this, CancelCitaActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token); //Your id
		    b.putString("id_cita", String.valueOf(data[id_cita].getId())); //Your id
		    b.putString("fecha_txt", fechas[id_cita]);

		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }




}
