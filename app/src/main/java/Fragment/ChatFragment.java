package Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.farhachat.farha.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.UserAdapter;
import model.Chat;
import model.User;

public class ChatFragment extends Fragment
{

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    List<String> userlist;

    public ChatFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        userlist = new ArrayList<>();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                userlist.clear();
            for (DataSnapshot snapshot:dataSnapshot.getChildren())
            {
                Chat chat = snapshot.getValue(Chat.class);
                if(chat.getSender().equals(fuser.getUid()))
                {
                    userlist.add(chat.getReceiver());
                }

                if (chat.getReceiver().equals(fuser.getUid()))
                {
                    userlist.add(chat.getSender());
                }
            }
           readChats();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return root;
    }

    private void readChats()
    {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);
                    for (String id:userlist)
                    {
                        if (user.getId().equals(id))
                        {
                            if (mUsers.size() != 0)
                            {
//                                for (User user1 : mUsers)
//                                {
//                                    if (!user1.getUsername().equals(user.getUsername()))
//                                    {
//                                        mUsers.add(user1);
//                                    }
//                                }
                               // Toast.makeText(getContext(), "mUsers size : "+mUsers.size(), Toast.LENGTH_SHORT).show();
                            }
                            else
                                {
                                mUsers.add(user);
                            }
                          // mUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
