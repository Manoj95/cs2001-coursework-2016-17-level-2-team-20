package g20.brunelplanner.views.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

    private static final String TAG = LoginActivity.class.getSimpleName();

    // Might not need to be composite
    private final CompositeDisposable disposables = new CompositeDisposable();

    @BindView(R.id.input_student_id) EditText studentId;
    @BindView(R.id.input_student_password) EditText studentPassword;
    @BindView(R.id.btn_login) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void actionLogin(final View view) {
        // show dialog
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.DarkDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        disposables.add(BrunelService.getTemporaryInstance()
                .authenticateStudent(studentId.getText().toString(),studentPassword.getText().toString())
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        // Move to Main
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(view, e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(Color.parseColor("#be1a2d"));
                        snackbar.show();
                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, " onNext value : " + value);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear(); // do not send event after activity has been destroyed
    }

}
