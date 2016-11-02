package com.fd.goraebang.main.mypage;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fd.goraebang.R;
import com.fd.goraebang.consts.CONST;
import com.fd.goraebang.custom.CustomActivityWithToolbar;
import com.fd.goraebang.model.User;
import com.fd.goraebang.util.AppController;
import com.fd.goraebang.util.CallUtils;
import com.fd.goraebang.util.CustomProgressDialog;
import com.fd.goraebang.util.Utils;
import com.yalantis.ucrop.UCrop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

@EActivity(R.layout.activity_my_profile)
public class ActivityMyProfile extends CustomActivityWithToolbar{
    @ViewById
    CircleImageView ivProfile;
    @ViewById
    ImageView ivProfileBackground;
    @ViewById
    TextView tvEmail, tvGenderMale, tvGenderFemale, tvGenderETC;
    @ViewById
    EditText etName;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private CustomProgressDialog dialog = null;
    private Uri imageUri;
    private String userName, userGender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences(CONST.PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }
    
    @AfterViews
    void afterViews(){
        setToolbar("내 정보", 0, R.drawable.ic_arrow_back_white_24dp, 0, 0);

        userName = AppController.USER.getName();
        userGender = AppController.USER.getGender();

        updateView();
    }

    private void updateView() {
        if (AppController.USER.getThumbnail() != null)
            Glide.with(this).load(AppController.USER.getThumbnail()).into(ivProfile);

        if (AppController.USER.getImage() != null)
            Glide.with(this).load(AppController.USER.getImage())
                    .bitmapTransform(new BlurTransformation(this, 25))
                    .into(ivProfileBackground);

        tvEmail.setText(AppController.USER.getEmail());
        etName.setText(userName);
        tvGenderMale.setTextColor(getResources().getColor(R.color.gray));
        tvGenderFemale.setTextColor(getResources().getColor(R.color.gray));
        tvGenderETC.setTextColor(getResources().getColor(R.color.gray));

        if (AppController.USER.getGender() == null) {
            return;
        }

        if (userGender.equals("1")) {
            tvGenderMale.setTextColor(getResources().getColor(R.color.white));
        } else if (userGender.equals("2")) {
            tvGenderFemale.setTextColor(getResources().getColor(R.color.white));
        } else if (userGender.equals("3")) {
            tvGenderETC.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && intent != null) {
            switch (requestCode) {
                case CONST.RQ_CODE_SELECT_IMAGE:
                    String timestamp = System.currentTimeMillis()/1000 + "";
                    Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), timestamp));
                    cropImage(intent.getData(), mDestinationUri);
                    break;
                case UCrop.REQUEST_CROP:
                    imageUri = UCrop.getOutput(intent);
                    saveCroppedImage();
                    break;
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(intent);
                    Toast.makeText(this, "이미지 생성에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveCroppedImage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void saveCroppedImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            if (imageUri != null && imageUri.getScheme().equals("file")) {
                try {
                    copyFileToDownloads(imageUri);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.msgErrorCommon), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyFileToDownloads(Uri croppedFileUri) throws Exception {
        String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), croppedFileUri.getLastPathSegment());

        File saveFile = new File(downloadsDirectoryPath, filename);

        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
        FileOutputStream outStream = new FileOutputStream(saveFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();

        uploadImage(saveFile);
    }

    private void cropImage(Uri data, Uri mDestinationUri) {
        UCrop.of(data, mDestinationUri)
                .withMaxResultSize(800, 800)
                .start(this);
    }

    private void uploadImage(File file) {
        dialog = Utils.createDialog(this, dialog);

        MediaType mediaType = MediaType.parse("image/*");
        RequestBody requestBody = RequestBody.create(mediaType, file);

        updateUser(requestBody, userName, userGender);
    }

    private void updateUser(final RequestBody file, final String name, final String gender){
        dialog = Utils.createDialog(this, dialog);
        Log.d("GORAEBANG", "update user file : " + file);

        dialog = Utils.createDialog(this, dialog);
        Call<User> call = AppController.getAccountService().modify(
                AppController.USER_TOKEN,
                name,
                gender,
                null,
                null,
                null,
                null
        );

        call.enqueue(new CallUtils<User>(call, this, getResources().getString(R.string.msgErrorCommon)) {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getResult().equals("SUCCESS")) {
                    AppController.USER.setName(response.body().getName());
                    AppController.USER.setGender(response.body().getGender());
                    AppController.USER.setImage(response.body().getImage());
                    onComplete();
                    showToast("저장되었습니다.");
                } else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast(msg);
                onComplete();
            }

            @Override
            public void onComplete() {
                dialog = Utils.hideDialog(dialog);
            }
        });
    }

    private void updateUserGender(int id){
        if(id == R.id.tvGenderMale){
            userGender = "1";
        }else if(id == R.id.tvGenderFemale){
            userGender = "2";
        }else if(id == R.id.tvGenderETC){
            userGender = "3";
        }

        updateView();
    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnLeft:
                finish();
                break;
            case R.id.btnComplete:
                userName = etName.getText().toString();
                updateUser(null, userName, userGender);
                break;
            case R.id.btnSetting:
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "응용프로그램을 선택하세요."), CONST.RQ_CODE_SELECT_IMAGE);
                intent = null;
                break;
            case R.id.tvGenderMale:
            case R.id.tvGenderFemale:
            case R.id.tvGenderETC:
                userName = etName.getText().toString();
                updateUserGender(v.getId());
                break;
            case R.id.tvChangePassword:
                intent = new Intent(this, ActivityChangePassword_.class);
                break;
            default :
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
