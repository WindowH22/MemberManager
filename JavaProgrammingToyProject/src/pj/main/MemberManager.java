package pj.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MemberManager {
	MemberDAO dao = new MemberDAO();
	Member member = new Member();
	
	// 특정문자 개수 
	public int countChar(String str, char ch) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				count++;
			}
		}
		return count;
	}

	public void readMenu() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		boolean available = false;
		String m = "M-";

		// 무한반복문
		while (true) {
			bw.write("목록을 원하시면 1번을 입력하세요.\n");
			bw.write("등록을 원하시면 2번을 입력하세요.\n");
			bw.write("수정을 원하시면 3번을 입력하세요.\n");
			bw.write("삭제를 원하시면 4번을 입력하세요.\n");
			bw.write("종료를 원하시면 0번을 입력하세요.\n");
			bw.flush();
			int n = Integer.parseInt(br.readLine());

			// 회원 목록
			if (n == 1) {
				dao.getMemberList();

			} // 회원 등록
			else if (n == 2) {

				System.out.print("아이디를 입력하세요 (형식 M-00001): ");
				String memberID = br.readLine();

				// 아이디 입력하지 않은경우
				if ("".equals(memberID)) {
					System.out.println("아이디는 필수 입력항목입니다.");
					continue;
				}
				// 아이디 중복 검사
				else if (dao.getMemberListDetail(memberID)) {
					System.out.println(memberID + " 가 이미 존재합니다.");
					continue;
				}
				// 전체 문자의 길이가 7이 아닌 경우
				else if (memberID.length() != 7) {
					System.out.println("아이디는 'M-'로 시작해야 하며, M-를 포함하여 7개의 문자로 구성해야 합니다.");
					continue;
				}

				// 아이디가 “M-“로 시작하지 않는경우
				for (int i = 0; i < 2; i++) {
					if (m.charAt(i) != memberID.charAt(i)) {
						System.out.println("아이디는 'M-'로 시작해야 하며, M-를 포함하여 7개의 문자로 구성해야 합니다.");
						available = true;
					}
				}

				// 아이디가 유효성검사 불합격시 continue;
				if (available) {
					continue;
				}

				System.out.print("이름을 입력하세요 : ");
				String name = br.readLine();

				// 이름 미입력시
				if ("".equals(name)) {
					System.out.println("이름은 필수 입력항목입니다.");
					continue;
				}

				System.out.print("전화번호를 입력하세요 : ");
				String phoneNumber = br.readLine();
				if ("".equals(phoneNumber)) {
					System.out.println("전화번호는 필수 입력항목입니다.");
					continue;
				}
//			   만약 전화번호에 ‘-‘ 기호가 두개가 아니거나 ‘-’개를 포함하여 총 13개의 문자로 구성되지 않은 경우 다음과 같은 메시지를 출력한다. 
				if (!((countChar(phoneNumber, '-') == 2) && (phoneNumber.length() == 13))) {
					System.out.println("전화번호는 두 개의 '-'를 포함하여 총 13개의 문자로 구성해야 합니다.");
					continue;
				}

				bw.write("---> 회원가입에 성공하셨습니다. \n");

				bw.flush();
				dao.insertMember(memberID, name, phoneNumber);

			}
			// 회원 수정
			else if (n == 3) {
				System.out.print("수정할 아이디를 입력하세요 (형식 M-00001): ");
				String memberID = br.readLine();

				// 아이디 존재여부
				if (!(dao.getMemberListDetail(memberID))) {
					System.out.println("수정할 " + memberID + " 회원 정보가 존재하지 않습니다.");
					continue;
				}
				// 아이디 중복 검사
				else if (dao.getMemberListDetail(memberID)) {
					System.out.println(memberID + " 가 이미 존재합니다.");
					continue;
				}
				// 전체 문자의 길이가 7이 아닌 경우
				else if (memberID.length() != 7) {
					System.out.println("아이디는 'M-'로 시작해야 하며, M-를 포함하여 7개의 문자로 구성해야 합니다.");
					continue;
				}

				// 아이디가 “M-“로 시작하지 않는경우
				for (int i = 0; i < 2; i++) {
					if (m.charAt(i) != memberID.charAt(i)) {
						System.out.println("아이디는 'M-'로 시작해야 하며, M-를 포함하여 7개의 문자로 구성해야 합니다.");
						available = true;
					}
				}

				// 아이디가 유효성검사 불합격시 continue;
				if (available) {
					continue;
				}

				System.out.print("수정할 전화번호를 입력하세요 : ");
				String phoneNumber = br.readLine();
//				만약 전화번호에 ‘-‘ 기호가 두개가 아니거나 ‘-’개를 포함하여 총 13개의 문자로 구성되지 않은 경우 다음과 같은 메시지를 출력한다. 
				if (!((countChar(phoneNumber, '-') == 2) && (phoneNumber.length() == 13))) {
					System.out.println("전화번호는 두 개의 '-'를 포함하여 총 13개의 문자로 구성해야 합니다.");
					continue;
				}
				bw.write("---> 회원수정에 성공하셨습니다. \n");
				bw.flush();
				dao.updateMember(phoneNumber, memberID);
			}
			// 회원 삭제
			else if (n == 4) {
				System.out.print("삭제할 아이디를 입력하세요 (형식 M-00001): ");
				String memberID = br.readLine();

				// 아이디 존재여부
				if (!(dao.getMemberListDetail(memberID))) {
					System.out.println("삭제할" + memberID + "회원 정보가 존재하지 않습니다.");
					continue;
				}
				bw.write("---> " + memberID + "회원 삭제에 성공하셨습니다. \n");
				;
				bw.flush();
				dao.deleteStudent(memberID);
			}
			// 프로그램 종료
			else if (n == 0) {
				bw.write("##########################");
				bw.write("### GOOD-BYE 프로그램 종료 ###");
				bw.write("##########################");

				// 입출력 종료
				br.close();
				bw.flush();
				break;
			}
		}
	}
}
