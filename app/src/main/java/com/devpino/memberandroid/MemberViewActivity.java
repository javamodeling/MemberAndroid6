package com.devpino.memberandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.devpino.memberandroid.databinding.ViewLayoutBinding;

public class MemberViewActivity extends AppCompatActivity {

    private long no;

    private ViewLayoutBinding viewLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewLayoutBinding = DataBindingUtil.setContentView(this, R.layout.view_layout);

        no = getIntent().getLongExtra("no", 0);

    }

    private void loadData() {

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance();
        memberDbAdapter.open();

        Member member = memberDbAdapter.obtainMember(no);

        memberDbAdapter.close();

        viewLayoutBinding.setMember(member);

        if (member.getGender().equals("M")) {
            viewLayoutBinding.radioButtonMale.setChecked(true);
        }
        else {
            viewLayoutBinding.radioButtonFemale.setChecked(true);
        }

        String[] jobs = getResources().getStringArray(R.array.jobs);

        int i = 0;

        for (; i < jobs.length; i++) {

            if (member.getJob().equals(jobs[i])) {
                break;
            }
        }

        viewLayoutBinding.spinnerJob.setSelection(i);

        if (member.getPhotoUrl() != null) {

            viewLayoutBinding.imageViewPhoto.setImageURI(Uri.parse(member.getPhotoUrl()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.view_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemEdit) {

            Intent intent = new Intent(this, MemberEditActivity.class);

            intent.putExtra("no", no);
            startActivity(intent);

        }
        else if (item.getItemId() == R.id.itemDelete) {

            AlertDialog.Builder builder	= new AlertDialog.Builder(this);
            AlertDialog alert = null;

            builder.setMessage(getString(R.string.message_delete))
                    .setTitle(getString(R.string.delete))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            deleteMember();
                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});

            alert = builder.create();
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteMember() {

        MemberDbAdapter memberDbAdapter = MemberDbAdapter.getInstance();
        memberDbAdapter.open();

        boolean result = memberDbAdapter.removeMember(no);

        memberDbAdapter.close();

        if (result) {

            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }
}
