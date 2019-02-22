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
import java.util.List;
import model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.hold>
{
    private List<User> users;
    private Context context;


    public UserAdapter(Context context ,List<User> users )
    {
        this.users = users;
        this.context = context;
    }

    @Override
    public hold onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View V = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);
        return new hold(V);
    }

    @Override
    public void onBindViewHolder(hold holder, int position)
    {
        holder.username.setText(users.get(position).getUsername());
        if (users.get(position).getImageURl().equals("default"))
        {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(context).load(users.get(position).getImageURl()).into(holder.profile_image);
        }


    }

    @Override
    public int getItemCount() {
        return users!=null?users.size():0;
    }


    class hold extends RecyclerView.ViewHolder
    {
        ImageView profile_image;
        TextView username;
        public hold(View itemView)
        {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.user_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context,ChatActivity.class);
                    intent.putExtra("userid",users.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });


        }

    }
}
