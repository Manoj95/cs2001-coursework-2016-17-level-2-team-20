package g20.brunelplanner.views.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import g20.brunelplanner.R;

public class ContactFragment extends Fragment {

    @BindView(R.id.contact_name_wrapper)
    TextInputLayout emailNameWrapper;
    @BindView(R.id.contact_name)
    EditText emailName;

    @BindView(R.id.contact_subject_wrapper)
    TextInputLayout emailSubjectWrapper;
    @BindView(R.id.contact_subject)
    EditText emailSubject;

    @BindView(R.id.contact_message_wrapper)
    TextInputLayout emailMessageWrapper;
    @BindView(R.id.contact_message)
    EditText emailMessage;

    @BindView(R.id.send_message)
    Button sendEmail;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;

    }

    @OnClick(R.id.send_message)
    public void buttonPostMessage(){

        String name = emailName.getText().toString();
        String subject = emailSubject.getText().toString();
        String message = emailMessage.getText().toString();

        if (TextUtils.isEmpty(name)){
            emailNameWrapper.setError("Enter Your Name");
            emailName.requestFocus();
            return;
        } else {
            emailNameWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(subject)){
            emailSubjectWrapper.setError("Enter Your Subject");
            emailSubject.requestFocus();
            return;
        } else {
            emailSubjectWrapper.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(message)){
            emailMessageWrapper.setError("Enter Your Message");
            emailMessage.requestFocus();
            return;
        } else {
            emailMessageWrapper.setErrorEnabled(false);
        }

        Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

        // Fill it with Data
        sendEmail.setType("plain/text");
        sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"1514850@my.brunel.ac.uk"});
        sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                "Name: " + name + "\nMessage:\n" + message);

        // Send it off to the activity chooser
        startActivity(Intent.createChooser(sendEmail, "Send Email"));

    }

}