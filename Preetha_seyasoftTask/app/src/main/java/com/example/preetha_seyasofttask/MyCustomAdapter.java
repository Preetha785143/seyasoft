package com.example.preetha_seyasofttask;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyCustomAdapter extends ArrayAdapter {

    private List<ContactInfo> contactsInfoList;
    private Context context;
    Dialog dialog;

    public MyCustomAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.contactsInfoList = objects;
        this.context = context;
    }

    private class ViewHolder {
        TextView name;
        TextView mobile;
        CircleImageView imageView;
        ConstraintLayout list_view;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_mycontacts, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView);
            holder.mobile = (TextView) convertView.findViewById(R.id.textView5);
            holder.imageView = (CircleImageView) convertView.findViewById(R.id.imageView);
            holder.list_view = (ConstraintLayout) convertView.findViewById(R.id.list_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ContactInfo contactsInfo = contactsInfoList.get(position);
        holder.name.setText(contactsInfo.getDisplayName());
        //holder.email.setText(contactsInfo.getPhoneNumber());
        holder.mobile.setText(contactsInfo.getPhoneNumber());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_image);

                dialog.setCanceledOnTouchOutside(true);

                int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.5f);
                int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.5f);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                int dialogWindowWidth = (int) (width * 0.5f);
                int dialogWindowHeight = (int) (height * 0.5f);
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                dialog.getWindow().setAttributes(layoutParams);

                ImageView imageView2 = (ImageView) dialog.findViewById(R.id.imageView2);

                imageView2.setImageResource(R.drawable.sample);

                dialog.show();
            }
        });



        return convertView;
    }


}