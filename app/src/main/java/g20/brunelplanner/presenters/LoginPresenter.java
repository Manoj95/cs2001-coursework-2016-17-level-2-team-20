package g20.brunelplanner.presenters;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;

import g20.brunelplanner.controllers.networks.BrunelService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private static boolean studentValid = false;
    private LoginView loginView;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public LoginPresenter(LoginView view) {
        this.loginView = view;
    }

    private void validate() {
        // If login succeeded, move onto the main activity
        if (studentValid) {
            loginView.showTimetable();
        } else {
            Log.d(TAG, "Account Error");
        }
    }

    public void authStudent(final View v, final String studentId, final String studentPassword) {
        // Show the progress dialog while attempting login
        loginView.showDialog(v);
        // TODO: Dispose after a successful login
        disposables.add(BrunelService.getInstance()
                // Link to the Observable and pass through is and password
                .getTimetable(studentId, studentPassword)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Create Observer
                .subscribeWith(new DisposableObserver<JSONArray>() {
                    @Override
                    public void onComplete() {
                        loginView.hideDialog();
                        validate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.hideDialog();
                        loginView.showError(v, e.getMessage());
                    }

                    @Override
                    public void onNext(JSONArray value) {
                        // If the value obtained is not null then the user has logged in
                        if (value != null) {
                            studentValid = true;
                        }
                    }
                }));
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        // Might not be the correct way to discard this
        this.loginView = null;
        disposables.clear();
    }

    @Override
    public void onPause() {

    }
}
