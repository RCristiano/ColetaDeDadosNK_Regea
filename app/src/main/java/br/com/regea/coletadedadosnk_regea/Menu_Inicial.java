package br.com.regea.coletadedadosnk_regea;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu_Inicial extends Fragment {

    public Menu_Inicial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_init_menu, container, false);

        Button btn_New = (Button) view.findViewById(R.id.btn_NewCad);
        btn_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Cadastro)getActivity()).newCad();
            }
        });

        return view;
    }
}
