package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	void insert(DepartmentDAO obj);
	void update(DepartmentDAO obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
	
}
