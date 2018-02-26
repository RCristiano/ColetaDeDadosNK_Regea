package br.com.regea.coletadedadosnk_regea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity {

    public static final String MY_SHAREDPREF = "Shared_Prefs_NK_Regea";
    public static final String REQUERID_MSG = "Requerido";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        sharedPreferences = getSharedPreferences(MY_SHAREDPREF, MODE_PRIVATE);
        String name = sharedPreferences.getString("CadName", null);

        if ( name != null  ) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("CadName", name);
            startActivity(intent);
        }
    }

    public void logCad (View view) {
        EditText cadName = findViewById(R.id.txt_CadName);

        if ( cadName.length() < 3 ) {
            cadName.setError(REQUERID_MSG);
            Toast.makeText(this, "Informe seu nome", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CadName", cadName.getText().toString());
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
