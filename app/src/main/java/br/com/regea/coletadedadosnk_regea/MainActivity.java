package br.com.regea.coletadedadosnk_regea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remover
        Intent intent = new Intent(this, Cadastro.class);
        intent.putExtra("CadName", "Nome");
        startActivity(intent);
    }

    public void logCad (View view) {
        EditText cadName = findViewById(R.id.txt_CadName);

        if ( cadName.length() < 3 ) {
            Toast.makeText(this, "Informe seu nome", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, Cadastro.class);
        intent.putExtra("CadName", cadName.getText().toString());
        startActivity(intent);
    }
}
