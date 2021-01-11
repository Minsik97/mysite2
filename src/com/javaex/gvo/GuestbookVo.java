package com.javaex.gvo;

public class GuestbookVo {
	
	//필드
	public int no;
	public String name;
	public String password;
	public String content;
	public String reg_date;
	
	//생성자
	public GuestbookVo() {}
	
	public GuestbookVo(int no, String password) {
		this.no = no;
		this.password = password;
	}

	public GuestbookVo(String name, String password, String content, String regDate) {
		this.name = name;
		this.password = password;
		this.content = content;
		this.reg_date = regDate;
	}

	public GuestbookVo(int no, String password, String content, String regDate) {
		this.no = no;
		this.password = password;
		this.content = content;
		this.reg_date = regDate;
	}

	public GuestbookVo(int no, String name, String password, String content, String regDate) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.content = content;
		this.reg_date = regDate;
	}
	
	//메소드 g/s
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return reg_date;
	}

	public void setRegDate(String regDate) {
		this.reg_date = regDate;
	}
	
	//메소드 일반
	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content
				+ ", regDate=" + reg_date + "]";
	}
	
	

}
