package com.workera.apps.starwars;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);




        SWCharacter savedExtra = (SWCharacter) getIntent().getSerializableExtra("character");
        TextView nameView = (TextView) findViewById(R.id.name);
        TextView heightView = (TextView) findViewById(R.id.height);
        TextView massView = (TextView) findViewById(R.id.mass);
        TextView dateView = (TextView) findViewById(R.id.date);

        // If data has been fetched and transferred to the second screen.
        if (savedExtra != null) {
            nameView.setText(savedExtra.getName());
            dateView.setText(savedExtra.getdTime());

            // Formatting the double values to 2 decimal places with unit at the end

            double mass = convertTo2dec(savedExtra.getMass());
            String mass_string = Double.toString(mass);
            String m_display = mass_string + " kg";
            massView.setText(m_display);

            double height = convertTo2dec(savedExtra.getHeight());
            String height_string = Double.toString(height);
            String h_display = height_string + " m";
            heightView.setText(h_display);


        }

    }

    public double convertTo2dec(double value){

        value = value *100 ;
        value = Math.round(value);
        value = value/100;

        return value;
    }
}
