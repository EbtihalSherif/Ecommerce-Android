package com.example.newversion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItemArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_first, container, false);

       String user_Name=null;
        Bundle b =this.getArguments();
        if(b != null){
            user_Name=b.getString("user_Name");        }

        TextView userName=(TextView)view.findViewById(R.id.txt_username);
        userName.setText(user_Name);

        EcommerceDBHelper database = new EcommerceDBHelper(getActivity());
        Cursor c = database.Fetchcategories();
        ArrayList<Container_item> exampleList = new ArrayList<>();
      //  Toast.makeText(view.getContext(), String.valueOf( c.getColumnCount()) , Toast.LENGTH_SHORT).show();
        if (!c.isAfterLast()) {
            exampleList.add(new Container_item(R.drawable.fashion, c.getString(1), "Dresses,and more fashion related stuff"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.foodandbeverage, c.getString(1), "All kitchen food Needs"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.home, c.getString(1), "Furniture and more"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.makeup, c.getString(1), "Lips,Face,Nails Products"));
            c.moveToNext();

        }

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new ItemArrayAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ItemArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();


                Bundle bundle_data=new Bundle();
                bundle_data.putString("cat_id",String.valueOf(position+1) );

                Product_Fragement nextFrag= new Product_Fragement();
                nextFrag.setArguments(bundle_data);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });


        return view;

    }


}