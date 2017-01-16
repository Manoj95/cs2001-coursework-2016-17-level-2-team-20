package g20.brunelplanner.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import g20.brunelplanner.R;
import g20.brunelplanner.presenters.LoginPresenter;
import g20.brunelplanner.presenters.LoginView;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;
    private ProgressDialog progressDialog;

    @BindView(R.id.input_student_id) EditText studentId;
    @BindView(R.id.input_student_password) EditText studentPassword;
    // TODO: Use these to disppay field errors
    //@BindView(R.id.input_id_wrapper) TextInputLayout idWrapper;
    //@BindView(R.id.input_password_wrapper) TextInputLayout passwordWrapper;
    @BindView(R.id.btn_login) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm realm = Realm.getDefaultInstance();
        // TODO: Check this works on edge cases
        if (realm.isEmpty()) {
            realm.close();
            ButterKnife.bind(this);
            presenter = new LoginPresenter(this);
            presenter.onCreate();
        } else {
            realm.close();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        // Destroy the presenter
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void showDialog(View view) {
        progressDialog = new ProgressDialog(view.getContext(), R.style.DarkDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(View v, String e) {
        Snackbar snackbar = Snackbar.make(v, e, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.parseColor("#be1a2d"));
        snackbar.show();
    }

    @Override
    public void showTimetable() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_login)
    public void buttonLogin(View v) {
        presenter.authStudent(
                v,
                studentId.getText().toString(),
                studentPassword.getText().toString());
    }

}
