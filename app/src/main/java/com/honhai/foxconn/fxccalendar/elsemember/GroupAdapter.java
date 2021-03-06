package com.honhai.foxconn.fxccalendar.elsemember;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.elsemember.GroupMemberList.GroupMemberActivity;
import com.honhai.foxconn.fxccalendar.elsemember.photo.PhotoSelectActivity;
import com.honhai.foxconn.fxccalendar.main.MainActivity;

import java.util.List;

import static com.honhai.foxconn.fxccalendar.elsemember.photo.PhotoSelectActivity.INTENT_GROUP_OBJECT_ID;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.READ_REQUESTCODE;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<MemberList> mList;
    private String groupName;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView groupImage;
        TextView groupName, groupSignature;
        View groupView;

        public ViewHolder(View itemView) {
            super(itemView);
            groupView = itemView;
            groupImage = itemView.findViewById(R.id.imv_avatar);
            groupName = itemView.findViewById(R.id.tv_group_name);
            groupSignature = itemView.findViewById(R.id.tv_group_signature);


        }
    }

    public GroupAdapter(List<MemberList> memberLists) {

        mList = memberLists;
    }

    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_manager_list_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MemberList member = mList.get(position);
                //Toast.makeText(v.getContext(), "???????????????", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                for (Group group : Data.getInstance().getGroups()) {
                    if (member.getName().equals(group.getGroupName())) {
                        Data.getInstance().refreshUserNamesByGroup(group);
                        break;
                    }
                }
                intent.setClass(v.getContext(), GroupMemberActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(
                        v.getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(
                            (MainActivity) v.getContext(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            READ_REQUESTCODE);
                else {
                    Intent intent = new Intent();
                    for (Group group : Data.getInstance().getGroups()) {
                        if (group.getGroupName().equals(holder.groupName.getText().toString())) {
                            intent.putExtra(INTENT_GROUP_OBJECT_ID, group.getObjectId());
                            break;
                        }
                    }
                    String s = intent.getStringExtra(INTENT_GROUP_OBJECT_ID);
                    if (s == null || s.length() == 0)
                        return;
                    intent.setClass(v.getContext(), PhotoSelectActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder viewHolder, int position) {
        MemberList member = mList.get(position);
        viewHolder.groupImage.setImageResource(member.getImageID());
        viewHolder.groupName.setText(member.getName());
        Drawable drawable = viewHolder.groupImage.getDrawable();
        viewHolder.groupImage.getDrawable().setTint(Data.getInstance().getThemeColor());

        String objectId = member.getGroupObjectId();
        String photoData = viewHolder.groupImage.getContext().getFilesDir().getPath() + "/" + objectId + ".jpg";
        RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(drawable);
        ImageView imageView = viewHolder.groupImage;
        Glide.with(imageView)
                .load(photoData)
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
