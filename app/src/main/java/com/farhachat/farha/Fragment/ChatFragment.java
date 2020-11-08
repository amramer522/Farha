package com.farhachat.farha.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farhachat.farha.Notifications.Token;
import com.farhachat.farha.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.farhachat.farha.adapter.UserAdapter;
import com.farhachat.farha.model.Chat;
import com.farhachat.farha.model.User;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    List<String> userlist;
    private ProgressDialog progressDialog;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        progressDialog = new ProgressDialog(getContext());


        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        userlist = new ArrayList<>();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(fuser.getUid()))
                    {
                        userlist.add(chat.getReceiver());
                    }

                    if (chat.getReceiver().equals(fuser.getUid())) {
                        userlist.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        updateToken(FirebaseInstanceId.getInstance().getToken());

        return root;
    }


    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }


    private void readChats()
    {


        LinkedHashSet<String> hashSet = new LinkedHashSet<>(userlist);
        userlist = new ArrayList<>(hashSet);
//        Toast.makeText(getContext(), ""+hashSet.size(), Toast.LENGTH_SHORT).show();

        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(getContext(), "" + userlist.size(), Toast.LENGTH_SHORT).show();
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);
                    for (String id : userlist)
                    {
                        if (user.getId().equals(id))
                        {
                             mUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers,true);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    }

}
