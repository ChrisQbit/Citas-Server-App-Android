package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
{

	  TextView nombre_txt;
	  TextView mobil_txt;
	  TextView token_txt;
	  String nombre ;
	  String mobil ;
	  String token ;
	  String email ;

	  String Id;

	  Button button;
	  Button button_wallet;
	  Button button_agendar;
	  Button button_explorar;
	  Button button_historial;


	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_home);
		    setTitle("Hola, Bienvenide");
		    button = (Button) findViewById(R.id.direccion_btn);
		    button.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					   SaveAddress();
				 }
		    });

		    button_wallet = (Button) findViewById(R.id.btn_wallet);
		    button_wallet.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					  GetWalet();
				 }
		    });

		    button_agendar = (Button) findViewById(R.id.btn_agendar);
		    button_agendar.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					   GoAgendar();
				 }
		    });


		    button_explorar = (Button) findViewById(R.id.btn_explorar);
		    button_explorar.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					   Explorar();
				 }
		    });

		    button_historial = (Button) findViewById(R.id.btn_historial);
		    button_historial.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View v) {
					   GoHistorial();
				 }
		    });


		    nombre_txt = findViewById(R.id.nombre_txt);
		    mobil_txt = findViewById(R.id.mobil_txt);
		    token_txt = findViewById(R.id.token_txt);

		    //setActivityBackgroundColor();

		     Intent intent = getIntent();
		     nombre = intent.getStringExtra("nombre");
		     mobil = intent.getStringExtra("movil");
		     token = intent.getStringExtra("token");
		     Id = intent.getStringExtra("id");
		     email = intent.getStringExtra("email");
		     runUpdateData();

	  }


	  public void setActivityBackgroundColor() {
		    View view = this.getWindow().getDecorView();
		    view.setBackgroundColor(Color.BLACK);
	  }

	  public void SaveAddress() {

		    Intent intent = new Intent(HomeActivity.this, AddressActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token); //Your id
		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }


	  public void Explorar() {

		    Intent intent = new Intent(HomeActivity.this, ExplorarActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token); //Your id
		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }

	  public void GoAgendar() {
		    Intent intent = new Intent(HomeActivity.this, AgendarActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token); //Your id
		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }


	  public void GetWalet() {

		    Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token);
		    b.putString("mobil", mobil);//Your id
		    b.putString("email", email);//Your id
		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }


	  public void GoHistorial() {

		    Intent intent = new Intent(HomeActivity.this, HistorialActivity.class);
		    Bundle b = new Bundle();
		    b.putString("nombre", nombre); //Your id
		    b.putString("id", Id); //Your id
		    b.putString("token", token);
		    b.putString("mobil", mobil);//Your id
		    b.putString("email", email);//Your id
		    intent.putExtras(b); //Put your id to your next Intent
		    startActivity(intent);
	  }


	  private void runUpdateData ()
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
											 nombre_txt.setText(nombre);
											 mobil_txt.setText(mobil);
											 token_txt.setText(token);
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
