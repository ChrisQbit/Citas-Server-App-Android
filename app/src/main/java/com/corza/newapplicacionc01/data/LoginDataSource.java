package com.corza.newapplicacionc01.data;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;

import com.corza.newapplicacionc01.R;
import com.corza.newapplicacionc01.data.model.LoggedInUser;
import com.corza.newapplicacionc01.models.logintoken_model;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource
{


	  public logintoken_model login (String username, String password)
	  {
		    logintoken_model login_model = new logintoken_model();
		    try
		    {


				 return login_model;
		    }
		    catch (Exception e)
		    {
				 return login_model;
		    }
	  }
	  public void logout ()
	  {
		    // TODO: revoke authentication
	  }






}
