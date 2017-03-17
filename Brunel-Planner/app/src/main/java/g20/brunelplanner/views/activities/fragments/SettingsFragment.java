package g20.brunelplanner.views.activities.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import butterknife.BindView;
import butterknife.OnClick;
import g20.brunelplanner.R;
import g20.brunelplanner.views.activities.MainActivity;

import static org.xdty.preference.colorpicker.ColorPickerDialog.newInstance;

//Importing the colour picker library.

public class SettingsFragment extends Fragment {

    private int mSelectedColor;

    //All the Default colour values of the app.
    private int DefaultActionBarColour = R.color.colorPrimary;
    private int DefaultBackGroundColour = R.color.cardview_light_background;
    private int DefaultHeaderColour = R.color.colorAccent;
    private int DefaultNavBodyColour = R.color.white;

    public int getDefaultActionBarColour() {
        return DefaultActionBarColour;
    }

    public int getDefaultNavBodyColour() {
        return DefaultNavBodyColour;
    }

    public int getDefaultBackGroundColour() {
        return DefaultBackGroundColour;
    }

    public int getDefaultHeaderColour() {
        return DefaultHeaderColour;
    }

    @BindView(R.id.settings_actionbar_text_view)
    TextView actionbar_text_view;
    @BindView(R.id.settings_background_text_view)
    TextView background_text_view;
    @BindView(R.id.settings_header_text_view)
    TextView header_text_view;
    @BindView(R.id.settings_body_text_view)
    TextView body_text_view;

    @BindView(R.id.settings_actionbar_button)
    Button actionbar_button;
    @BindView(R.id.settings_background_button)
    Button background_button;
    @BindView(R.id.settings_header_button)
    Button header_button;
    @BindView(R.id.settings_body_button)
    Button body_button;
    @BindView(R.id.store_settings)
    Button save_button;

    public SettingsFragment() {
        // ...
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @OnClick(R.id.store_settings)
    public void StoreSettings(View v) {
        Toast.makeText(getActivity(), "All Saved", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    @OnClick(R.id.settings_header_button)
    public void HeaderColour() {

        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultHeaderColour);
        int[] mColors = getResources().getIntArray(R.array.rainbow);

        ColorPickerDialog dialog = newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                4, // Number of columns
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
                saveColourPrefs("HeaderColour", mSelectedColor);
                header_text_view.setTextColor(loadColourPrefs("HeaderColour",DefaultHeaderColour));

            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_actionbar_button)
    public void ActionBarColour() {
        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultActionBarColour);
        int[] mColors = getResources().getIntArray(R.array.rainbow);

        ColorPickerDialog dialog = newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                4, // Number of columns
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
                saveColourPrefs("ActionBarColour", mSelectedColor);
                actionbar_text_view.setTextColor(loadColourPrefs("ActionBarColour",DefaultActionBarColour));

            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_background_button)
    public void BackgroundColour() {
        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultBackGroundColour);
        int[] mColors = getResources().getIntArray(R.array.rainbow);

        ColorPickerDialog dialog = newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                4, // Number of columns
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
                saveColourPrefs("BackGroundColour", mSelectedColor);
                background_text_view.setTextColor(loadColourPrefs("BackGroundColour",DefaultBackGroundColour));

            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_body_button)
    public void NavBodyColour() {
        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultNavBodyColour);
        int[] mColors = getResources().getIntArray(R.array.rainbow);

        ColorPickerDialog dialog = newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                4, // Number of columns
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
                saveColourPrefs("NavigationBodyColour", mSelectedColor);
                body_text_view.setTextColor(loadColourPrefs("NavigationBodyColour",DefaultNavBodyColour));
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    private void saveColourPrefs(String ValueOfKey, int ValueToSave){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(ValueOfKey, ValueToSave);
        edit.apply();
    }

    private int loadColourPrefs(String ValueOfKey, int DefaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return sharedPreferences.getInt(ValueOfKey, DefaultValue);
    }

}
