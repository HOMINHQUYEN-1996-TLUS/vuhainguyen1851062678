package bomoncntt.svk60.vuhainguyen1851062678;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import bomoncntt.svk60.vuhainguyen1851062678.helper.DatabaseHelper;

public class RegisterForm extends AppCompatActivity {
    EditText txtusername, txtpassword, txtrepassword;
    TextView alreadyaccount, rg;
    Button btnregister;
    DatabaseHelper mydb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        mydb = new DatabaseHelper(this);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtrepassword = findViewById(R.id.txtrepassword);
        btnregister = findViewById(R.id.btnregister);
        alreadyaccount = findViewById(R.id.alreadyaccount);
        rg = findViewById(R.id.rg);

        value(0, 300);
        rg.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        txtusername.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        txtpassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        txtrepassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnregister.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        alreadyaccount.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1100).start();
        setTitle("Trang đăng ký");
        alreadyaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in = new Intent(RegisterForm.this,LoginForm.class);
                startActivity(in);
                Animatoo.animateZoom(RegisterForm.this);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                String repassword = txtrepassword.getText().toString();
                if(username.equals("")||password.equals("")||repassword.equals("")){
                    Toast.makeText(RegisterForm.this, "Nhap vao", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(password.equals(repassword)){
                        boolean checkName = mydb.checkUsername(username);
                        if(checkName == false){
                            boolean insert = mydb.insertUser(username, password);
                            if(insert == true){
                                Toast.makeText(RegisterForm.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(getApplicationContext(), LoginForm.class);
                                startActivity(in);
                                Animatoo.animateZoom(RegisterForm.this);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterForm.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterForm.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(RegisterForm.this, "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
    public float value( float alpha, float transY){
        txtusername.setAlpha(alpha);
        txtpassword.setAlpha(alpha);
        txtrepassword.setAlpha(alpha);
        btnregister.setAlpha(alpha);
        rg.setAlpha(alpha);
        alreadyaccount.setAlpha(alpha);

        txtusername.setTranslationY(transY);
        txtpassword.setTranslationY(transY);
        txtrepassword.setTranslationY(transY);
        btnregister.setTranslationY(transY);
        rg.setTranslationY(transY);
        alreadyaccount.setTranslationY(transY);
        return 0;
    }
}