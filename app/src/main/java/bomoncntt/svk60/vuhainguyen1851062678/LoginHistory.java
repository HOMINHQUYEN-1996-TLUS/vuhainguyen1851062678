package bomoncntt.svk60.vuhainguyen1851062678;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bomoncntt.svk60.vuhainguyen1851062678.helper.HisLogListAdapter;
import bomoncntt.svk60.vuhainguyen1851062678.model.LoginHis;

public class LoginHistory extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 100;
    TextView txtname, txtstatus;
    ListView hislist;
    ArrayList arrayListLog=null;
    ArrayAdapter arrayAdapterLog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_history);
        txtname = findViewById(R.id.txtname);
        txtstatus = findViewById(R.id.txtstatus);
        hislist = findViewById(R.id.hislist);

        setTitle("Lịch sử đăng nhập");
        readData();
        showDataListView();
    }
    private void showDataListView() {
        String str=readData();
        arrayListLog=arrayListUserLog(str);
        arrayAdapterLog= new HisLogListAdapter(this, arrayListLog);
        hislist.setAdapter(arrayAdapterLog);
    }


    public String readData() {
        String s = "";
        try {
            FileInputStream fileIn = openFileInput("datalogin.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
        } catch (Exception e) {

        }
        return s;
    }

    public ArrayList<LoginHis> arrayListUserLog(String str){
        ArrayList<LoginHis> result=new ArrayList<>();
        try {
            String arr[]=str.split(";\n");
            for (String s : arr) {
                result.add((new LoginHis(s)));
//                Log.v("sublog", s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home :
                this.finish();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}