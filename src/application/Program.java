package application;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDAO.findById(12);
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = sellerDAO.findByDepartment(department);
		for (Seller obj : list){
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		list = sellerDAO.findAll();
		for (Seller obj : list){
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
		sellerDAO.insert(newSeller);
		System.out.println("Inserted new id = " + newSeller.getId());
		
		System.out.println("\n=== TEST 5: seller update ===");
		seller = sellerDAO.findById(8);
		if(seller == null) {
			System.out.println("Id não encontrado!");
		}else {
			seller.setName("Martha Wayne");
			sellerDAO.update(seller);
			System.out.println("Update completed");
		}
	}

}
