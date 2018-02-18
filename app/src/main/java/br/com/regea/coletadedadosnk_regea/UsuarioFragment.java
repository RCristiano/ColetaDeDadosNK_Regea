package br.com.regea.coletadedadosnk_regea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


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

        Button btn_Next = (Button) view.findViewById(R.id.btn_Next);
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(view);
            }
        });

        return view;
    }

    public void saveData(View view) {
        EditText txtUsuario = (EditText) getActivity().findViewById(R.id.EMP_NM_USUARIO);

        DbController dbController = new DbController(getActivity());
        String teste = dbController.insertUser(txtUsuario.getText().toString());

        Toast.makeText(getActivity(), teste, Toast.LENGTH_LONG).show();
    }
}
