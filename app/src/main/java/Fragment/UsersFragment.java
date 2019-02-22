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
import model.User;

public class UsersFragment extends Fragment
{
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> users;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    public UsersFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<>();
        readUsers();

        return root;
    }

    private void readUsers()
    {
         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                users.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);

                    assert firebaseUser != null;
                    assert user != null;
                    if(!user.getId().equals(firebaseUser.getUid()))
                    {
                        users.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(),users);
                recyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
