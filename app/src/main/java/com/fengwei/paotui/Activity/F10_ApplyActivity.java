package com.fengwei.paotui.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.fengwei.paotui.Model.UserModel;
import com.fengwei.paotui.PaotuiAppConst;
import com.fengwei.paotui.Protocol.ENUM_USER_GENDER;
import com.fengwei.paotui.R;
import com.fengwei.paotui.Utils.VerifyIdCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by fengwei on 15/5/17.
 */
public class F10_ApplyActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_PHOTO = 2;
    private final int REQUEST_PHOTOZOOM = 3;
    Button applyButton;
    ImageView avatarPreview;
    ImageView backButton;
    EditText bankIdEditText;
    TextView changeAvatar;
    UserModel dataModel;
    private Dialog dialog;
    private File file;
    private File fileDir;
    private File fileDir2;
    private String fileName = "";
    EditText fullNameEditText;
    ImageView genderArrow;
    LinearLayout genderLayout;
    private PopupWindow genderPopwindow;
    ENUM_USER_GENDER genderStyle = ENUM_USER_GENDER.MAN;
    TextView genderTextView;
    EditText identityEditText;
    File imageFile;
    private String imagePath;
    TextView service_aggrenment;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f10_apply_activity);
        fullNameEditText = (EditText) findViewById(R.id.f10_apply_name);
        changeAvatar = (TextView) findViewById(R.id.f10_change_avatar);
        identityEditText = (EditText) findViewById(R.id.f10_id_card);
        bankIdEditText = (EditText) findViewById(R.id.f10_bank_id);
        applyButton = (Button) findViewById(R.id.f10_apply_button);
        avatarPreview = (ImageView) findViewById(R.id.avatar_preview);
        genderArrow = (ImageView) findViewById(R.id.gender_arrow);
        genderLayout = (LinearLayout) findViewById(R.id.gender_layout);
        genderTextView = (TextView) findViewById(R.id.gender_title);
        service_aggrenment = (TextView) findViewById(R.id.service_aggrenment);
        service_aggrenment.getPaint().setFlags(8);
        service_aggrenment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(F10_ApplyActivity.this, WebViewActivity.class);
                intent.putExtra("weburl","http://www.kuaidi111.com");
                startActivity(intent);
            }
        });

        applyButton.setOnClickListener(this);
        changeAvatar.setOnClickListener(this);
        dataModel = new UserModel(this);
        dataModel.addResponseListener(this);
        genderLayout.setOnClickListener(this);
        backButton = (ImageView) findViewById(R.id.top_view_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shared = getSharedPreferences("user_info",0);



    }

    @Override
    public void onClick(View v) {

        String fullName = fullNameEditText.getText().toString();
        String identity = identityEditText.getText().toString();
        String bankId = bankIdEditText.getText().toString();
        switch (v.getId()){
            case R.id.f10_change_avatar:
                showDialog();
                break;
            case R.id.gender_layout:
                View popWindow = View.inflate(this, R.layout.gender_view, null);
                TextView genderMan = (TextView)popWindow.findViewById(R.id.gender_men);
                genderMan.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        genderStyle = ENUM_USER_GENDER.MAN;
                        genderTextView.setText("男");
                        genderPopwindow.dismiss();
                        genderArrow.setImageResource(R.drawable.b3_arrow_down);

                    }
                });
                TextView genderWomen = (TextView)popWindow.findViewById(R.id.gender_women);
                genderWomen.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        genderStyle = ENUM_USER_GENDER.WOMAN;
                        genderTextView.setText("女");
                        genderArrow.setImageResource(R.drawable.b3_arrow_down);
                        genderPopwindow.dismiss();
                    }
                });
                genderPopwindow = new PopupWindow(popWindow, v.getWidth(), -2, true);
                genderPopwindow.setFocusable(true);
                genderPopwindow.setOutsideTouchable(true);
                genderArrow.setImageResource(R.drawable.b4_arrow_up);
                genderPopwindow.setBackgroundDrawable(new BitmapDrawable());
                genderPopwindow.showAsDropDown(v);
                genderPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    public void onDismiss() {
                        genderArrow.setImageResource(R.drawable.b3_arrow_down);
                    }
                });
                break;
            case R.id.f10_apply_button:
                VerifyIdCard verifyIdCard = new VerifyIdCard();
                if("".equals(fullName)) {
                    ToastView toast = new ToastView(this, "姓名不能为空");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    fullNameEditText.requestFocus();
                    return;
                }
                if(fullName.length() < 2) {
                    ToastView toast = new ToastView(this, "姓名不能太短");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    fullNameEditText.requestFocus();
                    return;
                }
                if(fullName.length() > 10) {
                    ToastView toast = new ToastView(this, "姓名不能太长");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    fullNameEditText.requestFocus();
                    return;
                }
                if("".equals(identity)) {
                    ToastView toast = new ToastView(this, "身份证号码不能为空");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    identityEditText.requestFocus();
                    return;
                }
                if(!verifyIdCard.verify(identity)) {
                    ToastView toast = new ToastView(this, "身份证号码不正确");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    identityEditText.requestFocus();
                    return;
                }
                if("".equals(bankId)) {
                    ToastView toast = new ToastView(this, "银行卡号不能为空");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    bankIdEditText.requestFocus();
                    return;
                }
                if(!VerifyIdCard.checkBankCard(bankId)) {
                    ToastView toast = new ToastView(this, "银行卡号不正确");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    bankIdEditText.requestFocus();
                    return;
                }
                if(imageFile == null) {
                    ToastView toast = new ToastView(this, "头像不能为空");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                dataModel.certify(fullName, identity, bankId, genderStyle, imageFile);
                break;
        }

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if(url.endsWith("/user/certify")) {
            Message msg = new Message();
            msg.what = 3;
            EventBus.getDefault().post(msg);
            ToastView toast = new ToastView(this, "申请已提交审核");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(this, SlidingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.photo_dialog,null);
        dialog = new Dialog(this,R.style.dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        LinearLayout requsetCameraLayout = (LinearLayout) view.findViewById(R.id.register_camera);
        LinearLayout requestPhotoLayout = (LinearLayout)view.findViewById(R.id.register_photo);
        requsetCameraLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                if(fileDir == null) {
                    fileDir = new File(PaotuiAppConst.FILEPATH + "img/");
                    if(!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                }
                fileName = PaotuiAppConst.FILEPATH + "img/" + "avatar.jpg";
                file = new File(fileName);
                Uri imageuri = Uri.fromFile(file);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", imageuri);
                intent.putExtra("return-data", false);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        requestPhotoLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                Intent picture = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUEST_PHOTO);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1) {
            if(requestCode == REQUEST_CAMERA) {
                File files = new File(fileName);
                if(files.exists()) {
                    imagePath = fileName;
                    imagePath = startPhotoZoom(Uri.fromFile(new File(imagePath)));
                }
                return;
            }
            if(requestCode == REQUEST_PHOTO) {
                Uri selectedImage = data.getData();
                imagePath = startPhotoZoom(selectedImage);
                return;
            }
            if(requestCode == REQUEST_PHOTOZOOM) {
                File f = new File(imagePath);
                if(f.exists()) {
                    imageFile = new File(ImageUtil.zoomImage(imagePath, 350));
                    Bitmap previewBitmap = BitmapFactory.decodeFile(imagePath);
                    avatarPreview.setImageBitmap(previewBitmap);
                    return;
                }
                ToastView toast = new ToastView(this, "图片不存在");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    private String startPhotoZoom(Uri uri) {
        if(fileDir2 == null) {
            fileDir2 = new File(PaotuiAppConst.FILEPATH + "img/");
            if(!fileDir2.exists()) {
                fileDir2.mkdirs();
            }
        }
        String fileName = PaotuiAppConst.imageName();
        String filePath = fileDir2 + fileName;
        File loadingFile = new File(filePath);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 400);
        intent.putExtra("aspectY", 400);
        intent.putExtra("output", Uri.fromFile(loadingFile));
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_PHOTOZOOM);
        return filePath;
    }


}
