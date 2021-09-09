package com.corza.newapplicacionc01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ExplorarActivity extends AppCompatActivity
{

	  String nombre;
	  String token;
	  String Id;
	  @Override
	  protected void onCreate (Bundle savedInstanceState)
	  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_explorar);
		    setTitle("Explorar en la Red");

		    Intent intent = getIntent();
		    nombre = intent.getStringExtra("nombre");
		    token = intent.getStringExtra("token");
		    Id = intent.getStringExtra("id");
	  }
}
