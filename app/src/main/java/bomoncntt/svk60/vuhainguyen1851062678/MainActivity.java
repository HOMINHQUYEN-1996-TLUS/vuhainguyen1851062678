package bomoncntt.svk60.vuhainguyen1851062678;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import bomoncntt.svk60.vuhainguyen1851062678.helper.DatabaseHelper;
import bomoncntt.svk60.vuhainguyen1851062678.helper.ListAdapter;
import bomoncntt.svk60.vuhainguyen1851062678.model.student;

import static bomoncntt.svk60.vuhainguyen1851062678.LoginForm.MyPREFERENCES;

public class MainActivity extends AppCompatActivity {

    ArrayList<student> arrayliststu=null;
    ArrayAdapter<student> adapter=null;
    ListView lvst;
    DatabaseHelper mydb=null;
    ArrayList<student> StudentCheckedItemList;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvst=findViewById(R.id.liststu);
        setTitle("Danh sách sinh viên");
        StudentCheckedItemList = new ArrayList<>();
        mydb=new DatabaseHelper(this);
        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        arrayliststu=new ArrayList<>();//Khởi tạo mảng lưu các đối tượng
        Cursor cursor=mydb.showData();
        while(cursor.moveToNext()){
            student std=new student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            //student std = new student("1996","lam","nam","K60","xxx");
            arrayliststu.add(std);
        }
        //b2
        //custom, mỗi 1 phần tử sẽ gọi giao dien layout_item_sv
        adapter = new ListAdapter(this, arrayliststu);//gán data mảng vào adapter mà mình custom
        //b2
        lvst.setAdapter(adapter);
        lvst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox itemCheckbox = (CheckBox)view.findViewById(R.id.student_list_item_checkbox);
                boolean checkChecked = false;
                if(itemCheckbox.isChecked()){
                    itemCheckbox.setChecked(false);
                    checkChecked=false;
                }
                else{
                    itemCheckbox.setChecked(true);
                    checkChecked= true;
                }
                student std = new student();
                std.setStdid(arrayliststu.get(position).getStdid());
                std.setFullname(arrayliststu.get(position).getFullname());
                std.setSex(arrayliststu.get(position).getSex());
                std.setGrade(arrayliststu.get(position).getGrade());
                std.setStdimage(arrayliststu.get(position).getStdimage());
                Log.v("obstu",""+std);
                addCheckListItem(std, checkChecked);
                Log.d("list", ""+getStudent());
            }
        });
    }
    private String getStudent() {
        StringBuffer b = new StringBuffer();
        if (StudentCheckedItemList != null) {
            int size = StudentCheckedItemList.size();
            for (int i = 0; i < size; i++) {
                student std = StudentCheckedItemList.get(i);
                b.append(std.getStdid());
                b.append("");
            }
        }
        return b.toString().trim();
    }
    private void addCheckListItem(student user, boolean add){
        if(StudentCheckedItemList != null)
        {
            boolean countExist = false;
            int existPosition = -1;
            int size = StudentCheckedItemList.size();
            for(int i = 0; i < size; i++)
            {
                student std = StudentCheckedItemList.get(i);
                if(std.getStdid().equals(user.getStdid())){
                    countExist = true;
                    existPosition = i;
                    break;
                }
            }
            if(add){
                if(!countExist){
                    StudentCheckedItemList.add(user);
                }
            }
            else
            {
                if(countExist){
                    if(existPosition != 1)
                    {
                        StudentCheckedItemList.remove(existPosition);
                    }
                }
            }
        }
        else
        {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.item_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId==R.id.menu_add){
            Log.v("add", "ok");
            Intent in= new Intent(this, Student_Info.class);
            in.putExtra("Flag", "Add");
            in.putExtra("Stdid", "");
            in.putExtra("Fullname", "");
            in.putExtra("Sex", "");
            in.putExtra("Grade", "");
            in.putExtra("Image", "");

            startActivity(in);
        }
        else if( itemId == R.id.menu_del)
        {

            if(StudentCheckedItemList !=null) {
                int size = StudentCheckedItemList.size();
                if (size == 0) {
                    Toast.makeText(MainActivity.this, "Chọn ít nhất 1 phần tử để thao tác ", Toast.LENGTH_LONG).show();
                }
                else {
                    for(int i = 0; i<size;i++)
                    {
                        student std = StudentCheckedItemList.get(i);
                        Integer delete = mydb.delete(std.getStdid());
                        if(delete>0){
                            StudentCheckedItemList.remove(i);
                            size = StudentCheckedItemList.size();
                        }else{
                            Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_LONG).show();
                        }
                        i--;
                    }
                    arrayliststu.clear();
                    Cursor cursor = mydb.showData();
                    while(cursor.moveToNext()){
                        student std = new student(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
                    arrayliststu.add(std);
                    }
                    adapter = new ListAdapter(this, arrayliststu);
                    lvst.setAdapter(adapter);
                }
            }
        }
        else if( itemId == R.id.menu_edit)
            {
                if(StudentCheckedItemList !=null)
                {
                    int size = StudentCheckedItemList.size();
                    if(size != 1)
                    {
                        Toast.makeText(MainActivity.this, "Chỉ chọn 1 phần tử để thao tác", Toast.LENGTH_LONG).show();
                    }
                else{


                Log.v("edit","ok");
                student std = StudentCheckedItemList.get(0);
                Intent in= new Intent(this, Student_Info.class);
                in.putExtra("Flag", "Edit");
                in.putExtra("Stdid", std.getStdid());
                in.putExtra("Fullname", std.getFullname());
                in.putExtra("Sex", std.getSex());
                in.putExtra("Grade", std.getGrade());
                in.putExtra("Image", std.getStdimage());
                startActivity(in);

                }
            }
            }
        else if( itemId == R.id.menu_exit){
            editor=pref.edit();
            editor.remove("USERNAME");
            editor.remove("PASSWORD");
            editor.commit();
            finish();
            Intent in = new Intent(getApplicationContext(), LoginForm.class);
            startActivity(in);
        }
        else{
            Intent in = new Intent(getApplicationContext(), LoginHistory.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }
}