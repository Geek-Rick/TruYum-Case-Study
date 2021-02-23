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

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImpl implements CartDao {
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

	@Override
	public void addCartItem(long userId, long menuItemId) {
		String query = "insert into cart(user_id,menu_item_id) values (?,?) ";
			try {
				connection = ConectionHandler.getConnection();
				pstmt =  connection.prepareStatement(query);
				pstmt.setLong(1, userId);
				pstmt.setLong(2,menuItemId);
				 pstmt.execute();
			
			} catch (SQLException | ClassNotFoundException | IOException e) {		
				System.out.println("Error...."+e.getMessage());
			}
		

	}

	@Override
	public Cart getAllCartItems(long userId) throws CartEmptyException {
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();
		Cart cart = null;
		String query = "select * from menu_item JOIN cart ON menu_item.id = cart.menu_item_id where cart.user_id = ?";
		float total = 0.0f;
		try {
			connection = ConectionHandler.getConnection();
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, userId);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				float price = resultSet.getFloat("price");
				String category = resultSet.getString("category");
				Date date1 = resultSet.getDate("date_of_Launch");  //this is the sql date
				String freeDeliveryStr = resultSet.getString("free_delivery");
				String activeStr = resultSet.getString("active");
				boolean active ="Yes".equalsIgnoreCase(activeStr)? true:false;
				boolean freeDelivery = "Yes".equalsIgnoreCase(freeDeliveryStr)?true:false;
				java.util.Date dateOfLaunch = new java.util.Date(date1.getTime());                 
				MenuItem menuItem = new MenuItem(id, name, price, active, dateOfLaunch, category, freeDelivery);
				menuItemList.add(menuItem);
			}
			if(menuItemList.isEmpty()) {
				throw new CartEmptyException("Cart is Empty");
			}
			cart = new Cart(menuItemList,0);
			String query1 = "select sum(price) from cart join menu_item ON cart.menu_item_id=menu_item.id where user_id=?";
		 pstmt = connection.prepareStatement(query1);
			pstmt.setLong(1, userId);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				cart.setTotal(resultSet.getFloat(1));
			}
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
			System.out.println("Error...."+e.getMessage());
		}
		
		return cart;
	}

	@Override
	public void removeCartItem(long userId, long menuItem) {
		try {
			connection = ConectionHandler.getConnection();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			System.out.println("Error...."+e.getMessage());
		}
		String query = "delete from cart where user_id=? AND menu_item_id=? limit 1";
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, userId);
			pstmt.setLong(2, menuItem);                                                         
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			System.out.println("Error...."+e.getMessage());;
		}
		

	}
	private void closeConnection() throws SQLException                                                
	{
	if(resultSet !=null)
	{
	resultSet.close();
	}
	if(pstmt!=null)
	{
	pstmt.close();
	}

	if(connection!=null)
	{
	connection.close();
	}
	}

}
