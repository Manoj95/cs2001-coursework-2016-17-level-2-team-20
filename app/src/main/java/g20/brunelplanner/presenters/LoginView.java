package g20.brunelplanner.presenters;

import android.view.View;

public interface LoginView {

    void showDialog(View v);

    void hideDialog();

    void showError(View v, String e);

    void showTimetable();

}
