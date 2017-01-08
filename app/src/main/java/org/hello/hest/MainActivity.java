package org.hello.hest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.hello.rest.R;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HttpRequestTask().execute("Salon"); // Initial refresh when opening
        new HttpRequestTask().execute("Chambre-1");
        Button buttonC = (Button) findViewById(R.id.buttonC);
        Button buttonS = (Button) findViewById(R.id.buttonS);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute("Salon");
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute("Chambre-1");
            }
        });

    }

    // Create the menu bar which shows in the up right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
// traitement du bouton Settings modifié :
        if (id == R.id.action_settings) {
            new HttpRequestTask().execute("Salon,Chambre-1"); // appelle la méthode doInBackground() de la classe HttpRequestTask
            Toast.makeText( getApplicationContext(),
                    "Actualisation All...",
                    Toast.LENGTH_SHORT ).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, TemperatureReponse> {

        @Override
        protected TemperatureReponse doInBackground(String... params) {
            String nomsCaps = params[0];

            try {
                final String url = "http://192.168.43.35:8080/temps/"+nomsCaps;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, TemperatureReponse.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(TemperatureReponse tempReponse) {
            TextView salonTemp = (TextView) findViewById(R.id.treSalon);
            TextView chambre1Temp = (TextView) findViewById(R.id.treChambre1);

            if (tempReponse.getMapNomsTems().containsKey("Salon")) {
                salonTemp.setText(String.valueOf(tempReponse.getMapNomsTems().get("Salon")));
            }

            if(tempReponse.getMapNomsTems().containsKey("Chambre-1")) {
                chambre1Temp.setText(String.valueOf(tempReponse.getMapNomsTems().get("Chambre-1")));
            }

            if (tempReponse == null) {
                salonTemp.setText("PAS DE RESEAU");
                chambre1Temp.setText("PAS DE RESEAU");
            }

        }
    }


}
