package nina.sensurregistrering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    protected void next(View v){
        EditText et = (EditText) findViewById(R.id.eksamenskode);
        String examCode = et.getText().toString();


        //sjekk at Eksamenskode er fylt ut
        if( et.getText().toString().trim().equals("")) {

            et.setError("Må fylles ut!");
        }
        else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra("examCode", examCode);
            startActivity(intent);
        }
    }
}
