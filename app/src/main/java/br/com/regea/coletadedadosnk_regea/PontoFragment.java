package br.com.regea.coletadedadosnk_regea;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PontoFragment extends Fragment {

    private static final int REQUEST_TAKE_PHOTO = 262;
    String mCurrentPhotoPath;
    int VIEW_TARGET_ID;
    private Uri imageUri;

    public PontoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_ponto, container, false);

        Button btn_gps = (Button) view.findViewById(R.id.btn_gps);
        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Cadastro)getActivity()).requestLocation(view);
            }
        });

        Button btn_FotoGeral = (Button) view.findViewById(R.id.btn_foto_geral);
        btn_FotoGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VIEW_TARGET_ID = R.id.img_FotoGeral;
                dispatchTakePictureIntent();
            }
        });

        Button btn_FotoDetalhe = (Button) view.findViewById(R.id.btn_foto_detalhe);
        btn_FotoDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VIEW_TARGET_ID = R.id.img_FotoDetalhe;
                dispatchTakePictureIntent();
            }
        });

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File fotoFile = null;
            try {
                fotoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (fotoFile != null) {
                imageUri = FileProvider.getUriForFile(getContext(),
                        "br.com.regea.android.fileprovider",
                        fotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) getView().findViewById(VIEW_TARGET_ID);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        Bitmap d = new BitmapDrawable(getContext().getResources(), bitmap).getBitmap();
                        int nh = (int) (d.getHeight() * (512.0 / d.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(d, 512, nh, true);

                        imageView.setImageBitmap(scaled);
                        Toast.makeText(getContext(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Falha ao exibir imagem.", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

}
