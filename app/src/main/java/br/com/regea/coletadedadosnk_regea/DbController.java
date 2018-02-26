package br.com.regea.coletadedadosnk_regea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Rodrigo on 18/02/2018.
 */

public class DbController implements DbContract {

    private SQLiteDatabase sqLiteDatabase;
    private DbOpenHelper dbOpenHelper;

    public DbController(Context context) {
        dbOpenHelper = new DbOpenHelper(context);
    }

    public String insertUser(String name) {
        ContentValues valores = new ContentValues();
        long resultado;

        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        valores.put(DbEntry.USUARIO_NAME, name);

        resultado = sqLiteDatabase.insert(DbEntry.TB_USUARIO, null, valores);
        sqLiteDatabase.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor getUsuarios() {
        Cursor cursor;
        String[] campos = {DbEntry._ID, DbEntry.USUARIO_NAME};
        try {
            sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(DbEntry.TB_USUARIO, campos, null, null, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }
            sqLiteDatabase.close();
            return cursor;
        } catch (Exception e) {
            Log.e("TAG", "=>", e);
        }
        return null;
    }

    public void createDimTable(final String TB_NAME, String[] campos) {
        sqLiteDatabase = dbOpenHelper.getWritableDatabase();

        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TB_NAME + " (" +
                DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT";

        StringBuilder sql = new StringBuilder(sqlCreate);

        for (String campo : campos) {
            sql.append(", ").append(campo);
        }

        sql.append(");");

        sqLiteDatabase.execSQL(sql.toString());

        sqLiteDatabase.close();
    }

    // TODO Implementar função para exportar DB

    /*private void exportDB(Context context) {

        File dbFile = context.getDatabasePath("MyDBName.db");
        DbOpenHelper dbhelper = new DbOpenHelper(context.getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "csvname.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM contacts", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("LauncherActivity", sqlEx.getMessage(), sqlEx);
        }
    }*/

}