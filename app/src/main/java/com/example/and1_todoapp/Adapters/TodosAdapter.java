package com.example.and1_todoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.Models.Todo;
import com.example.and1_todoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private ArrayList<Todo> todos;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://and1-todoapp-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    private TodosAdapter.OnClickListener listener;
    Context context;

    public TodosAdapter()
    {
        todos = new ArrayList<>();
    }

    public void getTodoData()
    {
        databaseReference.child("todos").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    todos.add(ds.getValue(Todo.class));
                }

                notifyDataSetChanged();
                for (int i=0; i<todos.size(); i++)
                {
                    System.out.println("!!!!!!!!!!!!!!!!!"+todos.get(i).getTitle());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(context, "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.todoName.setText(todos.get(position).getTitle());
        holder.todoDeadline.setText(todos.get(position).getDeadline().toString());
        holder.todoDescription.setText(todos.get(position).getDescription());
        holder.boardName.setText(todos.get(position).getBoard().getBoardName());
    }

    @Override
    public int getItemCount()
    {
        return todos.size();
    }

    public void setOnClickListener(TodosAdapter.OnClickListener listener)
    {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView todoName;
        private final TextView todoDeadline;
        private final TextView todoDescription;
        private final TextView boardName;

        ViewHolder(View itemView){
            super(itemView);
            this.todoName = itemView.findViewById(R.id.todo_title);
            this.todoDeadline = itemView.findViewById(R.id.todo_deadline);
            this.todoDescription = itemView.findViewById(R.id.todo_description);
            this.boardName = itemView.findViewById(R.id.boardName);
            itemView.setOnClickListener(v -> listener.onClick(todos.get(getBindingAdapterPosition())));
        }
    }

    public interface OnClickListener
    {
        void onClick(Todo board);
    }
}
