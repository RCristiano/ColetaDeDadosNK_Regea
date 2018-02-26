package br.com.regea.coletadedadosnk_regea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropriedadeFragment extends Fragment {

    private static final String TAB_NAME = "TAB_PROPRIEDADE";
    private static boolean hasTable = false;

    public PropriedadeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_propriedade, container, false);

        getActivity().setTitle(R.string.title_propriedade);

        if (!hasTable)
            hasTable = ((MainActivity) getActivity()).createTableFromView(TAB_NAME, view);

        return view;
    }

}
