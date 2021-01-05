package com.javamaster.demo.ui.phones;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.javamaster.demo.FabButtonClick;
import com.javamaster.demo.MainActivity;
import com.javamaster.demo.R;
import com.javamaster.demo.ui.phones.PhonesViewModel;


public class DetailPhoneFragment extends Fragment implements FabButtonClick {

    private EditText editText;
    private Button deletePhone;
    private Button changePhone;
    private PhonesViewModel phonesViewModel;
    private String phone;
    private String changedPhone;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.fragment_detail_phone, container, false);
        ((MainActivity)getActivity()).setListener(this);
        phonesViewModel = ViewModelProviders.of(getActivity()).get(PhonesViewModel.class);
        phonesViewModel.openDb();

        editText = view.findViewById(R.id.phoneNumber);

        deletePhone = view.findViewById(R.id.button_deletePhone);
        changePhone = view.findViewById(R.id.button_changePhone);

        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonesViewModel.deleteItem(phone);
                getActivity().onBackPressed();
            }
        });

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changePhone.getText().toString().equals("Change")) {
                    changePhone.setText("Save");
                    editText.setEnabled(true);
                } else {
                    changePhone.setText("Change");
                    editText.setEnabled(false);
                    changedPhone = editText.getText().toString();
                    if (!phonesViewModel.changeItem(phone, changedPhone)) {
                        Toast.makeText(mContext, "This phone is exists!", Toast.LENGTH_LONG).show();
                        editText.setText(phone);
                    }
                }
            }
        });

        Bundle bundle = getArguments();

        if (bundle.getBoolean("add")) {
            deletePhone.setVisibility(View.INVISIBLE);
            changePhone.setVisibility(View.INVISIBLE);
            editText.setEnabled(true);
        } else {
            phone = bundle.getString("phone");
            editText.setText(phone);
            deletePhone.setVisibility(View.VISIBLE);
            changePhone.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onFabClicked() {
        phone = editText.getText().toString();
        if (!phonesViewModel.addItem(phone)) {
            Toast.makeText(mContext, "This phone is exists!", Toast.LENGTH_LONG).show();
        }
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        phonesViewModel.closeDb();
    }
}
