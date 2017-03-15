package g20.brunelplanner.views.activities.fragments;


import android.content.Intent;
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
import android.widget.Toast;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import g20.brunelplanner.R;
import g20.brunelplanner.views.activities.MainActivity;

import static org.xdty.preference.colorpicker.ColorPickerDialog.newInstance;

public class SettingsFragment extends Fragment {

    
    private int mSelectedColor;

    //All the Default colour values of the app.
    private int DefaultActionBarColour = R.color.colorPrimary;
    private int DefaultBackGroundColour = R.color.cardview_light_background;
    private int DefaultCardsColour = R.color.cardview_light_background;
    private int DefaultFontColour = R.color.colorPrimaryDark;
    private int DefaultHeaderColour = R.color.colorAccent;
    //the default value of the notification checkbox.
    private boolean DefaultNotificationChose = false;

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


    @OnCheckedChanged(R.id.settings_notification_checkbox)
    void onChecked(boolean checked){
        saveNotificationPrefs("Notification", checked);
        Toast.makeText(getActivity(), checked ? "Checked!" : "Unchecked!",Toast.LENGTH_SHORT).show();
    }


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
        ButterKnife.bind(this,view);
        LoadPreviousPrefs();
        return view;
    }


            @OnClick(R.id.store_settings)
    public void StoreSettings(View v) {
        Toast.makeText(getActivity(), "All Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
    }


    @OnClick(R.id.settings_header_button)
    public void HeaderColour(View v) {

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
                header_text_view.setTextColor(mSelectedColor);
                saveColourPrefs("HeaderColour", mSelectedColor);
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_actionbar_button)
    public void ActionBarColour(View v) {
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
                actionbar_text_view.setTextColor(mSelectedColor);
                saveColourPrefs("ActionBarColour", mSelectedColor);
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_background_button)
    public void BackgroundColour(View v) {
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
                background_text_view.setTextColor(mSelectedColor);
                saveColourPrefs("BackGroundColour", mSelectedColor);
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_cards_button)
    public void CardsColour(View v) {
        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultCardsColour);
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
                cards_text_view.setTextColor(mSelectedColor);
                saveColourPrefs("CardsColour", mSelectedColor);
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }

    @OnClick(R.id.settings_font_button)
    public void FontColour(View v) {
        mSelectedColor = ContextCompat.getColor(getActivity(), DefaultFontColour);
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
                font_text_view.setTextColor(mSelectedColor);
                saveColourPrefs("FontColour", mSelectedColor);
            }

        });

        dialog.show(getActivity().getFragmentManager(), "color_dialog_test");

    }




    private void saveColourPrefs(String ValueOfKey, int ValueToSave){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(ValueOfKey, ValueToSave);
        edit.commit();
    }
    private void saveNotificationPrefs(String ValueOfKey, Boolean ValueToSave){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(ValueOfKey, ValueToSave);
        edit.commit();
    }
    private int loadColourPrefs(String ValueOfKey, int DefaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(getActivity());
        int StoredColour = sharedPreferences.
                getInt(ValueOfKey, DefaultValue);
        return StoredColour;

    }
    private boolean loadNotificationPrefs(String ValueOfKey, boolean DefaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(getActivity());
        boolean StoredColour = sharedPreferences.
                getBoolean(ValueOfKey, DefaultValue);
        return StoredColour;

    }


    public void LoadPreviousPrefs(){
        int PreHeaderColour = loadColourPrefs("HeaderColour",DefaultHeaderColour);
        header_text_view.setTextColor(PreHeaderColour);

        int PreFontColour = loadColourPrefs("FontColour",DefaultFontColour);
        font_text_view.setTextColor(PreFontColour);

        int PreBackGroundColour = loadColourPrefs("BackGroundColour",DefaultBackGroundColour);
        background_text_view.setTextColor(PreBackGroundColour);

        int PreActionBarColour = loadColourPrefs("ActionBarColour",DefaultActionBarColour);
        actionbar_text_view.setTextColor(PreActionBarColour);

        int PreCardsColour = loadColourPrefs("CardsColour",DefaultCardsColour);
        cards_text_view.setTextColor(PreCardsColour);

        boolean PreNotificationChose = loadNotificationPrefs("Notification",DefaultNotificationChose);
        notification_checkbox.setChecked(PreNotificationChose);
    }
    public int getmSelectedColor() {
        return mSelectedColor;
    }

    public int getDefaultActionBarColour() {
        return DefaultActionBarColour;
    }

    public int getDefaultBackGroundColour() {
        return DefaultBackGroundColour;
    }

    public int getDefaultCardsColour() {
        return DefaultCardsColour;
    }

    public int getDefaultFontColour() {
        return DefaultFontColour;
    }

    public int getDefaultHeaderColour() {
        return DefaultHeaderColour;
    }

    public boolean isDefaultNotificationChose() {
        return DefaultNotificationChose;
    }


}
