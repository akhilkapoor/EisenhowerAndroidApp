/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.smith.kapoor.theeisenhowerwaytogtd.R;

/**
 * Creates the Color screen
 */  
public class ColorFragment extends Fragment {

    private Button saveButton, cancelButton;
    Spinner fgColor1, fgColor2, fgColor3, fgColor4;
    Spinner bgColor1, bgColor2, bgColor3, bgColor4;
    private int fsp1, fsp2, fsp3, fsp4;
    private int bsp1, bsp2, bsp3, bsp4;

    public static ColorFragment newInstance() {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color, container, false);

        getActivity().getActionBar().setTitle("Change colors");

        fgColor1 = (Spinner) v.findViewById(R.id.colorSelector1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        fgColor1.setAdapter(adapter1);
        fgColor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fsp1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fsp1 = 0;
            }
        });
        fgColor1.setSelection(colorToSelector(MainFragment.buttonText1));


        fgColor2 = (Spinner) v.findViewById(R.id.colorSelector2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        fgColor2.setAdapter(adapter2);
        fgColor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fsp2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fsp2 = 0;
            }
        });
        fgColor2.setSelection(colorToSelector(MainFragment.buttonText2));

        fgColor3 = (Spinner) v.findViewById(R.id.colorSelector3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        fgColor3.setAdapter(adapter3);
        fgColor3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fsp3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fsp3 = 0;
            }
        });
        fgColor3.setSelection(colorToSelector(MainFragment.buttonText3));

        fgColor4 = (Spinner) v.findViewById(R.id.colorSelector4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        fgColor4.setAdapter(adapter4);
        fgColor4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fsp4 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fsp4 = 0;
            }
        });
        fgColor4.setSelection(colorToSelector(MainFragment.buttonText4));


        bgColor1 = (Spinner) v.findViewById(R.id.colorSelector5);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        bgColor1.setAdapter(adapter5);
        bgColor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bsp1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bsp1 = 0;
            }
        });
        bgColor1.setSelection(colorToSelector(MainFragment.buttonColor1));

        bgColor2 = (Spinner) v.findViewById(R.id.colorSelector6);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        bgColor2.setAdapter(adapter6);
        bgColor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bsp2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bsp2 = 0;
            }
        });
        bgColor2.setSelection(colorToSelector(MainFragment.buttonColor2));

        bgColor3 = (Spinner) v.findViewById(R.id.colorSelector7);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        bgColor3.setAdapter(adapter7);
        bgColor3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bsp3 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bsp3 = 0;
            }
        });
        bgColor3.setSelection(colorToSelector(MainFragment.buttonColor3));

        bgColor4 = (Spinner) v.findViewById(R.id.colorSelector8);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(getActivity(), R.array.color_list, android.R.layout.simple_spinner_item);
        bgColor4.setAdapter(adapter8);
        bgColor4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bsp4 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bsp4 = 0;
            }
        });
        bgColor4.setSelection(colorToSelector(MainFragment.buttonColor4));

        saveButton = (Button) v.findViewById(R.id.chan_btn);

        cancelButton = (Button) v.findViewById(R.id.cancel_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainFragment.buttonColor1 = selectorToColor(bsp1);
                MainFragment.buttonColor2 = selectorToColor(bsp2);
                MainFragment.buttonColor3 = selectorToColor(bsp3);
                MainFragment.buttonColor4 = selectorToColor(bsp4);

                MainFragment.buttonText1 = selectorToColor(fsp1);
                MainFragment.buttonText2 = selectorToColor(fsp2);
                MainFragment.buttonText3 = selectorToColor(fsp3);
                MainFragment.buttonText4 = selectorToColor(fsp4);

//                Toast.makeText(getActivity(), "This feature doesn't quite work yet. Please come back later ;)", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        return v;
    }

    private int selectorToColor(int position) {
        switch (position) {
            case 0: return Color.BLACK;
            case 1: return Color.BLUE;
            case 2: return Color.CYAN;
            case 3: return Color.DKGRAY;
            case 4: return Color.GRAY;
            case 5: return Color.GREEN;
            case 6: return Color.LTGRAY;
            case 7: return Color.MAGENTA;
            case 8: return Color.RED;
            case 9: return Color.WHITE;
            case 10: return Color.YELLOW;
            default: return Color.LTGRAY;
        }
    }

    private int colorToSelector(int color) {
        switch (color) {
            case Color.BLACK: return 0;
            case Color.BLUE: return 1;
            case Color.CYAN: return 2;
            case Color.DKGRAY: return 3;
            case Color.GRAY: return 4;
            case Color.GREEN: return 5;
            case Color.LTGRAY: return 6;
            case Color.MAGENTA: return 7;
            case Color.RED: return 8;
            case Color.WHITE: return 9;
            case Color.YELLOW: return 10;
            default: return 3;
        }
    }

}
