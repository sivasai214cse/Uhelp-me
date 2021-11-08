package intern.siva.uhelpme.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import intern.siva.uhelpme.Adapters.CatogeryAdapter;
import intern.siva.uhelpme.Pojo.CatogeryPojo;
import intern.siva.uhelpme.R;
import intern.siva.uhelpme.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

FragmentHomeBinding binding;
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ArrayList<CatogeryPojo> catogery = new ArrayList<>();
        CatogeryAdapter catogeryAdapter = new CatogeryAdapter(getContext(), catogery);

    catogery.add(new CatogeryPojo(R.drawable.food, "Groceries"));
    catogery.add(new CatogeryPojo(R.drawable.img, "Resturants"));
    catogery.add(new CatogeryPojo(R.drawable.hotels, "Hotels"));
    catogery.add(new CatogeryPojo(R.drawable.cafe, "Cafe"));
    catogery.add(new CatogeryPojo(R.drawable.saloon, "Saloon"));
    catogery.add(new CatogeryPojo(R.drawable.bakery, "Bakery"));
    catogery.add(new CatogeryPojo(R.drawable.fashion, "Fashions"));
    catogery.add(new CatogeryPojo(R.drawable.chemist, "Chemist"));
    catogery.add(new CatogeryPojo(R.drawable.stationary, "Stationaries"));
    catogery.add(new CatogeryPojo(R.drawable.footwears, "Footwears"));
      //  "Hotels", "Resturants", "Cafe", "Bakery", "Groceries", "Fashions", "Footwears","Stationaries","Saloon","Chemist")

        binding.CatogeryRecyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));

        //vertical Recylerview
//       binding.CatogeryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.CatogeryRecyclerview.setAdapter(catogeryAdapter);
binding.wallet.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
                AlertDialog dialog ;
                LayoutInflater factory = LayoutInflater.from(getContext());
                View writeBlogDialogLayout = factory.inflate(R.layout.comming, null);
                dialog = new AlertDialog.Builder(getContext()).create();
                dialog.setView(writeBlogDialogLayout);
                dialog.show();
    }
});
        return binding.getRoot();
    }
}