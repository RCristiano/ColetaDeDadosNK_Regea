package br.com.regea.coletadedadosnk_regea;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listar_CadastrosFragment extends Fragment {

    private ListView lista;

    public Listar_CadastrosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_cadastros, container, false);

        DbController crud = new DbController(getContext());
        Cursor cursor = crud.getUsuarios();

        String[] nomeCampos = new String[]{DbContract.DbEntry._ID, DbContract.DbEntry.USUARIO_NAME};
        int[] idViews = new int[]{R.id.item_id, R.id.userName};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getContext(),
                R.layout.list_itens, cursor, nomeCampos, idViews, 0);
        lista = (ListView) view.findViewById(R.id.list_Cad);
        lista.setAdapter(adaptador);

        return view;
    }

}
