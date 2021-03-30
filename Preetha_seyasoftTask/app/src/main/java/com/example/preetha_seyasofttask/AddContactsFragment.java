package com.example.preetha_seyasofttask;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AddContactsFragment extends Fragment {

    @Bind(R.id.button)
    Button button;

    @Bind(R.id.editTextTextPersonName)
    EditText editTextTextPersonName;

    @Bind(R.id.editTextEmail)
    EditText editTextEmail;

    @Bind(R.id.editTextMobile)
    EditText editTextMobile;

    @Bind(R.id.profilePhoto)
    CircleImageView profilePhoto;

    private PostAPIService mAPIService;
    String name;
    String email;
    String mobile;
    public static final int RC_CAMERA_PERM = 123;
    public static final int RC_GALLERY = 124;
    private static final int PICK_IMAGE_GALLERY = 100;
    private static final int PICK_IMAGE_CAMERA = 200;
    Uri imageUri;
    String imageEncoded;

    public AddContactsFragment() {
    }
    public static AddContactsFragment newInstance(String param1, String param2) {
        AddContactsFragment fragment = new AddContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_addcontacts, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.profilePhoto)
    public void onProfileClick(){
        showCameraOrPictureDialog();
    }

    public void showCameraOrPictureDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Option")
                .setItems(R.array.choose_file_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openCamera();
                        } else if (which == 1) {
                            openGallery();
                        }
                    }
                });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.button)
    public void onButtonClick(){

        name = editTextTextPersonName.getText().toString();
        email = editTextEmail.getText().toString();
        mobile = editTextMobile.getText().toString();

        if(editTextTextPersonName.getText().toString()==null || editTextTextPersonName.getText().toString().isEmpty() || editTextTextPersonName.getText().toString().equals("")){
            Toast.makeText(getActivity(),"Please enter your Name",Toast.LENGTH_SHORT).show();
        }else if(editTextEmail.getText().toString()==null || editTextEmail.getText().toString().isEmpty() || editTextEmail.getText().toString().equals("")){
            Toast.makeText(getActivity(),"Please enter your Email",Toast.LENGTH_SHORT).show();
        }else if(editTextMobile.getText().toString()==null || editTextMobile.getText().toString().isEmpty() || editTextMobile.getText().toString().equals("")){
            Toast.makeText(getActivity(),"Please enter your Mobile number",Toast.LENGTH_SHORT).show();
        }else {
            mAPIService = ApiUtils.getAPIService();
            sendPost(name, email, mobile, imageEncoded);
        }

    }

    public void sendPost(String name, String email,String mobile,String image) {
        mAPIService.savePost(name,email,mobile,image).enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                if(response!=null) {
                    showResponse(response.toString());
                    Log.i(TAG, "post submitted to API." + response.toString());
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void openCamera() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);
        } else {
            EasyPermissions.requestPermissions(getActivity(), "This app needs access to your camera so you can take pictures.", RC_CAMERA_PERM, perms);
        }
    }

    @AfterPermissionGranted(RC_GALLERY)
    public void openGallery() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE_GALLERY);
        } else {
            EasyPermissions.requestPermissions(getActivity(), "This app needs access to your gallery for setting profile picture.",
                    RC_GALLERY, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY){
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
            imageEncoded = String.valueOf(imageUri);
        }else if(requestCode == PICK_IMAGE_CAMERA){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePhoto.setImageBitmap(photo);
            encodeTobase64(photo);
        }
    }

    private String encodeTobase64(Bitmap photo) {
        Bitmap immagex=photo;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public void showResponse(String response) {
        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
    }
}
