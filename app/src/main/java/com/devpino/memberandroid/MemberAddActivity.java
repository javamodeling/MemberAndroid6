package com.devpino.memberandroid;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devpino.memberandroid.databinding.AddLayoutBinding;

public class MemberAddActivity extends AppCompatActivity {

    private final static int CHOOSE_IMAGE = 100;

    private String photoUrl;

    private AddLayoutBinding addLayoutBinding;

    private MemberDao memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addLayoutBinding = DataBindingUtil.setContentView(this, R.layout.add_layout);

        addLayoutBinding.setMember(new Member());

        AppDatabase appDatabase = AppDatabase.getSqliteDatabase(this);

        memberDao = appDatabase.memberDao();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_menu, menu);

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

        Member member = addLayoutBinding.getMember();

        if (member.getEmail() == null || member.getEmail().trim().length() == 0) {

            int result = MemberDialog.showAlertDialog("Email", getString(R.string.message_email_required), this);

            return;
        }
        else if (!isEmailValid(member.getEmail())) {

            int result = MemberDialog.showAlertDialog("Email", getString(R.string.message_email_invalid), this);

            return;
        }

        if (member.getMemberName() == null || member.getMemberName().trim().length() == 0) {

            int result = MemberDialog.showAlertDialog("Name", getString(R.string.message_name_required), this);

            return;
        }

        if (member.getPassword() == null || member.getPassword().trim().length() == 0) {

            int result = MemberDialog.showAlertDialog("Password", getString(R.string.message_password_required), this);

            return;
        }

        boolean isMale = addLayoutBinding.radioButtonMale.isChecked();

        String job = addLayoutBinding.spinnerJob.getSelectedItem().toString();

        member.setMobileNo(member.getMobileNo1() + "-" + member.getMobileNo2() + "-" + member.getMobileNo3());

        member.setPhotoUrl(photoUrl);

        if (isMale) {
            member.setGender("M");
        }
        else {
            member.setGender("F");
        }

        member.setJob(job);

        memberDao.addMember(member);

        finish();
    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
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
            addLayoutBinding.imageViewPhoto.setImageURI(selectedImage);

            photoUrl = selectedImage.toString();
        }
    }
}
