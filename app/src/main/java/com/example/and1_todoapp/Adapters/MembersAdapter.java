package com.example.and1_todoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder>
{
    private String[] members;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://and1-todoapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    private OnClickListener listener;
    Context context;

    public MembersAdapter()
    {
        members = new String[]{};
    }

    public void setOnClickListener(MembersAdapter.OnClickListener listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view = inflater.inflate(R.layout.member_fragment, parent, false);
        return new MembersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.nickname.setText(members[position]);
    }

    @Override
    public int getItemCount()
    {
        return members.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView nickname;

        ViewHolder(View itemView)
        {
            super(itemView);
            this.nickname = itemView.findViewById(R.id.nickname);
            itemView.setOnClickListener(v -> listener.onClick(members[getBindingAdapterPosition()]));
        }
    }

    public interface OnClickListener
    {
        void onClick(String member);
    }

    public void getMemberData()
    {
        databaseReference.child("member").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                HashMap<String,String> value =(HashMap<String, String>)snapshot.getValue();

                members
                        = value.values().toArray(new String[0]);

                notifyDataSetChanged();

                for (int i=0; i<members.length; i++)
                {
                    System.out.println("!!!!!!!!!!!!"+members[i]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
