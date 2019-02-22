package com.farhachat.farha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import adapter.MessageAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Chat;
import model.User;

public class ChatActivity extends AppCompatActivity
{
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
     MessageAdapter messageAdapter;
     List<Chat> mchat;
     RecyclerView recyclerView;

    CircleImageView profile_image;
    TextView username;
    Intent intent;
    ImageButton btn_send;
    EditText txt_send;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.user_name);
        btn_send = findViewById(R.id.btn_send);
        txt_send = findViewById(R.id.txt_send);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        if(intent!=null&& intent.hasExtra("userid"))
        {
            userid = intent.getExtras().getString("userid");

        }


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURl().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                }
                else
                {
                    Glide.with(ChatActivity.this).load(user.getImageURl()).into(profile_image);
                }

                readMessage(firebaseUser.getUid(),userid,user.getImageURl());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void sendMessage(String sender,String receiver,String message)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Chat chat = new Chat(sender,receiver,message);
        reference.child("Chats").push().setValue(chat);
    }

    public  void onclick_btn_send(View view)
    {
        String msg = txt_send.getText().toString().trim();
        if (!msg.equals(""))
        {
            sendMessage(firebaseUser.getUid(),userid,msg);
        }
        else
        {
            Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show();
        }
        txt_send.setText("");
    }


    private void readMessage(final String myid, final String userid2, final String imageurl)
    {
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat chat =snapshot.getValue(Chat.class);
                    if((chat.getReceiver().equals(myid)&&chat.getSender().equals(userid2))||(chat.getReceiver().equals(userid2)&&chat.getSender().equals(myid)))
                    {
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(ChatActivity.this,mchat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
