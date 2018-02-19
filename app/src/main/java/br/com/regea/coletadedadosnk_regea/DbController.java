package br.com.regea.coletadedadosnk_regea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rodrigo on 18/02/2018.
 */

public class DbController {

    private SQLiteDatabase sqLiteDatabase;
    private DbOpenHelper dbOpenHelper;

    public DbController(Context context) {
        dbOpenHelper = new DbOpenHelper(context);
    }

    public String insertUser(String name) {
        ContentValues valores = new ContentValues();
        long resultado;

        sqLiteDatabase = dbOpenHelper.getWritableDatabase();
        valores.put(DbContract.DbEntry.USUARIO_NAME, name);

        resultado = sqLiteDatabase.insert(DbContract.DbEntry.TB_USUARIO, null, valores);
        sqLiteDatabase.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor getUsuarios() {
        Cursor cursor;
        String[] campos = {DbContract.DbEntry._ID, DbContract.DbEntry.USUARIO_NAME};
        sqLiteDatabase = dbOpenHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(DbContract.DbEntry.TB_USUARIO, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }

}