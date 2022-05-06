package pj.main;
public class Member {
	private String memberID;
	private String name;
	private String phoneNumber;
	
	
	
	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return "----> MemberID=" + memberID+", name = "+ getName() +", phoneNumber="+getPhoneNumber() ;
	}
}
