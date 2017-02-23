package g20.brunelplanner.views.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import g20.brunelplanner.R;

import static g20.brunelplanner.R.id.your_email;
import static g20.brunelplanner.R.id.your_message;
import static g20.brunelplanner.R.id.your_name;
import static g20.brunelplanner.R.id.your_subject;

public class ContactFragment extends Fragment {

    @BindView(your_name)
    EditText YourName;

    @BindView(your_email)
    EditText YourEmail;

    @BindView(your_subject)
    EditText YourSubject;

    @BindView(your_message)
    EditText YourMessage;

    @BindView(R.id.post_message)
    Button email;



    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this,view);

        // Inflate the layout for this fragment
        return view;

    }

    @OnClick(R.id.post_message)
    public void buttonPostMessage(View view){

        String name = YourName.getText().toString();
        String email = YourEmail.getText().toString();
        String subject = YourSubject.getText().toString();
        String message = YourMessage.getText().toString();

        if (TextUtils.isEmpty(name)){
            YourName.setError("Enter Your Name");
            YourName.requestFocus();
            return;
        }

        Boolean onError = false;
        if (!isValidEmail(email)) {
            onError = true;
            YourEmail.setError("Invalid Email");
            return;
        }

        if (TextUtils.isEmpty(subject)){
            YourSubject.setError("Enter Your Subject");
            YourSubject.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(message)){
            YourMessage.setError("Enter Your Message");
            YourMessage.requestFocus();
            return;
        }

        Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
        sendEmail.setType("plain/text");
        sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"1514850@my.brunel.ac.uk"});
        sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                "name:"+name+'\n'+"Email ID:"+email+'\n'+"Message:"+'\n'+message);

            /* Send it off to the Activity-Chooser */
        startActivity(Intent.createChooser(sendEmail, "Send mail..."));


    }


    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



}
