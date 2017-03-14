package g20.brunelplanner.views.activities.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import g20.brunelplanner.R;
public class SettingsFragment extends Fragment {

    private int mSelectedColor, actionBarColour, BackGroundColour, CardsColour, FontColour, HeaderColour;

    @BindView(R.id.settings_actionbar_text_view)
    TextView actionbar_text_view;
    @BindView(R.id.settings_background_text_view)
    TextView background_text_view;
    @BindView(R.id.settings_cards_text_view)
    TextView cards_text_view;
    @BindView(R.id.settings_font_text_view)
    TextView font_text_view;
    @BindView(R.id.settings_header_text_view)
    TextView header_text_view;
    @BindView(R.id.settings_notification_text_view)
    TextView notification_text_view;

    @BindView(R.id.settings_actionbar_button)
    Button actionbar_button;
    @BindView(R.id.settings_background_button)
    Button background_button;
    @BindView(R.id.settings_cards_button)
    Button cards_button;
    @BindView(R.id.settings_font_button)
    Button font_button;
    @BindView(R.id.settings_header_button)
    Button loginButton;
    @BindView(R.id.store_settings)
    Button save_button;

    @BindView(R.id.settings_notification_checkbox)
    CheckBox notification_checkbox;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_settings, container, false);
        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");
        } catch (NullPointerException e) {
            Log.e("SettingsFragment", "onCreateView: ", e);
        }
        //ButterKnife.bind(this,view); <------ This doesn't work. Causes the app to crash if the settings option is chosen.
        //ButterKnife.bind(view); <----------- The app doesn't crash but the onclick methods don't work.

        return view;
    }



    public void ColourPicker(){ //<---This works.
        mSelectedColor = ContextCompat.getColor(getActivity().getBaseContext(), R.color.flamingo);
        int[] mColors = getResources().getIntArray(R.array.default_rainbow);
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                5, // Number of columns
                ColorPickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
            }
        });
        dialog.show((getActivity()).getFragmentManager(), "color_dialog_test");
    }


        @OnClick(R.id.store_settings)
        public void StoreSettings(View v) {
           saveIntPrefs("ActionBarColour", actionBarColour);
            saveIntPrefs("BackGroundColour", BackGroundColour);
            saveIntPrefs("CardColour", CardsColour);
            saveIntPrefs("FontColour", FontColour);
            saveIntPrefs("HeaderColour", HeaderColour);
        }

       @OnClick(R.id.settings_actionbar_button)
        public void ActionBarColour(View v) {
           ColourPicker();
           actionBarColour = mSelectedColor;
        }

        @OnClick(R.id.settings_background_button)
        public void BackgroundColour(View v) {
            ColourPicker();
            BackGroundColour = mSelectedColor;
        }

        @OnClick(R.id.settings_cards_button)
        public void CardsColour(View v) {
            ColourPicker();
            CardsColour = mSelectedColor;
        }

        @OnClick(R.id.settings_font_button)
        public void FontColour(View v) {
            ColourPicker();
            FontColour = mSelectedColor;
        }


    @OnClick(R.id.settings_header_button)
    public void HeaderColour(View v) {
        ColourPicker();
        HeaderColour = mSelectedColor;
    }



    private void saveIntPrefs(String ValueOfKey, int ValueToSave){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(ValueOfKey, ValueToSave);
        edit.commit();
    }

}
