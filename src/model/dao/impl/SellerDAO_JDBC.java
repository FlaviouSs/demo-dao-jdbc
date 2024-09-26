package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAO_JDBC implements SellerDAO{

	private Connection conn = null;
	
	public SellerDAO_JDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT s.id, s.name, s.email, s.birth_date, s.base_salary, s.department_id, d.name as DepName FROM seller s"
					+ " JOIN department d ON s.department_id = d.id WHERE s.id = ?;");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			
			return null;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT s.id, s.name, s.email, s.birth_date, s.base_salary, s.department_id, d.name as DepName FROM seller s"
					+ " JOIN department d ON s.department_id = d.id ORDER BY s.name;");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("department_id"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			
			return list;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("department_id"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		obj.setEmail(rs.getString("email"));
		obj.setBaseSalary(rs.getDouble("base_salary"));
		obj.setBirthDate(rs.getDate("birth_date"));
		obj.setDepartment(dep);
		return obj;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT s.id, s.name, s.email, s.birth_date, s.base_salary, s.department_id, d.name as DepName FROM seller s"
					+ " JOIN department d ON s.department_id = d.id WHERE d.id = ? ORDER BY s.name;");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("department_id"));
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("department_id"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			
			return list;
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
}
