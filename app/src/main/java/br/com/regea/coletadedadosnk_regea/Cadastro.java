package br.com.regea.coletadedadosnk_regea;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    UsuarioFragment usuarioFragment = new UsuarioFragment();
    PropriedadeFragment propriedadeFragment = new PropriedadeFragment();
    PontoFragment pontoFragment = new PontoFragment();
    Menu_Inicial menu_inicial = new Menu_Inicial();

    LocationListener locationListener;
    LocationManager locationManager;
    Location bestLocation;

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

        Toast.makeText(this, "Cadastro finalizado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Acessando sinal GPS", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Necessário acesso ao GPS", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void requestLocation (View view) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return;
            }

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Ajustando posição GPS");
            progress.setMessage("Aguarde!");
            progress.setCancelable(false);
            progress.setButton(DialogInterface.BUTTON_POSITIVE, "Registrar posição", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    TextView txt_latitude = findViewById(R.id.INT_CR_LATITUDE);
                    TextView txt_longitude = findViewById(R.id.INT_CR_LONGITUDE);
                    txt_latitude.setText( String.format("%s", bestLocation.getLatitude()) );
                    txt_longitude.setText( String.format("%s", bestLocation.getLongitude()) );

                    locationManager.removeUpdates(locationListener);
                }
            });
            progress.show();
            progress.getButton(ProgressDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                    //Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderEnabled(String arg0) {
                    //Toast.makeText(getApplicationContext(), "Provider habilitado", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderDisabled(String arg0) {
                    Toast.makeText(getApplicationContext(), "Ative o GPS", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1);
                }

                @Override
                public void onLocationChanged(Location location) {
                    setLocation(location, progress);
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);

        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setLocation(Location location, ProgressDialog progressDialog) {
        if ( location != null ) {
            if ( bestLocation != null ) {
                if ( bestLocation.getAccuracy() > location.getAccuracy() )
                    return;
            }

            bestLocation = location;

            progressDialog.setMessage("Precisão: " + location.getAccuracy());
            progressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        }
    }
}
