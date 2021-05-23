package com.example.newversion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Cart_item_container> user_cart=new ArrayList<>();
    public static String currrent_user_ID=null;
    private RecyclerView mRecyclerView;
    private ItemArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String user_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b =getIntent().getExtras();
        if(b != null){
            user_Name=b.getString("username");
            currrent_user_ID=b.getString("userid");
        }

     /*   RecyclerView cat_view = (RecyclerView) findViewById(R.id.recycler_view);
        ArrayList<Container_item> exampleList = new ArrayList<>();

        EcommerceDBHelper database = new EcommerceDBHelper(getApplicationContext());
        Cursor c = database.Fetchcategories();
        if (!c.isAfterLast()) {
            exampleList.add(new Container_item(R.drawable.fashion, c.getString(1), "Line 2"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.foodandbeverage, c.getString(1), "Line 2"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.home, c.getString(1), "Line 2"));
            c.moveToNext();

            exampleList.add(new Container_item(R.drawable.makeup, c.getString(1), "Line 2"));
            c.moveToNext();

        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
          mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemArrayAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new ItemArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent products_intent=new Intent(MainActivity.this,Products.class);
                products_intent.putExtra("category_ID",position+1);
                MainActivity.this.startActivity(products_intent);

            }
        });

*/


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FirstFragment()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_cart:
                            selectedFragment = new Cart_Fragement();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.nav_home:
                            Bundle bundle_data=new Bundle();
                            bundle_data.putString("user_Name", user_Name);
                            selectedFragment = new FirstFragment();
                            selectedFragment.setArguments(bundle_data);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;

                            case R.id.nav_search:
                            selectedFragment = new search_Fragement();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        selectedFragment).commit();
                            break;

                        case R.id.nav_graphOrder:
                            selectedFragment = new Graph_fragement();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;


                    }

                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}