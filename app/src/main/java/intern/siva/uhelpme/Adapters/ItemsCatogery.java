package intern.siva.uhelpme.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import intern.siva.uhelpme.CatogeryActivity;
import intern.siva.uhelpme.Home1;
import intern.siva.uhelpme.ItemDetailedActivity;
import intern.siva.uhelpme.Pojo.ItemsCatogeryPojo;
import intern.siva.uhelpme.R;

public class ItemsCatogery extends RecyclerView.Adapter<ItemsCatogery.ItemsCatogeryViewHolder> {

    Context context;
    ArrayList<ItemsCatogeryPojo> itemsCatogeries;
    public  ItemsCatogery(Context context,ArrayList<ItemsCatogeryPojo> itemsCatogeries)
    {
        this.context=context;
        this.itemsCatogeries=itemsCatogeries;

    }
    @NonNull
    @Override
    public ItemsCatogeryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.detailedcatogery,null);
        return new ItemsCatogeryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsCatogeryViewHolder holder, int position)
    {
        String typek;

        ItemsCatogeryPojo catogery=itemsCatogeries.get(position);


            holder.ItemName.setText(catogery.getName());
            holder.ItemRatings.setText("4.5");
            holder.ItemAddress.setText(catogery.getAddressLin1() + " ," + catogery.getLandmark() + " \n" + catogery.getPhoneNo());
            Glide.with(context)
                    .load(catogery.getImage())
                    .into(holder.Item);

            holder.Rating.setImageResource(android.R.drawable.btn_star_big_on);
            holder.Catogeryname.setText(catogery.getCatogerType());

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog dialog ;
//                LayoutInflater factory = LayoutInflater.from(context);
//                View writeBlogDialogLayout = factory.inflate(R.layout.sucessful, null);
//                dialog = new AlertDialog.Builder(context).create();
//                dialog.setView(writeBlogDialogLayout);
//                dialog.show();
//            }
//        });

    }
    public String type(String type)
    {
        return type;
    }

    @Override
    public int getItemCount() {
        return itemsCatogeries.size();
    }

    public class ItemsCatogeryViewHolder extends RecyclerView.ViewHolder{
        ImageView Item,Rating;
        TextView ItemName,ItemAddress,ItemRatings,Catogeryname;
        CardView layout;


        public ItemsCatogeryViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName =itemView.findViewById(R.id.CatogeryItem);
            ItemAddress =itemView.findViewById(R.id.ItemAddress);
            ItemRatings=itemView.findViewById(R.id.Ratings);
            Item=itemView.findViewById(R.id.Catogeryimage);
            Rating=itemView.findViewById(R.id.Ratingimage);
            Catogeryname=itemView.findViewById(R.id.CatogeName);
            layout=itemView.findViewById(R.id.cardViewCatogery);

            }


        }
    }

