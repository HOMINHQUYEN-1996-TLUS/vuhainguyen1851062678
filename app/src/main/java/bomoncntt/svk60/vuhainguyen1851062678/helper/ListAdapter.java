package bomoncntt.svk60.vuhainguyen1851062678.helper;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import bomoncntt.svk60.vuhainguyen1851062678.R;

import bomoncntt.svk60.vuhainguyen1851062678.model.student;
import de.hdodenhof.circleimageview.CircleImageView;


public class ListAdapter extends ArrayAdapter<student> {

    private final Activity context;
    private final ArrayList<student> stulist;

    private static class ViewHolder {

        TextView txtStuid;
        TextView txtFullname;
        TextView txtSex;
        TextView txtGrade;
        ImageView imageView;

    }

    public ListAdapter(Activity context, ArrayList<student> data) {
        super(context, R.layout.layout_item_sv, data);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.stulist = data;
        Log.d("stulist",""+stulist);

    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        //position là vị trí của listview mà khi click
        //getItem(position)-> trà về object (Phụ thuộc vào adapter)
        student dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            //get item mà trong layout
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //layout_item_sv chỉ thay đổi item
            convertView = inflater.inflate(R.layout.layout_item_sv, parent, false);

            //ánh xạ từ xml sang java
            viewHolder.txtStuid =  convertView.findViewById(R.id.item_txtStuid);
            viewHolder.txtFullname =  convertView.findViewById(R.id.itemt_txtFullname);
            viewHolder.txtSex=convertView.findViewById(R.id.item_txtSex);
            viewHolder.txtGrade=convertView.findViewById(R.id.item_txtGrade);
            viewHolder.imageView=convertView.findViewById(R.id.imageViewSt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtStuid.setText(dataModel.getStdid());
        viewHolder.txtFullname.setText(dataModel.getFullname());
        viewHolder.txtSex.setText(dataModel.getSex());
        viewHolder.txtGrade.setText(dataModel.getGrade());
        String anhsv = dataModel.getStdimage();
        viewHolder.imageView.setImageBitmap(Common.StringToBitMap(anhsv));
        return convertView;

    };
}
