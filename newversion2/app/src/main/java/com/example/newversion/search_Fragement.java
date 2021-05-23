package com.example.newversion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class search_Fragement extends Fragment {
    private static final int REQUEST_CODE = 1234;
    private final int CAMERA_REQUEST = 1888;
    private ImageView myCaptured_image;
    private RecyclerView mRecyclerView;
    private ItemArrayAdapter mAdapter;
    private Products_recycler_Adapter mAdapterProduct;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<product_item_container> product_list;
    ArrayList<Container_item> exampleList;

    SearchView search;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        search = (SearchView) view.findViewById(R.id.search_bar);
        Spinner searchby = (Spinner) view.findViewById(R.id.spinner_options);
      //  RadioButton Rd_cat=(RadioButton)view.findViewById(R.id.rd_category);
      //  RadioButton Rd_prod=(RadioButton)view.findViewById(R.id.rd_product);

        ImageButton speakButton = (ImageButton) view.findViewById(R.id.imgBtn_rec);
        ImageButton CaptureButton = (ImageButton) view.findViewById(R.id.imgBtn_cam);
     //   myCaptured_image = (ImageView) view.findViewById(R.id.captured_img);
        CaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraIntent, CAMERA_REQUEST);*/
                IntentIntegrator integ = IntentIntegrator.forSupportFragment(search_Fragement.this);
                integ.setCaptureActivity(CaptureAct.class);
                integ.setOrientationLocked(false);
                integ.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integ.setPrompt("scanning code..");
                integ.initiateScan();


            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakButtonClicked(v);
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String search_by = searchby.getSelectedItem().toString();
                if ( search_by.equals("Category")) {

                    EcommerceDBHelper database = new EcommerceDBHelper(getActivity());
                    Cursor c = database.searchCategory(search.getQuery().toString());
                    exampleList = new ArrayList<>();
                    exampleList.clear();
                    Toast.makeText(view.getContext(), String.valueOf(c.getColumnCount()), Toast.LENGTH_SHORT).show();
                    if (!c.isAfterLast()) {
                        exampleList.add(new Container_item(0, c.getString(1), " "));
                        c.moveToNext();

                    }

                    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_search);
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

                    return true;
                } else if ( search_by.equals("Product")) {

                    product_list = new ArrayList<>();

                    EcommerceDBHelper database = new EcommerceDBHelper(getActivity());
                    Cursor c = database.searchProducts(search.getQuery().toString());
                    while (!c.isAfterLast()) {
                        product_list.add(new product_item_container(c.getString(2), c.getString(1), c.getString(3)));
                        c.moveToNext();
                    }
                    // product_list.add(new product_item_container("60","testtt","10"));

                    mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_search);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mAdapterProduct = new Products_recycler_Adapter(product_list);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapterProduct);
                    mAdapterProduct.setOnItemClickListener(new Products_recycler_Adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            String Prod_Name = product_list.get(position).getTextName();
                            String Prod_price = product_list.get(position).getmtxtprice();
                            String Prod_Qty = product_list.get(position).getTextqty();

                            Bundle bundle_data = new Bundle();
                            bundle_data.putString("Name", Prod_Name);
                            bundle_data.putString("Price", Prod_price);
                            bundle_data.putString("Max_qty", Prod_Qty);

                            Product_details_Fragement nextFrag = new Product_details_Fragement();
                            nextFrag.setArguments(bundle_data);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();

                        }
                    });
                    return true;

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });


            return view;
        }

        public void speakButtonClicked (View v){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, REQUEST_CODE);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null)
        {
            if(result.getContents()!=null){
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage(result.getContents());
                builder.setTitle("scanning result");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        search.setQuery(result.getContents(), true);

                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
            else
            {
                Toast.makeText(getActivity(), "no result", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);

        }
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            if (matches != null) {
                if (matches.size() > 0) {
                    search.setQuery(matches.get(0), true);

                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap myimg = (Bitmap) data.getExtras().get("data");
            myCaptured_image.setImageBitmap(myimg);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
