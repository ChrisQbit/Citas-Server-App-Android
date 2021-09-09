package com.corza.newapplicacionc01.models;

public class empresas_model
{
	  String id;
	  String nombre;
	  String categoria;
	  String created_at;

	  public String getId() {
		    return id;
	  }
	  public void setId(String id) {
		    this.id = id;
	  }

	  public String getNombre() {
		    return nombre;
	  }
	  public void setNombre(String nombre) {
		    this.nombre = nombre;
	  }

	  public String getCategoria() {
		    return categoria;
	  }
	  public void setCategoria(String categoria) {
		    this.categoria = categoria;
	  }

	  public String getCreated_at() {
		    return created_at;
	  }
	  public void setCreated_at(String created_at) {
		    this.created_at = created_at;
	  }

}
