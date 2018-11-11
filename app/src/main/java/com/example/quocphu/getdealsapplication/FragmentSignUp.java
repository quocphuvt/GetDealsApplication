package com.example.quocphu.getdealsapplication;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSignUp extends Fragment {
    Button btn_signUpGoogle,btn_signUpAdmin;

    public FragmentSignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment_sign_up, container, false);
        btn_signUpGoogle = view.findViewById(R.id.btn_signUpGoogle);
        btn_signUpAdmin = view.findViewById(R.id.btn_signUpAdmin);
        btn_signUpAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setView(getLayoutInflater().inflate(R.layout.dialog_admin,null));
                View view2 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_admin,null);
                AlertDialog dialog = dialogBuilder.create();

                dialog.show();
                Button btn_confirm = view2.findViewById(R.id.btn_confirm);
                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        return view;
    }

}
