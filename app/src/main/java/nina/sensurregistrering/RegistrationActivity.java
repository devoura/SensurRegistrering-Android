package nina.sensurregistrering;

import android.app.ActionBar;
import android.content.Intent;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class RegistrationActivity extends AppCompatActivity {


    String url = "http://lbdotnet.no/reg.php";
    String charset = "UTF-8";
    String grade;
    String examCode;
    String candidateId;
    EditText et;
    Spinner spin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Lag Karakterspinner
        spin = (Spinner) findViewById(R.id.gradeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        //hent eksamenskode fra forrige activity
        Intent intent = getIntent();
        examCode = intent.getStringExtra("examCode");
        setTitle(examCode);

        et = (EditText) findViewById(R.id.candidateID);

    }

    protected void sendInformation(View v){

        //Sjekk at Kandidatnummer er fylt ut
        if( et.getText().toString().trim().equals("")) {

            et.setError("Må fylles ut!");
        }
        else {

            grade = spin.getSelectedItem().toString();
            candidateId = et.getText().toString();

            HttpURLConnection connection = null;
            String query = new String("");
            try {
                query = String.format("ekd=%s&knr=%s&kar=%s",
                        URLEncoder.encode(examCode, charset),
                        URLEncoder.encode(candidateId, charset),
                        URLEncoder.encode(grade, charset));

                connection = (HttpURLConnection) new URL(url + "?" + query).openConnection();
                connection.setRequestProperty("Accept-Charset", charset);
                InputStream response = connection.getInputStream();

                //sender til en URL som ikke virker, så vil alltid få error her.
            } catch (IOException | NetworkOnMainThreadException e) {
                Log.e("Error", "IO Error");
                Toast.makeText(getApplicationContext(), "Connection error for " + url + "?" + query, Toast.LENGTH_SHORT).show();

            } finally {

                Toast.makeText(getApplicationContext(), "Sendt Karakter: " + grade, Toast.LENGTH_SHORT).show();
                connection.disconnect();
                et.setText("");
            }

        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }


}
