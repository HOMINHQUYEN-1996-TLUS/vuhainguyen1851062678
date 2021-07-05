package bomoncntt.svk60.vuhainguyen1851062678.helper;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bomoncntt.svk60.vuhainguyen1851062678.R;
import bomoncntt.svk60.vuhainguyen1851062678.model.LoginHis;


public class HisLogListAdapter extends ArrayAdapter<LoginHis> {
    private final Activity context;
    private final ArrayList<LoginHis> listlog;

    private static class ViewHolder {

        TextView txtname;
        TextView txtstatus;


    }

    public HisLogListAdapter(Activity context, ArrayList<LoginHis> data) {
        super(context, R.layout.layout_item_loginhistory, data);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.listlog = data;
        Log.d("listlog",""+listlog);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoginHis userlog=getItem(position);

        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_item_loginhistory, parent, false);

            viewHolder.txtname =  convertView.findViewById(R.id.txtname);
            viewHolder.txtstatus=convertView.findViewById(R.id.txtstatus);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtname.setText(userlog.getUsername());
        viewHolder.txtstatus.setText(userlog.getStatus());

        return convertView;
    }
}