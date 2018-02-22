package br.com.regea.coletadedadosnk_regea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment {

    public UsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_usuario, container, false);

        final ArrayList<View> viewArrayList = ((Cadastro) getActivity()).getAllChildren(view);
        ArrayList<String> ar = new ArrayList<String>();

        for (View v : viewArrayList) {
            if (v instanceof EditText) {
                ar.add(getResources().getResourceEntryName(v.getId()) + " TEXT");
            }
        }

        String[] campos = ar.toArray(new String[0]);

        DbController dbController = new DbController(getActivity());
        dbController.createDimTable("TB_USUARIO", campos);

        Button btn_Next = (Button) view.findViewById(R.id.btn_Next);
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return view;
    }

    public void saveData() {
        //EditText txtUsuario = (EditText) getActivity().findViewById(R.id.EMP_NM_USUARIO);

        //String insert = dbController.insertUser(txtUsuario.getText().toString());

        //Toast.makeText(getActivity(), insert, Toast.LENGTH_LONG).show();
    }
}
