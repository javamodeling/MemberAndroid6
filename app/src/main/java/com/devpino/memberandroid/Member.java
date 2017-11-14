package com.devpino.memberandroid;

import java.io.Serializable;

public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6800961906229486245L;

	private long no;
	private String memberName = null;
	private String email = null;
	private String password = null;
	private String address = null;
	private String mobileNo = null;
	private String gender = null;
	private String homepage = null;
	private String job = null;
	private String comments = null;
	private String photoUrl = null;

    public static final String KEY_ADDRESS = "address";
    public static final String KEY_COMMENTS = "comments";
    public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_HOMEPAGE = "homepage";
    public static final String KEY_JOB = "job";
    public static final String KEY_MEMBER_NAME = "member_name";
    public static final String KEY_MOBILE_NO = "mobile_no";
    public static final String KEY_PHOTO_URL = "photo_url";
    public static final String KEY_ROWID = "_id";
    
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String mobileNo1;
	private String mobileNo2;
	private String mobileNo3;

	public String getMobileNo1() {

		if (mobileNo1 == null && mobileNo != null) {

			int index = mobileNo.indexOf("-");
			String mNo1 = mobileNo.substring(0, index);

			return mNo1;
		}
		else {

			return mobileNo1;
		}

	}

	public String getMobileNo2() {

		if (mobileNo2 == null && mobileNo != null) {

			int index = mobileNo.indexOf("-");

			int index2 = mobileNo.lastIndexOf("-");

			String mNo2 = mobileNo.substring(index + 1, index2);

			return mNo2;
		}
		else {

			return mobileNo2;
		}
	}

	public String getMobileNo3() {

		if (mobileNo3 == null && mobileNo != null) {

			int index2 = mobileNo.lastIndexOf("-");
			String mNo3 = mobileNo.substring(index2 + 1);

			return mNo3;
		}
		else {

			return mobileNo3;
		}
	}

	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public void setMobileNo3(String mobileNo3) {
		this.mobileNo3 = mobileNo3;
	}
}
