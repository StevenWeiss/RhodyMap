package com.example.rhodymap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener
{

    private Button submitButton, skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button)this.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        skipButton = (Button)this.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(this);

        DataManager data = new DataManager(this);
        data.getReadableDatabase(); // Creates the database.

    }

    public void onClick(View v)
    {
        Intent myIntent = new Intent(this, MapManager.class);

        // Sends the username and password to MapManager if the submit button
        // is pressed.
        if (v.getId() == submitButton.getId())
        {
            TextView username = (TextView)this.findViewById(R.id.usernameText);
            TextView password = (TextView)this.findViewById(R.id.passwordText);
                        
            myIntent.putExtra("username", username.getText().toString());
            myIntent.putExtra("password", password.getText().toString());
        }

        startActivity(myIntent);

    }
}