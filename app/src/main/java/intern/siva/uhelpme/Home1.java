package intern.siva.uhelpme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import intern.siva.uhelpme.Fragments.FavouriteFragment;
import intern.siva.uhelpme.Fragments.HomeFragment;
import intern.siva.uhelpme.Fragments.ProfileFragment;
import intern.siva.uhelpme.Pojo.CatogeryPojo;
import intern.siva.uhelpme.databinding.ActivityHome1Binding;
import io.paperdb.Paper;
import me.ibrahimsn.lib.OnItemSelectedListener;

public class Home1 extends AppCompatActivity {
    ArrayList<CatogeryPojo> catogeryPojos;
    ActivityHome1Binding binding;
    AlertDialog writeBlogDialog;
    Bitmap bitmap;
    File file;
    Uri filePathUri;
    String selectedservice;
    String encodeImageString;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    StorageReference reference;
    String Currentuserid;

    String name;
    String AddressLine;
    String Landmark;
    String phoneadd;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHome1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Framelayout, new HomeFragment());
        transaction.commit();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseStorage.getInstance().getReference();
     // Currentuserid = FirebaseAuth.getInstance().getUid();
        Currentuserid="zfuWpY0wK7hNwqDfyn75dhhVvZL2";
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.Framelayout, new HomeFragment());

                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.Framelayout, new FavouriteFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.Framelayout, new ProfileFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });


    }

    public void Upload() {
        LayoutInflater factory = LayoutInflater.from(Home1.this);

        final View writeBlogDialogLayout = factory.inflate(R.layout.upload_dialog, null);

        writeBlogDialog = new AlertDialog.Builder(Home1.this).create();

        writeBlogDialog.setView(writeBlogDialogLayout);

        writeBlogDialog.show();

        EditText Name = writeBlogDialogLayout.findViewById(R.id.nameAddressEditText);
        EditText Addressline = writeBlogDialogLayout.findViewById(R.id.address1Edittext);
        Button chooseImageButton = writeBlogDialogLayout.findViewById(R.id.choose);
        Spinner categorySpinner = writeBlogDialogLayout.findViewById(R.id.categorySpinner);
        EditText landmark = writeBlogDialogLayout.findViewById(R.id.landmarkEdittext);
        EditText PhoneNo = writeBlogDialogLayout.findViewById(R.id.phoneEdittext);
        Button addbutton = writeBlogDialogLayout.findViewById(R.id.buttonAddAddress);

        //setting up spinner


        ArrayList<String> array = new ArrayList<>(Arrays.asList("Hotels", "Resturants", "Cafe", "Bakery", "Groceries", "Fashions", "Footwears","Stationaries","Saloon","Chemist"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array);
        categorySpinner.setAdapter(adapter);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open the chooser to chose the image

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                String[] mimeTypes = {"image/png", "image/jpg", "image/jpeg"};  // and choose only jpg , png  and jpeg
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);


            }
        });


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // will send the hashmap in the api function

                HashMap<String, String> hashMap = new HashMap<>();


                if (Name.getText().toString().trim().isEmpty())
                    Name.setError("required");
                else if (Addressline.getText().toString().trim().isEmpty())
                    Addressline.setError("required");
                else if (landmark.getText().toString().trim().isEmpty())
                    landmark.setError("required");
                else if (PhoneNo.getText().toString().trim().isEmpty())
                    landmark.setError("required");
                else if (bitmap == null)
                    Toast.makeText(getApplicationContext(), "Select the image", Toast.LENGTH_LONG).show();
//                else if(categorySpinner.getSelectedItemPosition()==0){
//                    Toast.makeText(BlogsActivity.this, "Select the Category", Toast.LENGTH_LONG).show();
//                }
                else {
                    selectedservice = categorySpinner.getSelectedItem().toString();
                    Toast.makeText(Home1.this, "Uploading--" + selectedservice, Toast.LENGTH_SHORT).show();
                    name = Name.getText().toString().toUpperCase();
                    AddressLine = Addressline.getText().toString().toUpperCase();
                    Landmark = landmark.getText().toString();
                    phoneadd = PhoneNo.getText().toString();


                    addImagetoDatabse(name, AddressLine, Landmark, phoneadd, selectedservice);
                    writeBlogDialog.dismiss();


                }

            }
        });


    }

    public void addImagetoDatabse(String name, String addressLine, String landmark, String phoneadd, String selectedservice)
    {
    StorageReference postRef = reference.child(selectedservice).child(FieldValue.serverTimestamp().toString() + ".jpg");
                    postRef.putFile(filePathUri).

    addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onComplete (@NonNull Task < UploadTask.TaskSnapshot > task) {
            if (task.isSuccessful()) {
           //  String currentuserid= task.getResult().getUser().getUid();
                postRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String, Object> postMap = new HashMap<>();
                        postMap.put("image", uri.toString());
                        postMap.put("user", Currentuserid);
                        postMap.put("Name", name);
                        postMap.put("time", FieldValue.serverTimestamp());
                        postMap.put("AddressLin1", addressLine);
                        postMap.put("Landmark", landmark);
                        postMap.put("PhoneNo", phoneadd);
                        postMap.put("CatogerType", selectedservice);


                        firestore.collection(selectedservice).add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()) {
                                    // dialog.dismiss();
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                    finish();
                                    LayoutInflater factory = LayoutInflater.from(Home1.this);
                                    final View writeBlogDialogLayout = factory.inflate(R.layout.sucessful, null);
                                    writeBlogDialog = new AlertDialog.Builder(Home1.this).create();
                                    writeBlogDialog.setView(writeBlogDialogLayout);
                                    writeBlogDialog.show();
//                                  Toast.makeText(getApplicationContext(), "Sucessful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            } else {
               // mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    });
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            filePathUri=data.getData();
            file = new File(filePathUri.toString());

            Uri uri2=  Uri.fromFile(new File(filePathUri.getPath()));
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filePathUri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

                Toast.makeText(Home1.this,"error "+ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        return  bytesofimage;
    }


}