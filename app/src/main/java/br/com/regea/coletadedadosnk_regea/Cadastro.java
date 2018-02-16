package br.com.regea.coletadedadosnk_regea;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.cert.PolicyNode;

public class Cadastro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    UsuarioFragment usuarioFragment = new UsuarioFragment();
    PropriedadeFragment propriedadeFragment = new PropriedadeFragment();
    PontoFragment pontoFragment = new PontoFragment();
    Menu_Inicial menu_inicial = new Menu_Inicial();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, menu_inicial).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastro, menu);

        Button btnCadName = findViewById(R.id.btn_CadName);

        Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey("CadName")) {
            String userName = bundle.getString("CadName");

            btnCadName.setText(userName);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            cadUser();
        } else if (id == R.id.nav_prop) {
            cadProp();
        } else if (id == R.id.nav_ponto) {
            cadPonto();
        } else if (id == R.id.nav_save) {
            saveCad();
        } else if (id == R.id.nav_New) {
            newCad();
        } else if (id == R.id.nav_List) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void backToLog(View view) {
        super.onBackPressed();
    }

    public void newCad() {
        Menu menu = navigationView.getMenu();
        menu.setGroupVisible(R.id.group_Cad, true);

        cadUser();
    }

    public void cadUser() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, usuarioFragment).commit();
    }

    public void cadProp() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, propriedadeFragment).commit();
    }

    public void cadPonto() {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, pontoFragment).commit();
    }

    public void saveCad() {
        Menu menu = navigationView.getMenu();
        menu.setGroupVisible(R.id.group_Cad, false);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, menu_inicial).commit();

        Toast.makeText(this, "Cadostro finalizado", Toast.LENGTH_LONG).show();
    }
}
