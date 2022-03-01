package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmendDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		System.out.println("=== TST 1: seller findById ===");
		DepartmendDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== TST 1: seller findById ===");
		Department depart = departmentDao.findById(3);
		System.out.println(depart);
		
		System.out.println("=== TST 2: seller findAll ===");
		List<Department> listDep = new ArrayList<>();
		listDep = departmentDao.findAll();
		for (Department department : listDep) {
			System.out.println(department);
		}
		
		System.out.println("=== TST 3: seller Insert ===");
		Department dpt = new Department(null, "D3new");
		System.out.println("Antes do bd:"+dpt);
		departmentDao.insert(dpt);
		System.out.println("Depois do bd:"+dpt);
		
		System.out.println("=== TST 4: Update ===");
		dpt = departmentDao.findById(6);
		System.out.println("Id:"+dpt.getId());
		dpt.setName("dTeste");
		System.out.println("Name:"+dpt.getName());
		departmentDao.update(dpt);
		
		System.out.println("=== TST 5: Update ===");
		departmentDao.deleteById(34);
	}

}
