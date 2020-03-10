package com.example.menubar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
   private Inbox_fragment inbox_fragment;
   private LocationFragment locationFragment;
   private CallFragment callFragment;

   private ExpendListAdepter expendListAdepter;
   private ExpandableListView expandableListView;
   List<MenuModel>menuModelList=new ArrayList<>();
   HashMap<MenuModel, List<MenuModel>> chidlist= new HashMap<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerlayoutId);
        drawerLayout.setScrimColor(getResources().getColor(R.color.Transparant));

        toolbar=findViewById(R.id.tolMenubar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);

        frameLayout=findViewById(R.id.framId);
        homeFragment=new HomeFragment();
        inbox_fragment=new Inbox_fragment();
        locationFragment =new LocationFragment();
        callFragment=new CallFragment();


        setFragment(homeFragment);

        expandableListView=findViewById(R.id.expended_menuId);
        preParMenu();
        populateEpendablelist();



        navigationView=findViewById(R.id.navigationView);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_open,R.string.navigation_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        actionBarDrawerToggle.syncState();

        bottomNavigationView=findViewById(R.id.bottomNavMenuId);
        bottomNavigationView.setSelectedItemId(R.id.homeId);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.emailId:
                        setFragment(inbox_fragment);
                        return true;
                    case R.id.homeId:
                        setFragment(homeFragment);
                        return true;
                    case R.id.locationId:
                        setFragment(locationFragment);
                        return true;
                    case  R.id.callId:
                    setFragment(callFragment);
                    return true;
                        default:
                            return false;
                }

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id =item.getItemId();

                if(id==R.id.settingId) {
//                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar=Snackbar.make(drawerLayout,"Setting Is Clicked",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if(id==R.id.shareId) {
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.logoutId) {
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

    }

    private void preParMenu() {

        MenuModel menuModel=new MenuModel("test",true,true);
        menuModelList.add(menuModel);
        List<MenuModel> childModellist =new ArrayList<>();
        MenuModel childItem =new MenuModel("item1",false,false);
        childModellist.add(childItem);

        childItem=new MenuModel("item2",false,false);
        childModellist.add(childItem);

        if (menuModel.hasChild){
            chidlist.put(menuModel,childModellist);
        }
    }

    private void populateEpendablelist() {


        //menumodellist childlist
        expendListAdepter =new ExpendListAdepter(this,menuModelList,chidlist);
        expandableListView.setAdapter(expendListAdepter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (menuModelList.get(groupPosition).isGroup){

                    if (!menuModelList.get(groupPosition).hasChild){

                    }
                }




                return false;

            }
        });


    }


    private  void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.popo_enter,R.anim.pop_exit);
        fragmentTransaction.replace(R.id.framId,fragment);
        fragmentTransaction.commit();
    }



}

