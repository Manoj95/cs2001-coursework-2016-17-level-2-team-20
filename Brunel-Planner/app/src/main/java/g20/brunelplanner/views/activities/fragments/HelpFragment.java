package g20.brunelplanner.views.activities.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import g20.brunelplanner.R;

public class HelpFragment extends Fragment {

    @BindView(R.id.open_contact_fragment)
    Button open_contact;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.open_contact_fragment)
    public void openContact() {
        ContactFragment nextFrag = new ContactFragment();
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, nextFrag, "Contact Us")
                .addToBackStack(null)
                .commit();
    }

}
