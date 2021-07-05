package bomoncntt.svk60.vuhainguyen1851062678;

import androidx.appcompat.app.AppCompatActivity;

import android.app.slice.Slice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import bomoncntt.svk60.vuhainguyen1851062678.helper.DatabaseHelper;

public class LoginForm extends AppCompatActivity {
    EditText txtusername, txtpassword;
    TextView create, lg, forgotpassword, noaccount;
    Button btnlogin;
    CheckBox remember;
    DatabaseHelper mydb;
    public static final String  MyPREFERENCES = "MYPREFS";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        txtpassword = findViewById(R.id.txtpassword);
        setTitle("Trang đăng nhập");
        txtusername = findViewById(R.id.txtusername);
        btnlogin = findViewById(R.id.btnlogin);
        create = findViewById(R.id.create);
        remember = findViewById(R.id.remember);
        lg = findViewById(R.id.lg);
        noaccount = findViewById(R.id.noaccount);
        forgotpassword = findViewById(R.id.forgotpassword);
        mydb = new DatabaseHelper(this);
        Animatoo.animateZoom(LoginForm.this);
        value(0, 300);


        lg.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        txtpassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        txtusername.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        forgotpassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        remember.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        btnlogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1100).start();
        noaccount.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1300).start();
        create.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
//        mydb.insertUser("toibidien", "123");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginForm.this,RegisterForm.class);
                startActivity(in);
                Animatoo.animateZoom(LoginForm.this);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                //ghi lại lịch sử đăng nhập
                if(username.equals("")||password.equals("")){
                    Toast.makeText(LoginForm.this, "Có vẻ bạn đã quên nhập gì đó!", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkuserpass = mydb.checkUserNamePassWord(username, password);
                    if(checkuserpass)
                    {
                        Toast.makeText(LoginForm.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        String stt = "Thành công";
                        writeMessage(username,password, stt);
                        Intent in = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(in);
                        Animatoo.animateZoom(LoginForm.this);

                        finish();
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        if(remember.isChecked()){
                            //lưu thông tin xuống sharepreferences
                            editor=pref.edit(); //chỉnh sửa file  MYPREFS.xml
                            editor.putString("USERNAME",username); //ghi thông tin vào fields USERNAME='admin'
                            editor.putString("PASSWORD",password);
                            editor.commit();
                        }else
                        {
                            //xóa preferences
                            editor=pref.edit();
                            editor.clear();
                            editor.commit();
                        }
                    }
                    else{

                        Toast.makeText(LoginForm.this, "Tài khoản hoặc mật khẩu chưa đúng!", Toast.LENGTH_SHORT).show();
                        String stt = "Thất bại";
                        writeMessage(username,password, stt);
                    }
                }
            }
        });
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String un=pref.getString("USERNAME","");
        String pw=pref.getString("PASSWORD","");
        Log.v("username",un);
        if(!un.equals("")&&!pw.equals("")){

            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
            finish();
        }


    }
    private void writeMessage(String un,String pw, String status){
        try {
            FileOutputStream fileout = openFileOutput("datalogin.txt", MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(un + "," + pw + ","+status+ ";\n"); //csv
            outputWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public float value(float alpha, float transY)
    {
        txtpassword.setAlpha(alpha);
        txtusername.setAlpha(alpha);
        btnlogin.setAlpha(alpha);
        create.setAlpha(alpha);
        remember.setAlpha(alpha);
        forgotpassword.setAlpha(alpha);
        lg.setAlpha(alpha);
        noaccount.setAlpha(alpha);

        txtpassword.setTranslationY(transY);
        txtusername.setTranslationY(transY);
        btnlogin.setTranslationY(transY);
        create.setTranslationY(transY);
        remember.setTranslationY(transY);
        forgotpassword.setTranslationY(transY);
        lg.setTranslationY(transY);
        noaccount.setTranslationY(transY);
        return 0;
    }

}