package intern.siva.uhelpme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import intern.siva.uhelpme.Adapters.ItemsCatogery;
import intern.siva.uhelpme.Pojo.CatogeryPojo;
import intern.siva.uhelpme.Pojo.ItemsCatogeryPojo;
import intern.siva.uhelpme.Pojo.Post;
import intern.siva.uhelpme.Pojo.Users;
import intern.siva.uhelpme.databinding.ActivityCatogeryBinding;

public class CatogeryActivity extends AppCompatActivity {

    ActivityCatogeryBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private Query query;
    String selectedItem;
    private ListenerRegistration listenerRegistration;
    List<Users> usersList;
    ArrayList<ItemsCatogeryPojo> list;
    String currentuserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCatogeryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        selectedItem=getIntent().getStringExtra("Caotogery");
        String CatogeryType=getIntent().getStringExtra("Caotogery");
        //Toast.makeText(CatogeryActivity.this, ""+CatogeryType, Toast.LENGTH_SHORT).show();
        ArrayList<ItemsCatogeryPojo> catogery = new ArrayList<>();
        currentuserId = "zfuWpY0wK7hNwqDfyn75dhhVvZL2";
        list = new ArrayList<ItemsCatogeryPojo>();
        usersList = new ArrayList<>();
        ItemsCatogery Adapter = new ItemsCatogery(getApplicationContext(),list );




        if (currentuserId!= null){

            query = firestore.collection(selectedItem).orderBy("time" , Query.Direction.DESCENDING);
            listenerRegistration = query.addSnapshotListener(CatogeryActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for (DocumentChange doc : value.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){
                            String postId = doc.getDocument().getId();
//                            Toast.makeText(getApplicationContext(), ""+postId, Toast.LENGTH_SHORT).show();
                            ItemsCatogeryPojo post = doc.getDocument().toObject(ItemsCatogeryPojo.class).withId(postId);
                            String postUserId = doc.getDocument().getString("user");
                            Log.d("REsp",postId);
                            firestore.collection(selectedItem).document(postUserId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()){
                                                Users users = task.getResult().toObject(Users.class);
                                                usersList.add(users);
                                                list.add(post);
                                                Adapter.notifyDataSetChanged();


                                                binding.CatogeryTypeRecylerview.setLayoutManager(new LinearLayoutManager(CatogeryActivity.this));
                                                binding.CatogeryTypeRecylerview.setAdapter(Adapter);
                                            }else{
                                                Toast.makeText(CatogeryActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
                            Adapter.notifyDataSetChanged();
                        }
                    }
                    listenerRegistration.remove();
                }
            });

        }




//        for(int i=1;i<6;i++) {
//            catogery.add(new ItemsCatogeryPojo( "Green chilli ","4.5","Gunupur",CatogeryType));
//            catogery.add(new ItemsCatogeryPojo("Riyans Resturant","2.5","Gunupur",CatogeryType));
//            catogery.add(new ItemsCatogeryPojo("Pakwan Resturant","4.5","Gunupur",CatogeryType));
//
//        }
       // binding.CatogeryTypeRecylerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        //vertical Recylerview

    }
}
