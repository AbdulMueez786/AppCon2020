package com.example.appcon;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Home extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private RecyclerView rv;

    private  String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=findViewById(R.id.tablayout);
       // appBarLayout=findViewById(R.id.appbarid);
        viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragments(new FragmentHome(),"Home");
        adapter.AddFragments(new FragmentProfile(),"Profile");
        adapter.AddFragments(new FragmentList(),"peers");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.person);
        tabLayout.getTabAt(2).setIcon(R.drawable.group);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchid);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Type here to Search Peer");
        //searchView.setBackgroundColor(getResources().getColor(R.color.heading_color));
        return super.onCreateOptionsMenu(menu);
    }
}