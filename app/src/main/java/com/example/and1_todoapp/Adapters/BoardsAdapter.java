package com.example.and1_todoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.Models.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.and1_todoapp.R;

import java.util.ArrayList;

public class BoardsAdapter extends RecyclerView.Adapter<BoardsAdapter.ViewHolder> {

    ArrayList<Board> boards;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://and1-todoapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    private BoardsAdapter.OnClickListener listener;
    Context context;

    public BoardsAdapter()
    {
        boards = new ArrayList<Board>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view = inflater.inflate(R.layout.board_fragment, parent, false);
        return new ViewHolder(view);
    }

    public void getBoardData()
    {
        databaseReference.child("board").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    boards.add(ds.getValue(Board.class));
                }

                notifyDataSetChanged();
                for (int i=0; i<boards.size(); i++)
                {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!"+boards.get(i).boardName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(context, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return boards.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.boardName.setText(boards.get(position).getBoardName());
        holder.boardOwner.setText(boards.get(position).getOwner().getNickname());
        holder.boardDescription.setText(boards.get(position).getBoardDescription());
    }

    public void setOnClickListener(OnClickListener listener)
    {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView boardName;
        TextView boardOwner;
        TextView boardDescription;

        ViewHolder(View itemView){
            super(itemView);
            this.boardName = itemView.findViewById(R.id.board_name);
            this.boardOwner = itemView.findViewById(R.id.board_owner);
            this.boardDescription = itemView.findViewById(R.id.board_description);
            itemView.setOnClickListener(v -> listener.onClick(boards.get(getBindingAdapterPosition())));
        }
    }

    public interface OnClickListener
    {
        void onClick(Board board);
    }
}
