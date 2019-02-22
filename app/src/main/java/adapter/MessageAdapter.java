package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.farhachat.farha.ChatActivity;
import com.farhachat.farha.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import model.Chat;
import model.User;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.hold>
{
    public static final  int MSG_TYPE_LEFT = 0;
    public static final  int MSG_TYPE_RIGHT = 1;
private List<Chat> mchat;
private Context context;
private String imageurl;
private FirebaseUser fuser;


public MessageAdapter(Context context ,List<Chat> mchat,String imageurl )
        {
        this.mchat = mchat;
        this.context = context;
        this.imageurl = imageurl;
        }

@Override
public hold onCreateViewHolder(ViewGroup parent, int viewType)
        {
            if (viewType == MSG_TYPE_RIGHT) {
                View V = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
                return new hold(V);
            }
            else
            {
                View V = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
                return new hold(V);
            }
        }

@Override
public void onBindViewHolder(hold holder, int position)
        {
            Chat chat = mchat.get(position);
            holder.show_message.setText(chat.getMessage());

            if (imageurl.equals("default"))
            {
                holder.profile_image.setImageResource(R.mipmap.ic_launcher);
            }
            else
            {
                Glide.with(context).load(imageurl).into(holder.profile_image);
            }

        }

@Override
public int getItemCount() {
        return mchat!=null?mchat.size():0;
        }


class hold extends RecyclerView.ViewHolder
{
    ImageView profile_image;
    TextView show_message;
    public hold(View itemView)
    {
        super(itemView);
        profile_image = itemView.findViewById(R.id.profile_image);
        show_message = itemView.findViewById(R.id.show_message);


    }

}

    @Override
    public int getItemViewType(int position) {
    fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mchat.get(position).getReceiver().equals(fuser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
