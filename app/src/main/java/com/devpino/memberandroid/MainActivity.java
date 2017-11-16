package com.devpino.memberandroid;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.devpino.memberandroid.databinding.Row2LayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Member> memberEntities = new ArrayList<>();

    private ListView listView;

    private MemberArrayAdapter adapter = null;

    private MemberDao memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);

        listView.setOnItemClickListener(onItemClickListenerListView);

        AppDatabase appDatabase = AppDatabase.getSqliteDatabase(this);

        memberDao = appDatabase.memberDao();

        requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAdd) {

            Intent intent = new Intent(this, MemberAddActivity.class);

            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {

        memberEntities = memberDao.searchAllMembers();

        adapter = new MemberArrayAdapter(this, memberEntities);

        listView.setAdapter(adapter);


    }


    private class MemberArrayAdapter extends ArrayAdapter<String> {

        private final Activity context;

        private final List<Member> memberEntities;

        public MemberArrayAdapter(Activity context, List<Member> memberEntities) {

            super(context, R.layout.row_layout);

            this.context = context;
            this.memberEntities = memberEntities;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Member memberEntity = memberEntities.get(position);

            Row2LayoutBinding row2LayoutBinding = DataBindingUtil.getBinding(convertView);

            // reuse views
            if (row2LayoutBinding == null) {

                LayoutInflater inflater = context.getLayoutInflater();

                row2LayoutBinding = DataBindingUtil.inflate(inflater, R.layout.row2_layout, parent, false);

            }

            row2LayoutBinding.textViewName.setText(memberEntity.getMemberName());
            row2LayoutBinding.textViewEmail.setText(memberEntity.getEmail());

            if(memberEntity.getPhotoUrl() != null) {

                row2LayoutBinding.imageViewPhoto.setImageURI(Uri.parse(memberEntity.getPhotoUrl()));
            }

            return row2LayoutBinding.getRoot();
        }

        @Override
        public int getCount() {

            return memberEntities.size();
        }
    }

    AdapterView.OnItemClickListener onItemClickListenerListView = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Member memberEntity = memberEntities.get(position);

            long no = memberEntity.getNo();

            Intent intent = new Intent(MainActivity.this, MemberViewActivity.class);
            intent.putExtra("no", no);

            startActivity(intent);
        }
    };
}
