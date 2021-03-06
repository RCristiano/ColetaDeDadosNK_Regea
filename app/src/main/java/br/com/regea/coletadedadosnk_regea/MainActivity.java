package br.com.regea.coletadedadosnk_regea;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_GPS_PERMISSION = 332;
    public static final UsuarioFragment usuarioFragment = new UsuarioFragment();
    public static final PropriedadeFragment propriedadeFragment = new PropriedadeFragment();
    public static final PontoFragment pontoFragment = new PontoFragment();
    public static final Menu_Inicial menu_inicial = new Menu_Inicial();
    public static final Listar_CadastrosFragment listarCadastrosFragment = new Listar_CadastrosFragment();
    private NavigationView navigationView;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Location bestLocation = null;
    private SharedPreferences sharedpreferences;

    public static ArrayList<View> getAllChildren(View v) {

        if (v instanceof Spinner || !(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {

            View child = vg.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, menu_inicial).commit();
        }

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

        sharedpreferences = getSharedPreferences(LauncherActivity.MY_SHAREDPREF, MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (backStackEntryCount < 1) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastro, menu);

        TextView txt_CadName = (TextView) findViewById(R.id.lbl_CadName);

        String name = sharedpreferences.getString("CadName", null);

        if (name != null) {
            txt_CadName.setText(name);
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
        if (id == R.id.close_app) {
            moveTaskToBack(true);
            return true;
        } else if (id == R.id.logoff) {
            cadLogOff();
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
            listCad();
        } else if (id == R.id.nav_send) {
            sendAllCads();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cadLogOff() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void newCad() {
        Menu menu = navigationView.getMenu();
        menu.setGroupVisible(R.id.group_Cad, true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, usuarioFragment).commit();
    }

    public void cadUser() {
        // TODO Remover stub
        Bundle args = new Bundle();
        args.putInt("someInt", 10);
        getIntent().putExtras(args);

        usuarioFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, usuarioFragment).commit();
    }

    public void cadProp() {
        propriedadeFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, propriedadeFragment).commit();
    }

    public void cadPonto() {
        pontoFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, pontoFragment).commit();
    }

    public void saveCad() {
        Menu menu = navigationView.getMenu();
        menu.setGroupVisible(R.id.group_Cad, false);

        Toast.makeText(this, "MainActivity finalizado", Toast.LENGTH_LONG).show();

        // recreate() não limpou stack e manteve savedBundle em onCreate()
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void listCad() {
        listarCadastrosFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, listarCadastrosFragment).commit();
    }

    public void sendAllCads() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GPS_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Acesso permitido ao GPS", Toast.LENGTH_LONG).show();
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

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS_PERMISSION);

                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final AlertDialog progress = builder.create();
            progress.setTitle("Ajustando posição GPS");
            progress.setMessage("Aguarde!");
            progress.setCancelable(false);
            progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            locationManager.removeUpdates(locationListener);
                        }
                    });
            progress.setButton(DialogInterface.BUTTON_POSITIVE, "Registrar posição",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TextView txt_longitude = findViewById(R.id.INT_CR_LONGITUDE);
                            TextView txt_latitude = findViewById(R.id.INT_CR_LATITUDE);
                            txt_latitude.setText( String.format("%s", bestLocation.getLatitude()) );
                            txt_longitude.setText( String.format("%s", bestLocation.getLongitude()) );

                            locationManager.removeUpdates(locationListener);
                        }
                    });
            progress.show();
            progress.getButton(ProgressDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if ( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                Toast.makeText(getApplicationContext(), "Ative o GPS", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, REQUEST_GPS_PERMISSION);
            }

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
                    startActivityForResult(intent, REQUEST_GPS_PERMISSION);
                }

                @Override
                public void onLocationChanged(Location location) {
                    setLocation(location, progress);
                }
            };

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);

            locationManager.requestLocationUpdates(1000, 0, criteria, locationListener, null);

        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setLocation(Location location, AlertDialog alertDialog) {
        if (location != null) {
            if (bestLocation == null) {
                bestLocation = location;
            }

            if (bestLocation.getAccuracy() > location.getAccuracy()) {
                bestLocation = location;
            }

            alertDialog.setMessage(String.format("Precisão: %s metros", bestLocation.getAccuracy()));
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
        }
    }

    public boolean createTableFromView(String TAB_NAME, View view) {
        try {
            final ArrayList<View> viewArrayList = getAllChildren(view);
            final ArrayList<String> ar = new ArrayList<String>();

            for (View v : viewArrayList) {
                if (v.getId() != View.NO_ID && (v instanceof Spinner || v instanceof EditText)) {
                    ar.add(getResources().getResourceEntryName(v.getId()) + " TEXT");
                }
            }

            String[] campos = ar.toArray(new String[0]);

            DbController dbController = new DbController(this);
            dbController.createDimTable(TAB_NAME, campos);

            return true;
        } catch (Exception e) {
            Log.e("createTableFromView", "=> " + e.getMessage(), e);
            return false;
        }
    }

}