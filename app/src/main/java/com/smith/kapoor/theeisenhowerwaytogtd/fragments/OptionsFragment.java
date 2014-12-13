/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smith.kapoor.theeisenhowerwaytogtd.R;

/**
 * Creates the options menu
 */
public class OptionsFragment extends Fragment {

    private Button syncButton, disconnectButton, colorButton, backButton;

    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_options, container, false);

        getActivity().getActionBar().setTitle("Options");
        colorButton = (Button) v.findViewById(R.id.colors);
        backButton = (Button) v.findViewById(R.id.back);

        colorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.container, ColorFragment.newInstance()).addToBackStack(null).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return v;
    }
}
