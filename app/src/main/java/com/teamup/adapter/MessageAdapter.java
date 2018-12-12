package com.teamup.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.model.ChatMessage;
import com.teamup.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    private Activity activity;
    private List<ChatMessage> messages = new ArrayList<>();
    private int MY_BUBBLE = 0, SENDER_BUBBLE = 1;

    public MessageAdapter(Activity context, int resource, List<ChatMessage> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage chatMessage){
        messages.add(chatMessage);
        notifyDataSetChanged();
    }


    public void clearList()
    {
        messages.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatMessage ChatMessage = getItem(position);
        int viewType = getItemViewType(position);

        if (getItemViewType(position) == SENDER_BUBBLE) {
            layoutResource = R.layout.teamname_teamtalk_left;
        } else {
            layoutResource = R.layout.teamname_teamtalk_right;

        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        if(getItemViewType(position) == SENDER_BUBBLE){
            FirebaseUtils.setUserFirstName(holder.sender, messages.get(position).getFrom());
        }

        //set message content
        holder.msg.setText(ChatMessage.getMessage());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime. Value 2 is returned because of left and right views.
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)

        if(messages.get(position).getFrom().equalsIgnoreCase(FirebaseUtils.getUId()))
            return MY_BUBBLE;

        return SENDER_BUBBLE;
    }

    private class ViewHolder {
        private TextView msg, sender;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            sender = v.findViewById(R.id.msg_sender);
        }
    }
}
