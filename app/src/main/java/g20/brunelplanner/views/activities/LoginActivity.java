package g20.brunelplanner.views.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import g20.brunelplanner.R;
import g20.brunelplanner.network.BrunelService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    // Gets the current name of the activity, used for debugging
    private static final String TAG = LoginActivity.class.getSimpleName();

    // Might not need to be composite
    private final CompositeDisposable disposables = new CompositeDisposable();

    // Bind the views
    @BindView(R.id.input_student_id) EditText studentId;
    @BindView(R.id.input_student_password) EditText studentPassword;
    @BindView(R.id.input_id_wrapper) TextInputLayout idWrapper;
    @BindView(R.id.input_password_wrapper) TextInputLayout passwordWrapper;
    @BindView(R.id.btn_login) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    // When button is clicked
    @OnClick(R.id.btn_login)
    public void actionLogin(final View view) {

        // Check if the input is blank
        if (!validateInput()) {
            // Exit the method
            return;
        }

        // Show dialog while doing networking
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.DarkDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Add to disposables
        disposables.add(BrunelService.getInstance()
                // Link to the Observable and pass through is and password
                .authenticateStudent(studentId.getText().toString(), studentPassword.getText().toString())
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Create Observer
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        // Move to Main
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Show errors from networking
                        Log.e(TAG, e.getMessage(), e);
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(view, e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(Color.parseColor("#be1a2d"));
                        snackbar.show();
                    }

                    @Override
                    public void onNext(String value) {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(view, value, Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(Color.parseColor("#00E676"));
                        snackbar.show();
                    }
                }));
    }

    // Input is validated here
    public boolean validateInput() {
        boolean isValid = true;

        // Check if student id input is empty
        if (studentId.getText().toString().isEmpty()) {
            idWrapper.setError("Please enter a Student ID");
            isValid = false;
        } else {
            // Hide error
            idWrapper.setErrorEnabled(false);
        }

        // Check if student password is is empty
        if (studentPassword.getText().toString().isEmpty()) {
            passwordWrapper.setError("Please enter a Password");
            isValid = false;
        } else {
            passwordWrapper.setErrorEnabled(false);
        }

        return isValid;
    }

    @Override
    protected void onDestroy() {
        // This might not be the right order
        super.onDestroy();
        // Do not send event after activity has been destroyed
        disposables.clear();
    }

}
