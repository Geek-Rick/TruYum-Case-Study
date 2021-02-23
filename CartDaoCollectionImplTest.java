package com.cognizant.truyum.dao;

import com.cognizant.truyum.model.MenuItem;
import java.util.*;
import com.cognizant.truyum.dao.CartEmptyException;
import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.util.*;

public class CartDaoCollectionImplTest {

	public static void testAddCartItem() throws CartEmptyException {

		CartDao cartDao = new CartDaoCollectionImpl();
		cartDao.addCartItem(1, 4);
		cartDao.addCartItem(1, 2);
		Cart cartMenu = cartDao.getAllCartItems(1);
		System.out.println("Items added successfully");
		System.out.println(
				"===========================================================================================================");
	}

	public static void testGetAllCartItems() {
		CartDao cartDao = new CartDaoCollectionImpl();
		List<MenuItem> menuItemList;
		try {
			Cart cart = cartDao.getAllCartItems(1);
			menuItemList = cart.getMenuItemList();
			System.out.println(String.format("%-20s%-15s%-15s%-15s", "name", "price", "category", "FreeDelivery"));
			for (MenuItem menuItem : menuItemList) {
				System.out.printf("%-20s%-15s%-15s%-15s\n", menuItem.getName(), menuItem.getPrice(),
						menuItem.getCategory(), (menuItem.isFreeDelivery() ? "Yes" : "No"));
			}
			System.out.println("total=" + cart.getTotal());
		} catch (CartEmptyException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(
				"==========================================================================================================");
	}

	public static void testRemoveCartItem() throws CartEmptyException {
		CartDao cartDao = new CartDaoCollectionImpl();

		cartDao.removeCartItem(1, 4);

		System.out.println("Items removed successfully");
	}

	public static void main(String[] args) throws CartEmptyException {
		testAddCartItem();
		testGetAllCartItems();
		testRemoveCartItem();
		testGetAllCartItems();

	}

}
