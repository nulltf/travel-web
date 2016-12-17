package com.trip.vo;

import java.util.Date;

public class UserVo extends BaseSearchVO {
	
	private int id ;

	private String mobileNum;

	private String userAccount;

	private String userName;

	private String password;

	private String province;

	private String city;

	private String email;

	private int loginFirst;

	private String photoUrl;

	private Date createTime;

	private int age ;

	private String university;

	private String company;

	private String sex ;
	
	private String fansCount;
	
	private String followCount;
	
	private String blogCount;
	
	private String friendship;

	public String getFriendship() {
		return friendship;
	}

	public void setFriendship(String friendship) {
		this.friendship = friendship;
	}

	public String getFansCount() {
		return fansCount;
	}

	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}

	public String getFollowCount() {
		return followCount;
	}

	public void setFollowCount(String followCount) {
		this.followCount = followCount;
	}

	public String getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(String blogCount) {
		this.blogCount = blogCount;
	}

	public int getAge() {
		return age;
	}

	public String getCity() {
		return city;
	}

	public String getCompany() {
		return company;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

	public int getLoginFirst() {
		return loginFirst;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public String getPassword() {
		return password;
	}

	

	public String getProvince() {
		return province;
	}

	public String getSex() {
		return sex;
	}

	public String getUniversity() {
		return university;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLoginFirst(int loginFirst) {
		this.loginFirst = loginFirst;
	}
	
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setUniversity(String university) {
		this.university = university;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
