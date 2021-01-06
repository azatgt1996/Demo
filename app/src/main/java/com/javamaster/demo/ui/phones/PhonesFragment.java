package com.javamaster.demo.ui.phones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.javamaster.demo.FabButtonClick;
import com.javamaster.demo.MainActivity;
import com.javamaster.demo.R;
import com.javamaster.demo.model.Phone;
import com.javamaster.demo.model.PhoneAdapter;

import java.util.ArrayList;

public class PhonesFragment extends Fragment implements FabButtonClick {

    private PhonesViewModel phonesViewModel;
    ArrayList<Phone> phones;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        phonesViewModel = ViewModelProviders.of(getActivity()).get(PhonesViewModel.class);

        if (!phonesViewModel.isInitialized()) {
            phonesViewModel.setDbManager(mContext);
            phonesViewModel.openDb();
            phonesViewModel.init(getActivity(), mContext);
            phonesViewModel.closeDb();
        }

        View root = inflater.inflate(R.layout.fragment_phones, container, false);
        ((MainActivity)getActivity()).setListener(this);

        final ListView phonesList = root.findViewById(R.id.phonesList);

        phonesViewModel.getList().observe(getActivity(), new Observer<ArrayList<Phone>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Phone> list) {
                phones = list;
                Context context = mContext;
                PhoneAdapter phoneAdapter = new PhoneAdapter(context, R.layout.fragment_phone_item, phones);
                phonesList.setAdapter(phoneAdapter);
            }
        });

        phonesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phone phone = (Phone) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("phoneId", phone.getId());
                bundle.putString("phoneNumber", phone.getPhoneNumber());
                bundle.putBoolean("add", false);

                Navigation.findNavController(view).navigate(R.id.action_nav_phones_to_nav_phone_crud, bundle);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onFabClicked() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("add", true);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_phones_to_nav_phone_crud, bundle);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action).setVisible(true);
        menu.findItem(R.id.action).setTitle("Delete all");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(R.string.message_delete);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    phonesViewModel.openDb();
                    phonesViewModel.deleteAll();
                    phonesViewModel.closeDb();
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}