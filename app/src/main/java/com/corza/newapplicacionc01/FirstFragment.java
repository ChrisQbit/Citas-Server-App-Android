package com.corza.newapplicacionc01;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment
{


	  ProgressBar pgr;
	  @Override
	  public View onCreateView (
		   LayoutInflater inflater, ViewGroup container,
		   Bundle savedInstanceState
	  )
	  {


		    // Inflate the layout for this fragment
		    return inflater.inflate(R.layout.fragment_first, container, false);
	  }

	  public void onViewCreated (@NonNull View view, Bundle savedInstanceState)
	  {
		    super.onViewCreated(view, savedInstanceState);

		    view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener()
		    {
				 @Override
				 public void onClick (View view)
				 {

					   pgr = pgr.findViewById(R.id.button_first);

					   NavHostFragment.findNavController(FirstFragment.this)
						    .navigate(R.id.action_FirstFragment_to_SecondFragment);


					   // Aqui se llama el progress para guardar y recuperar INFO
					   ObjectAnimator progressAnimator;
					   progressAnimator = ObjectAnimator.ofInt(pgr, "progress", 0,1);
					   progressAnimator.setDuration(7000);
					   progressAnimator.start();
				 }
		    });
	  }


}
