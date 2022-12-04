package com.example.and1_todoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and1_todoapp.R;
import com.example.and1_todoapp.Adapters.MembersAdapter;

public class MembersFragment extends Fragment
{
    RecyclerView membersList;
    MembersAdapter membersAdapter;
    Context context;

    public MembersFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = container.getContext();
        View view = inflater.inflate(R.layout.members_fragment, container, false);
        membersList = view.findViewById(R.id.recyclerViewMember);
        membersList.hasFixedSize();
        membersList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        membersAdapter = new MembersAdapter();

        membersAdapter.getMemberData();
        membersList.setAdapter(membersAdapter);

        return view;
    }
}
