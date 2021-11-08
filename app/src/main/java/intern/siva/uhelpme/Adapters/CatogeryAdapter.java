package intern.siva.uhelpme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import intern.siva.uhelpme.CatogeryActivity;
import intern.siva.uhelpme.Pojo.CatogeryPojo;
import intern.siva.uhelpme.R;

public class CatogeryAdapter extends RecyclerView.Adapter<CatogeryAdapter.CatogeryViewHolder> {
     ArrayList<CatogeryPojo>  catogeryPojos=new ArrayList<>();
     Context context;
    public CatogeryAdapter(Context context,ArrayList<CatogeryPojo>  catogeryPojos)
    {
        this.context=context;
        this.catogeryPojos=catogeryPojos;
    }

    @NonNull
    @Override
    public CatogeryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.catogery,null);
        return new CatogeryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatogeryViewHolder holder, int position) {
        CatogeryPojo catogary=catogeryPojos.get(position);
        holder.catogeryName.setText(catogary.getCatogeryName());
        holder.catogeryImage.setImageResource(catogary.getImageurl());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, CatogeryActivity.class);
                i.putExtra("Caotogery",catogary.getCatogeryName());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return catogeryPojos.size();
    }


    public class CatogeryViewHolder extends RecyclerView.ViewHolder
    {
          TextView catogeryName;
          ImageView catogeryImage;
          CardView layout;

        public CatogeryViewHolder(@NonNull View itemView) {
            super(itemView);
            catogeryName=itemView.findViewById(R.id.CatogeName);
            catogeryImage=itemView.findViewById(R.id.Catogeryimage);
            layout=itemView.findViewById(R.id.cardViewCatogery);


        }
    }
}
