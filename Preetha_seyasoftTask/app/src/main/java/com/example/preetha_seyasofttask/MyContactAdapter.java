package com.example.preetha_seyasofttask;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ContactInfo> contactsInfoList;
    private Context context;
    Dialog dialog;

    public MyContactAdapter(Context context,List<ContactInfo> contactsInfoList) {
        this.contactsInfoList = contactsInfoList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            MyHolder holder1 = (MyHolder) holder;

            ContactInfo contactsInfo = contactsInfoList.get(position);
            holder1.name.setText(contactsInfo.getDisplayName());
            holder1.mobile.setText(contactsInfo.getPhoneNumber());

            holder1.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_image);

                    dialog.setCanceledOnTouchOutside(true);

                    int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.5f);
                    int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.5f);

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


        }
    }

    @Override
    public int getItemCount() {
        return contactsInfoList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        RecyclerView.ViewHolder viewHolder;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mycontacts, parent, false);
        viewHolder = new MyHolder(itemView);


        return viewHolder;

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView mobile;
        CircleImageView imageView;
        ConstraintLayout list_view;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            mobile = (TextView) itemView.findViewById(R.id.textView5);
            imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
            list_view = (ConstraintLayout) itemView.findViewById(R.id.list_view);
        }
    }

}

