package pj.main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class MemberDAO {

	//JDBC 관련 변수
	private Connection conn =null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	//MEMBER 테이블 관련 SQL 명령어
	private final String MEMBER_INSERT = "insert into member values(?,?,?)";
	private final String MEMBER_UPDATE = "update member set phone_number = ? where member_id= ?";
	private final String MEMBER_DELETE = "delete member where member_id = ?";
	private final String MEMBER_LIST = "select * from member order by member_id asc";
	private final String MEMBER_LIST_DETAIL = "select * from member where member_id = ?";
		
	Member member = new Member();
	
	public ArrayList<String> memberID = new ArrayList<>();
	
	//회원 등록
	public void insertMember(String memberID, String name, String phoneNumber) {
		try {
			conn = JDBCUtill.getConncetion();
			stmt = conn.prepareStatement(MEMBER_INSERT);
			stmt.setString(1, memberID);
			stmt.setString(2, name);
			stmt.setString(3, phoneNumber);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		finally {
			JDBCUtill.close(stmt, conn);
		}
	}
	
	//회원 수정
	public void updateMember(String phoneNumber,String memberID) {
		try {
			conn = JDBCUtill.getConncetion();
			stmt = conn.prepareStatement(MEMBER_UPDATE);
			stmt.setString(1, phoneNumber);
			stmt.setString(2, memberID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtill.close(stmt, conn);
		}
	}
	
	
	// 회원 삭제
	public void deleteStudent(String memberID) {
		try {
			conn = JDBCUtill.getConncetion();
			stmt = conn.prepareStatement(MEMBER_DELETE);
			stmt.setString(1, memberID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtill.close(stmt, conn);
		}
	}
	
	//회원 조회
	public void getMemberList() {
		try {
			conn = JDBCUtill.getConncetion();
			stmt = conn.prepareStatement(MEMBER_LIST);
			rs = stmt.executeQuery();
			
			//읽어들일 데이터가 없으면 회원이 없다는 메시지 출력
			if (rs.next() == false) {
			    System.out.println("등록된 회원이 없습니다.");
			}else { //회원 목록이 있다면 회원목록을 출력한다.
				System.out.println("현재 등록된 회원 목록입니다.");
				rs = stmt.executeQuery();
			}
			
			while(rs.next()){
				member.setMemberID(rs.getString("MEMBER_ID"));
				member.setName(rs.getString("NAME"));
				member.setPhoneNumber(rs.getString("PHONE_NUMBER"));
				//toString 메서드  사용
				System.out.println(member);	 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtill.close(rs, stmt,conn);
		}
	}
		
	//회원 상세조회
	public boolean getMemberListDetail(String memberId) {
		try {
			conn = JDBCUtill.getConncetion();
			stmt = conn.prepareStatement(MEMBER_LIST_DETAIL);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			
			if(rs.next()) {			
				return true;
			}else {
				return false;
			}
			
		} catch (SQLException e) {
			System.out.println("sqlexception");
			e.printStackTrace();
		} finally {
			JDBCUtill.close(rs, stmt,conn);
		}
		return false;	
	}
}
