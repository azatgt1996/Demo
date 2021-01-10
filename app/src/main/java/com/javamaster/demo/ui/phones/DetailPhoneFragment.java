package com.javamaster.demo.ui.phones;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.javamaster.demo.CustomFragmentListener;
import com.javamaster.demo.FabButtonClick;
import com.javamaster.demo.MainActivity;
import com.javamaster.demo.R;
import com.javamaster.demo.model.Phone;


public class DetailPhoneFragment extends Fragment implements FabButtonClick, CustomFragmentListener {

    private EditText editText_phoneNumber;
    private TextView textView_phoneId;
    private Button deletePhone;
    private Button changePhone;
    private PhonesViewModel phonesViewModel;
    private Phone phone;
    private String oldPhoneNum;
    private Context mContext;
    private View parentLayout;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {

        view = inflater.inflate(R.layout.fragment_detail_phone, container, false);
        ((MainActivity)getActivity()).setListener(this);

        parentLayout = getActivity().findViewById(android.R.id.content).getRootView();
        phonesViewModel = ViewModelProviders.of(getActivity(), new PhonesViewModelFactory(getActivity().getApplication(), mContext, parentLayout)).get(PhonesViewModel.class);
        phonesViewModel.setListener(this);
//        phonesViewModel.openDb();

        editText_phoneNumber = view.findViewById(R.id.phoneNumber);
        textView_phoneId = view.findViewById(R.id.phoneId);

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
                    editText_phoneNumber.setEnabled(true);
                    oldPhoneNum = phone.getPhoneNumber();
                } else {

                    String phoneNum = editText_phoneNumber.getText().toString().trim();

                    if (!phoneNum.equals(oldPhoneNum)) {
                        phone.setPhoneNumber(phoneNum);
                        phonesViewModel.changeItem(phone);
                    } else {
                        changePhone.setText("Change");
                        editText_phoneNumber.setEnabled(false);
                    }
                }
            }
        });

        Bundle bundle = getArguments();

        if (bundle.getBoolean("add")) {
            deletePhone.setVisibility(View.INVISIBLE);
            changePhone.setVisibility(View.INVISIBLE);
            editText_phoneNumber.setEnabled(true);
        } else {
            int id = bundle.getInt("phoneId");
            String phoneNum = bundle.getString("phoneNumber");
            phone = new Phone(id, phoneNum);
            editText_phoneNumber.setText(phone.getPhoneNumber());
            deletePhone.setVisibility(View.VISIBLE);
            changePhone.setVisibility(View.VISIBLE);
            textView_phoneId.setText(String.valueOf(phone.getId()));
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
        int id = -1;
        String phoneNum = editText_phoneNumber.getText().toString().trim();
        phone = new Phone(id, phoneNum);

        phonesViewModel.addItem(phone);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        phonesViewModel.closeDb();
    }

    @Override
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void onCommitted() {
        changePhone.setText("Change");
        editText_phoneNumber.setEnabled(false);
//        getActivity().onBackPressed();
    }
}
