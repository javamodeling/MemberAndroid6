package com.devpino.memberandroid;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devpino.memberandroid.databinding.EditLayoutBinding;

public class MemberEditActivity extends AppCompatActivity {

    private final static int CHOOSE_IMAGE = 100;

    private long no;

    EditLayoutBinding editLayoutBinding = null;

    private String photoUrl;

    private MemberDao  memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editLayoutBinding = DataBindingUtil.setContentView(this, R.layout.edit_layout);

        no = getIntent().getLongExtra("no", 0);

        AppDatabase appDatabase = AppDatabase.getSqliteDatabase(this);

        memberDao = appDatabase.memberDao();

        Member member = memberDao.getMember(no);

        editLayoutBinding.setMember(member);

        editLayoutBinding.editTextNo.setText(Long.toString(no));

        if (member.getGender().equals("M")) {
            editLayoutBinding.radioButtonMale.setChecked(true);
        }
        else {
            editLayoutBinding.radioButtonFemale.setChecked(true);
        }

        String[] jobs = getResources().getStringArray(R.array.jobs);

        int i = 0;

        for (; i < jobs.length; i++) {

            if (member.getJob().equals(jobs[i])) {
                break;
            }
        }

        editLayoutBinding.spinnerJob.setSelection(i);

        if (member.getPhotoUrl() != null) {

            editLayoutBinding.imageViewPhoto.setImageURI(Uri.parse(member.getPhotoUrl()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSave) {

            saveMember();
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveMember() {

        Member member = editLayoutBinding.getMember();

        if (member.getPassword() == null || member.getPassword().trim().length() == 0) {

            int result = MemberDialog.showAlertDialog("Password", getString(R.string.message_password_required), this);

            return;
        }

        boolean isMale = editLayoutBinding.radioButtonMale.isChecked();

        String job = editLayoutBinding.spinnerJob.getSelectedItem().toString();

        member.setMobileNo(member.getMobileNo1() + "-" + member.getMobileNo2() + "-" + member.getMobileNo3());

        if (photoUrl != null) {

            member.setPhotoUrl(photoUrl);
        }

        if (isMale) {
            member.setGender("M");
        }
        else {
            member.setGender("F");
        }

        member.setJob(job);

        memberDao.modifyMember(member);

        finish();
    }

    public void showPhoto(View view) {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a picture"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE) {

            Uri selectedImage = data.getData();
            editLayoutBinding.imageViewPhoto.setImageURI(selectedImage);

            photoUrl = selectedImage.toString();
        }
    }
}
