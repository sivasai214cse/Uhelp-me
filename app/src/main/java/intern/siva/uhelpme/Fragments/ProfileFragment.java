package intern.siva.uhelpme.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import intern.siva.uhelpme.Comun;
import intern.siva.uhelpme.Home1;
import intern.siva.uhelpme.Login;
import intern.siva.uhelpme.R;
import intern.siva.uhelpme.databinding.FragmentProfileBinding;
import io.paperdb.Paper;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

FragmentProfileBinding binding;
    AlertDialog writeBlogDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Paper.init(getContext());
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.UserPhoneNo.setText("+91-"+Paper.book().read("PhoneNo"));
        binding.Username.setText(Paper.book().read("UserName"));
        binding.UserEmail.setText(Paper.book().read("Email"));

        binding.UserType.setText(Paper.book().read("communi"));
        String usertype=Paper.book().read("communi");
        if(usertype.equals("LOCAL"))
        {
            binding.UserType.setText("Gunupuria");
        }
        else {
            binding.UserType.setText("Visitor");
        }

binding.Logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setCancelable(true)
                .setMessage("Delete permanently?")
                .setPositiveButton("Confirm", (dialog, which) -> {Paper.book().destroy();
                    Intent i=new Intent(getContext(), Comun.class);
                    startActivity(i);})
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .show();

    }
});


binding.Add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ((Home1)getContext()).Upload();
    }
});


binding.about.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View writeBlogDialogLayout = factory.inflate(R.layout.about, null);

        writeBlogDialog = new AlertDialog.Builder(getContext()).create();

        writeBlogDialog.setView(writeBlogDialogLayout);

        writeBlogDialog.show();

    }
});
        return binding.getRoot();
    }

}