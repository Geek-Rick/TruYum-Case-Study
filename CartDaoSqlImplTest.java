package com.cognizant.truyum.dao;

import java.util.List;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImplTest {

	public static void main(String[] args) throws CartEmptyException {
	
		testAddCartItem();
		testGetAllCartItems();
		testRemoveCartItem();
		testGetAllCartItems();
	}
	public static void testAddCartItem() throws CartEmptyException {
		CartDao cartDao = new CartDaoSqlImpl();
		cartDao.addCartItem(1, 4);
		cartDao.addCartItem(1, 2);
		Cart cartMenu = cartDao.getAllCartItems(1);
		System.out.println("Items added successfully");	
		System.out.println("===========================================================================================================");
	}
	public static void testGetAllCartItems() throws CartEmptyException {
		CartDao cartDao =  new CartDaoSqlImpl();
			Cart carts = cartDao.getAllCartItems(1);
		     System.out.println(String.format("%-20s%-15s%-15s%-15s", "name","price","category","FreeDelivery"));
		     carts.getMenuItemList().forEach(System.out::println);
		     System.out.println("total="+ carts.getTotal());
		 System.out.println("===========================================================================================================");
	}
			
	public static void testRemoveCartItem() {
               CartDao cartDao = new CartDaoSqlImpl();
               cartDao.removeCartItem(1, 4);
               System.out.println("Item Removed");
               System.out.println("======================================================================================================");

	}
}
