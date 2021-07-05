package bomoncntt.svk60.vuhainguyen1851062678;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk60.vuhainguyen1851062678.helper.Common;
import bomoncntt.svk60.vuhainguyen1851062678.helper.DatabaseHelper;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;

public class Student_Info extends AppCompatActivity {
    Spinner spingrade;
    String grade="";
    ArrayList<String> arraylistgrade;
    EditText txtstdid, txtfullname;
    Button btnclear, btnsave;
    RadioGroup rdgrsex;
    RadioButton rdbuttonsex;
    DatabaseHelper mydb= null;
    CircleImageView croppedImageView;
    Bitmap myBitmap;
    Uri picUri;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    public final static int CAMERA_REQUEST = 9999;
    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);
        mydb = new DatabaseHelper(this);
        txtstdid = findViewById(R.id.txtusername);
        txtfullname = findViewById(R.id.txtpassword);
        btnclear =  findViewById(R.id.btnclear);
        btnsave =  findViewById(R.id.btnlogin);
        rdgrsex= (RadioGroup)findViewById(R.id.rdgrsex);
        croppedImageView = findViewById(R.id.avapic);
        spingrade = findViewById(R.id.spingrade);
        arraylistgrade = new ArrayList<String>();
        arraylistgrade.add("Khóa 59") ;
        arraylistgrade.add("Khóa 60") ;
        arraylistgrade.add("Khóa 61");
        arraylistgrade.add("Khóa 62") ;
        ArrayAdapter<String> adaptergrade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraylistgrade);
        adaptergrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spingrade.setAdapter(adaptergrade);
        spingrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = arraylistgrade.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                grade = "";
            }
        });
        Intent intent = getIntent();//getdata from mainacti
        String stdid = intent.getStringExtra("Stdid");
        String fullname = intent.getStringExtra("Fullname");
        String sex = intent.getStringExtra("Sex");
        String grade1 = intent.getStringExtra("Grade");
        String image = intent.getStringExtra("Image");
        String flag= intent.getStringExtra("Flag");
        //croppedImageView.setImageBitmap(Common.StringToBitMap(image));
        if(!stdid.equals(""))
        {
            txtstdid.setText(stdid);
            txtfullname.setText(fullname);
            if("Nam".equals(sex)){
                rdbuttonsex=findViewById(R.id.rdbtnmale);
            }else if("Nữ".equals(sex)){
                rdbuttonsex=findViewById(R.id.rdbtnfemale);
            }
            rdbuttonsex.setChecked(true);
            setSelectSpinner(grade1);
            croppedImageView.setImageBitmap(Common.StringToBitMap(image));
        }
        Log.v("thamso", "okok");
        if(flag.equals("Add"))
        {
            setTitle("Thêm mới sinh viên");
        }
        else
        {
            setTitle("Sửa dữ liệu");
        }
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtstdid.length()==0 || txtfullname.length()==0)
                {
                    Toast.makeText(Student_Info.this, "Có vẻ bạn quên nhập cái gì đó", Toast.LENGTH_LONG).show();
                }
                else if(flag.equals("Add"))
                {
                    String stdid = txtstdid.getText().toString();
                    String stdname= txtfullname.getText().toString();
                    int selectedid = rdgrsex.getCheckedRadioButtonId();
                    rdbuttonsex= findViewById(selectedid);
                    String sex = rdbuttonsex.getText().toString();
                    String stdimage="";
                    if(myBitmap!=null) {
                        stdimage = Common.BitMapToString(myBitmap);
                    }
                    if(mydb.checkid(stdid)){
                        boolean insertedKq=mydb.insertData(stdid, stdname, sex, grade,stdimage);
                        if(insertedKq)
                        {
                            Toast.makeText(Student_Info.this,"Insert thành công", Toast.LENGTH_SHORT).show();
                            Log.d("tag_img","+"+stdimage);
                        }else
                        {
                            Toast.makeText(Student_Info.this,"Insert không thành công", Toast.LENGTH_SHORT).show();
                            Log.d("inserted","khongthanhcong");
                        }
                        finish();
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                    }

                }
                else
                {

                    String stdid = txtstdid.getText().toString();
                    String stdname= txtfullname.getText().toString();
                    int selectedid = rdgrsex.getCheckedRadioButtonId();
                    rdbuttonsex= findViewById(selectedid);
                    String sex = rdbuttonsex.getText().toString();

                    String stdimage="";
                    if(myBitmap!=null) {
                        stdimage = Common.BitMapToString(myBitmap);
                    }
                    boolean updatedkq=mydb.update(stdid, stdname, sex, grade,stdimage);
                    if(updatedkq)
                    {
                        Toast.makeText(Student_Info.this,"Update thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Student_Info.this,"Update không thành công", Toast.LENGTH_SHORT).show();
                        Log.d("inserted","khongthanhcong");
                    }

                }//nguyen
                finish();
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }

        });
        croppedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();

            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstdid.requestFocus();
                txtstdid.setText("");
                txtfullname.setText("");
                rdbuttonsex=findViewById(R.id.rdbtnmale);
                rdgrsex.check(rdbuttonsex.getId());
                spingrade.setSelection(0);


            }
        });
    }
    private void setSelectSpinner(String lop) {
        for(int i=0; i<spingrade.getCount();i++){
            if(lop.equals(spingrade.getItemAtPosition(i).toString())){
                spingrade.setSelection(i);
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.item_actionbar_camera, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId==R.id.menu_camera){
            Log.d("camera", "ok");
            openCamera();

        }
        if(itemId==R.id.menu_folder){
            choose();
        }
        return super.onOptionsItemSelected(item);
    }
    public Intent getPickImageChooserIntentFile() {
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
        }
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        Log.v("allIntents",""+allIntents.size());
        return chooserIntent;
    }
    public Intent getPickImageChooserIntent() {
        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
        }
        allIntents.add(0,captureIntent);
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);

        }
        Intent chooserIntent = Intent.createChooser(captureIntent, "Select source");
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, captureIntent);

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        Log.v("allIntents",""+allIntents.size());
        return chooserIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            Log.v("picUri",""+data);
            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                // Log.v("picUri",""+picUri.getPath());
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);//hình ảnh trong thư mục mà chụp
                    Log.v("myBitmap",""+myBitmap);
                    //   myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500); //nén ảnh lại

                    //Đoạn lệnh hiển thị ảnh lên circleimageview
                    croppedImageView.setImageBitmap(myBitmap);
                    //  imageView.setImageBitmap(myBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {

                Log.v("picUri","null");
                bitmap = (Bitmap) data.getExtras().get("data");

                myBitmap = bitmap;

                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }

                // imageView.setImageBitmap(myBitmap);

            }

        }

    }
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    //nén tập tin hình ảnh chụp
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    public void openCamera()
    {
        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        Intent in=getPickImageChooserIntent();
        startActivityForResult(in, 200);
    }
    public void choose(){
        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        startActivityForResult(getPickImageChooserIntentFile(), 200);
    }

}