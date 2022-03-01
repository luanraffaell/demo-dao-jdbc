package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartmendDao;
import model.entities.Department;

public class DepartmentDaoJdbc implements DepartmendDao {
	private Connection conn;
	public DepartmentDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("Insert into department (Name) values(?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getName());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
				obj.setId(rs.getInt(1));
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DbException("rollback fail");
			}
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("update department set Name = ? where Id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			int rowsAffect = st.executeUpdate();

			System.out.println("RowsAffected:"+rowsAffect);
			if (rowsAffect > 0) {{
				Statement s2 = conn.createStatement();
				rs = s2.executeQuery("Select * from department where Id="+obj.getId());
				while (rs.next()) {
					System.out.println("Id:"+rs.getInt("Id")+" - Name:"+rs.getString("Name"));
				}
			}
				
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("delete from department where Id =?");
			ps.setInt(1, id);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Rows Affected: "+rowsAffected);
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT Id, Name from department where Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
			Department dep = instantiateDepartment(rs);
			return dep;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			 st = conn.prepareStatement("SELECT Id, Name from department order by Id");
			
			 rs = st.executeQuery();
			 List<Department> list = new ArrayList<>();
			 while (rs.next()) {
				 Department depart = instantiateDepartment(rs);
				 list.add(depart);
				
			}
			 return list;
			 
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep =  new Department();
			dep.setId(rs.getInt("Id"));
			dep.setName(rs.getString("Name"));
			return dep;
	}

}
