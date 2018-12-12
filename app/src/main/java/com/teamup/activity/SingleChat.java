package com.teamup.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.teamup.R;
import com.teamup.adapter.MessageAdapter;
import com.teamup.model.ChatMessage;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SingleChat extends BaseActivity {

    LinearLayout btn_send;
    MessageAdapter msgAdapter;
    ListView listView;
    private List<ChatMessage> chatMessages =new ArrayList<>();
    EditText chat_text;
    boolean myMessage = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        messageAdapter();
/*        setBackText();
        setHeadValue("Mario Collins",context);*/
        setToolbar("Mario Collins");
//        rightIconListener();
        sendMsgListener();
    }



    private void sendMsgListener() {
        chat_text=findViewById(R.id.chat_text);
        btn_send=findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_text.getText().toString().trim().equals("")){
                    Toast.makeText(context, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    ChatMessage ChatMessage = new ChatMessage();
                    ChatMessage.setMessage(chat_text.getText().toString());

                    chatMessages.add(ChatMessage);
                    msgAdapter.notifyDataSetChanged();
                    chat_text.setText("");
                    if (myMessage) {
                        myMessage = false;
                    } else {
                        myMessage = true;
                    }
                }
            }
        });
    }

    /* righ icon listener  */

    private void rightIconListener() {

        AppCompatImageView flagClick=changeRightIcon(R.mipmap.flag_icon,context);
        flagClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(new ContextThemeWrapper(context, R.style.DialogSlideAnim));
                dialog.setContentView(R.layout.edit_bottom_list);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                lp.windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setAttributes(lp);

                // set the custom dialog components - text, image and button
                TextView editList = (TextView) dialog.findViewById(R.id.edit_list);
                TextView deleteList = (TextView) dialog.findViewById(R.id.delete_list);
                TextView cancel = (TextView) dialog.findViewById(R.id.cancel_pop);

                editList.setText("Report Mario Collins");
                deleteList.setText("Block Mario Collins");
                deleteList.setTextColor(ContextCompat.getColor(context,R.color.red));


                editList.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                deleteList.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                cancel.setTypeface(CommonUtils.setSFProDisplaySemibold(context));

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                deleteList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void messageAdapter() {
        listView=findViewById(R.id.list_msg);
        msgAdapter = new MessageAdapter(this, R.layout.left_chat_bubble, chatMessages);
        listView.setAdapter(msgAdapter);
    }

    String TAG = SingleChat.class.getName();
    PopupMenu popupMenu;
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        Log.d(TAG, "onCreateOptionsMenu: ");
        final MenuItem flag = menu.add(101,101,101,"Options");
        flag.setIcon(R.drawable.baseline_flag_24_px);
        flag.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CommonUtils.hideSoftKeyboard(context);

        final View anchorView = findViewById(item.getItemId());
        popupMenu = new PopupMenu(this, findViewById(item.getItemId()));
        popupMenu.getMenu().add(0,0,0,"Report Mario Collins");
        MenuItem block =popupMenu.getMenu().add(1,1,1,"Block Mario Collins");

        SpannableString spannableString = new SpannableString("Block Mario Collins");
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        block.setTitle(spannableString);


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItem.getItemId()==0){
                    Snackbar.make(anchorView,"Working on Report", Snackbar.LENGTH_LONG ).show();
                }
                else{
                    Snackbar.make(anchorView,"Working on Block", Snackbar.LENGTH_LONG ).show();
                }
                return false;
            }
        });

        popupMenu.show();
        return super.onOptionsItemSelected(item);
    }
}
