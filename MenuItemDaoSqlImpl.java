package com.cognizant.truyum.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.cognizant.truyum.model.MenuItem;

public class MenuItemDaoSqlImpl implements MenuItemDao {
	private Connection connection;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		String query = "select*from menu_item";
		try {
			try {
				connection = ConectionHandler.getConnection();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error....." + e.getMessage());
			}
			stmt = connection.createStatement();
			pstmt = connection.prepareStatement(query);
			resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				Date date1 = resultSet.getDate("date_of_Launch"); 
				String freeDeliveryStr = resultSet.getString("free_delivery");
				String activeStr = resultSet.getString("active");
				boolean active = "Yes".equalsIgnoreCase(activeStr) ? true : false;
				boolean freeDelivery = "Yes".equalsIgnoreCase(freeDeliveryStr) ? true : false;
				java.util.Date dateOfLaunch = new java.util.Date(date1.getTime()); 
																					
				MenuItem menuItem = new MenuItem(id, name, price, active, dateOfLaunch, category, freeDelivery);
				menuItemList.add(menuItem);
			}
		} catch (SQLException e) {

			System.out.println("Error...." + e.getMessage());
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error while closing.......");
			}
		}
		return menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		List<MenuItem> custList = new ArrayList<MenuItem>();
		String query = "select * from menu_item where active='Yes' AND Date_of_Launch<=current_date()";
		try {
			connection = ConectionHandler.getConnection();
			pstmt = connection.prepareStatement(query);
			resultSet = pstmt.executeQuery(query);
			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				Date date1 = resultSet.getDate("Date_of_Launch"); // this is the sql date
				String freeDeliveryStr = resultSet.getString("free_delivery");
				String activeStr = resultSet.getString("active");
				boolean active = "Yes".equalsIgnoreCase(activeStr) ? true : false;
				boolean freeDelivery = "Yes".equalsIgnoreCase(freeDeliveryStr) ? true : false;
				java.util.Date dateOfLaunch = new java.util.Date(date1.getTime()); 
																		
				MenuItem menuItem = new MenuItem(id, name, price, active, dateOfLaunch, category, freeDelivery);
				custList.add(menuItem);
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {

			System.out.println("Error...." + e.getMessage());
		}

		return custList;
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		try {
			connection = ConectionHandler.getConnection();
		} catch (ClassNotFoundException | IOException | SQLException e1) {		
			System.out.println("Error...."+e1.getMessage());
		}
		long id = menuItem.getId();
		String name = menuItem.getName();
		float price = menuItem.getPrice();
		boolean active = menuItem.isActive();
		Date date = menuItem.getDateOfLaunch();
		java.sql.Date d = new java.sql.Date(date.getTime());
		String category = menuItem.getCategory();
		boolean free = menuItem.isFreeDelivery();
		String act;
		if (active == true) {
			act = "Yes";
		} else {
			act = "No";
		}
		String fd;
		if (free == true) {
			fd = "Yes";
		} else {
			fd = "No";
		}
		String query = "update menu_item set name = ?,price = ?,active = ?,Date_of_Launch = ?,category = ?,free_delivery = ? where id = ?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setFloat(2, price);
			stmt.setString(3, act);
			stmt.setDate(4, d);
			stmt.setString(5, category);
			stmt.setString(6, fd);
			stmt.setLong(7, id);
			int n = stmt.executeUpdate();
			System.out.println(n + " rows updated");

		} catch (SQLException e) {
			System.out.println("Error....." + e.getMessage());
			;
		}

	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		MenuItem menuItem = null;
		String query = "Select * from menu_item where id = ?";
		try {
			connection = ConectionHandler.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, menuItemId);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				String activeStr = resultSet.getString("active");
				Date Date_of_Launch = resultSet.getDate("Date_of_launch");
				String free_DeliveryStr = resultSet.getString("free_delivery");

				boolean active = "Yes".equalsIgnoreCase(activeStr) ? true : false;
				boolean freeDelivery = "Yes".equalsIgnoreCase(free_DeliveryStr) ? true : false;

				java.util.Date dateOfLaunch = new java.util.Date(Date_of_Launch.getTime());

				menuItem = new MenuItem(id, name, price, active, Date_of_Launch, category, freeDelivery);
				menuItemList.add(menuItem);

			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			System.out.println("Error.." + e.getMessage());
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				System.out.println("Error............" + e.getMessage());
			}
		}

		return menuItem;
	}

	private void closeConnection() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}

		if (stmt != null) {
			stmt.close();
		}

		if (pstmt != null) {
			pstmt.close();
		}

		if (connection != null) {
			connection.close();
		}
	}

}
