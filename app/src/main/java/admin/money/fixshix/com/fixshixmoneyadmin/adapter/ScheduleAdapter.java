package admin.money.fixshix.com.fixshixmoneyadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import admin.money.fixshix.com.fixshixmoneyadmin.R;
import admin.money.fixshix.com.fixshixmoneyadmin.holder.ScheduleHolder;
import admin.money.fixshix.com.fixshixmoneyadmin.model.ScheduleModel;

/**
 * Created by lenovo on 9/18/2017.
 */

public class ScheduleAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<ScheduleModel> list = new ArrayList<>();
    Context context;

    public ScheduleAdapter(Context c, ArrayList<ScheduleModel> list ){
        inflater = LayoutInflater.from(c);
        this.context = c;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ScheduleHolder holder;
        final ScheduleModel item = (ScheduleModel)getItem(position);

        if (convertView == null)
        {

            holder = new ScheduleHolder();
            convertView  = inflater.inflate(R.layout.list_item_schedule,null,false);
            holder.id= (TextView)convertView.findViewById(R.id.t_id);
            holder.amount= (TextView)convertView.findViewById(R.id.amount);
            holder.status= (TextView)convertView.findViewById(R.id.status);
            holder.time= (TextView)convertView.findViewById(R.id.time);
            holder.method= (TextView)convertView.findViewById(R.id.method);
            holder.user_id= (TextView)convertView.findViewById(R.id.user_id);

            convertView.setTag(holder);
        }else
            holder=(ScheduleHolder)convertView.getTag();

            holder.id.setText(item.getId());
            holder.amount.setText(item.getAmount());
            holder.status.setText(item.getStatus());
            holder.time.setText(item.getTime());
              holder.method.setText(item.getMethod());
        holder.user_id.setText(item.getUser_id());

        return convertView;
    }
}
