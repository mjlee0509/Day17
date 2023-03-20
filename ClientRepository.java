package day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientRepository {

	private static ClientRepository repository = new ClientRepository();

	private ClientRepository() {
	}

	public static ClientRepository getInstance() {
		return repository;
	}

	ClientDTO clientDTO = new ClientDTO();

	Map<String, ClientDTO> cMap = new HashMap<>();
	List<BreakdownDTO> bList = new ArrayList<>();
	
	Scanner sc = new Scanner(System.in);

	public boolean save(ClientDTO clientDTO) {
		ClientDTO result = cMap.put(clientDTO.getId(), clientDTO);
		if (result == null) {
			return true;
		}
		return false;

	}

//	public boolean save(ClientDTO clientDTO) {
//		if(cMap.put(clientDTO.getAccount(), clientDTO) == null) {
//			return true;
//		} else {
//				return false;
//		}
//	}
	
	public boolean dupCheck(String id) {
		while (true) {
			for (String key : cMap.keySet()) {
				if (cMap.get(key).getId().equals(id)) {
					return true;
				}
			}
			return false;
		}
	}

	public boolean loginCheck(String id, String password) {
		for (String key : cMap.keySet()) {
			if (id.equals(cMap.get(key).getId()) && password.equals(cMap.get(key).getPassword())) {
				return true;
			}
		}
		return false;
	}

	public Map<String, ClientDTO> findAll() {
		return cMap;
	}

	public ClientDTO findById(String id, String password) {
		for (String key : cMap.keySet()) {
			if (id.equals(cMap.get(key).getId()) && password.equals(cMap.get(key).getPassword())) {
				return cMap.get(key);
			}
		}
		return null;
	}

	public List<BreakdownDTO> breakList(String account) {
		List<BreakdownDTO> list = new ArrayList<>();
		for (BreakdownDTO b : bList) {
			if (b.getAccount().equals(account)) {
				list.add(b);
			}
		}
		return list;

	}

	public String getAccount(String id, String password) {
		for (String key : cMap.keySet()) {
			if (id.equals(cMap.get(key).getId()) && password.equals(cMap.get(key).getPassword())) {
				return cMap.get(key).getAccount();
			}
		}
		return null;
	}

	public boolean deposit(String account, long money) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(account)) {
				// 잔액 찍는거?
				cMap.get(key).setBalance(cMap.get(key).getBalance() + money);

				// 이건 뭘까...
				BreakdownDTO breakdownDTO = new BreakdownDTO();
				breakdownDTO.setAccount(account);
				breakdownDTO.setDivision("입금");
				breakdownDTO.setDealMoney(money);
				breakdownDTO.setTotalMoney(cMap.get(key).getBalance());

				bList.add(breakdownDTO);

				return true;

			}

		}
		return false;

	}

	public boolean withdraw(String account, long money) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getAccount().equals(account)) {
				if (cMap.get(key).getBalance() >= money) {
					cMap.get(key).setBalance(cMap.get(key).getBalance() - money);
					BreakdownDTO breakdownDTO = new BreakdownDTO();
					breakdownDTO.setAccount(account);
					breakdownDTO.setDivision("출금");
					breakdownDTO.setDealMoney(money);
					breakdownDTO.setTotalMoney(cMap.get(key).getBalance());
					bList.add(breakdownDTO);
					return true;
				} else {
					return false; // <-- 여기는 if문 빠져나오는 false;
				}
			}

		}
		return false; // <-- 여기는 for문 빠져나오는 false;

	}

	public boolean transferCheck(String transferAccount) {
		for (String key : cMap.keySet()) {
			return true;
		}
		return false;
	}

	public boolean update(String id, String password, String updatePassword) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(id) && cMap.get(key).getPassword().equals(password)) {
				cMap.get(key).setPassword(updatePassword);
				return true;
			}
		}
		return false;
	}

	public boolean delete(String id, String password) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(id) && cMap.get(key).getPassword().equals(password)) {
				cMap.remove(key);
				return true;
			}
		}
		return false;
	}
	
	
	public String clientCheck (String id, String password) {
		for (String key : cMap.keySet()) {
			if (cMap.get(key).getId().equals(id) && cMap.get(key).getPassword().equals(password)) {
				cMap.remove(key);
				return key;
			}
		}
		return null;

				
				
	}


}
