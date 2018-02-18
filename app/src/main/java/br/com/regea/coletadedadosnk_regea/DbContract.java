package br.com.regea.coletadedadosnk_regea;

import android.provider.BaseColumns;

/**
 * Created by Rodrigo on 18/02/2018.
 */

public class DbContract {

    private DbContract () {}

    public static class DbEntry implements BaseColumns {
        public static final String TB_USUARIO = "tb_usuario";
        public static final String USUARIO_NAME = "nome";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DbEntry.TB_USUARIO + " (" +
                    DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DbEntry.USUARIO_NAME + " TEXT NOT NULL);";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbEntry.TB_USUARIO;
}
