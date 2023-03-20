package day17;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientService {
	// TODO Auto-generated method stub
	private static ClientService service = new ClientService();

	private ClientService() {
	}

	public static ClientService getInstance() {
		return service;
	}

	private Scanner sc = new Scanner(System.in);
	private ClientRepository repository = ClientRepository.getInstance();
	private String loginId = null;
	private String loginPassword = null;

	public void save() {
		ClientDTO clientDTO = new ClientDTO();
		while (true) {
			System.out.print("ID >>> ");
			clientDTO.setId(sc.next());
			if (repository.dupCheck(clientDTO.getId())) {
				System.out.println("이미 사용중인 아이디입니다. 다시 입력해주세요");
				continue;
			} else {
				break;
			}
		}
		System.out.print("PASSWORD >>> ");
		clientDTO.setPassword(sc.next());
		System.out.print("NAME >>> ");
		clientDTO.setName(sc.next());
		if (repository.save(clientDTO)) {
			System.out.println("회원가입 성공");
		} else {
			System.out.println("회원가입 실패");
		}

	}
	

	public boolean loginCheck() { // <-- Q)왜 Service에서는 매개변수를 안받고 repository에서 받는거죠?
		// 일단 ID하고 비밀번호 입력창
		System.out.print("ID >>> ");
		String id = sc.next();
		System.out.print("비밀번호 >>> ");
		String password = sc.next();

		// Repository로 가보자

		if (repository.loginCheck(id, password)) {
			System.out.println("로그인되었습니다.");
			loginId = id;
			loginPassword = password;
			return true;
		} else {
			System.out.println("ID 또는 비밀번호를 확인하세요");
			return false;
		}

	}

	public void findAll() {
		Map<String, ClientDTO> cMap = repository.findAll();
		System.out.println("계좌번호\t\t\t아이디\t\t비밀번호\t예금주\t잔액\t가입일");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		List<String> keySet = new ArrayList<>(cMap.keySet()); // <-- key값으로만 이루어진 리스트를 만들었다. 나중에 정렬 메뉴 만들때 쓰일 것 같다.
		for (String key : keySet) {
			cMap.get(key).print();
		}
	}

	public void findById() {
		ClientDTO clientDTO = repository.findById(loginId, loginPassword);

		if (clientDTO == null) {
			System.out.println("로그인 오류");

		} else {
			System.out.println("계좌번호\t\t\t아이디\t\t비밀번호\t예금주\t잔액\t가입일");
			System.out.println(
					"----------------------------------------------------------------------------------------------------");
			System.out.println(clientDTO);
			System.out.println(
					"----------------------------------------------------------------------------------------------------");
			List<BreakdownDTO> bList = repository.breakList(clientDTO.getAccount());
			if (bList.size() == 0) {
				System.out.println("입출금 내역이 없습니다");
			} else {
				System.out.println(" ▼ 입출금내역 ▼ ");
				System.out.println("계좌번호\t\t구분\t거래금액\t거래후잔액\t거래일");
				System.out.println(
						"----------------------------------------------------------------------------------------------------");
				for (BreakdownDTO b : bList) {
					System.out.println(b.toString());
				}

			}

		}

	}

	public void deposit() {
		String account = repository.getAccount(loginId, loginPassword);
		if (account == null) {
			System.out.println("로그인 오류");
		} else {
			System.out.print("입금금액 >>> ");
			long money = sc.nextLong();
			if (repository.deposit(account, money)) {
				System.out.println("입금되었습니다");
			} else {
				System.out.println("입금 실패");
			}
		}

	}

	public void withdraw() {
		String account = repository.getAccount(loginId, loginPassword);
		if (account == null) {
			System.out.println("로그인 오류");
		} else {
			System.out.print("출금금액 >>> ");
			long money = sc.nextLong();
			if (repository.withdraw(account, money)) {
				System.out.println("출금되었습니다.");
			} else {
				System.out.println("잔액이 부족합니다");
			}
		}
	}

	// 존재하지 않는 계좌로 이체가 되는 문제 발생
	public void transfer() {
		String account = repository.getAccount(loginId, loginPassword);
		if (account == null) {
			System.out.println("로그인 오류");
			return;
		}

		// 계좌이체 정보 입력
		System.out.print("이체할 계좌번호 >>> ");
		String transferAccount = sc.next();
		System.out.print("이체할 금액 >>> ");
		long transferMoney = sc.nextLong();

		// 이체할 계좌가 존재하는 계좌인가? --> 이걸 확인해보려면 repository에서 map을 불러와 훑어봐야겠지
		if (repository.transferCheck(transferAccount)) {
			// 내 계좌에 돈이 있는가?
			if (repository.withdraw(account, transferMoney)) {
				if (repository.deposit(transferAccount, transferMoney)) {
					System.out.println(transferAccount + " 계좌로 " + transferMoney + " 원을 이체합니다.");
				}
			} else {
				System.out.println("잔액이 부족합니다.");
			}
		} else {
			System.out.println("이체할 계좌를 찾을 수 없습니다.");
		}

	}

	public void update() {
		// 현재 비밀번호 입력
		System.out.print("비밀번호 확인 >>> ");
		String password = sc.next();
		// 입력받은 현재 비밀번호가 내 비밀번호와 일치하면
		if (password.equals(loginPassword)) {
			// 수정할 비밀번호 입력
			System.out.print("수정할 비밀번호 >>> ");
			String updatePassword = sc.next();
			// 이건 뭘까? repository의 update를 한 번 보자
			if (repository.update(loginId, loginPassword, updatePassword)) {
				// 아무튼,,, 위의 조건이 맞으면 loginPassword를 입력받은 updatePassword로 바꾸고 메세지를 띄워라
				loginPassword = updatePassword;
				System.out.println("비밀번호가 수정되었습니다.");
			} else {
				System.out.println("업데이트 오류");
			}

		} else {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}

	}

	public boolean delete() {
		if (repository.delete(loginId, loginPassword)) {
			System.out.println("계좌가 해지되었습니다. 안녕히가십시오.");
			return false;
		} else {
			System.out.println("어딜도망가려고ㅋㅋㅋㅋㅋㅋ");
			return true;
		}
	}

	public void logout() {
		loginId = null;
		loginPassword = null;
		System.out.println("로그아웃되었습니다.");
	}

}
