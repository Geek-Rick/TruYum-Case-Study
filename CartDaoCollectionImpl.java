package com.cognizant.truyum.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoCollectionImpl implements CartDao {
	private static Map<Long, Cart> userCarts;

	public CartDaoCollectionImpl() {
		if (userCarts == null) {
			userCarts = new HashMap<Long, Cart>();
			userCarts.put(1l, new Cart());
		}
	}

	public void addCartItem(long userId, long menuItemId) {
		List<MenuItem> menuItemList = new ArrayList<MenuItem>();

		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);

		if (userCarts.containsKey(userId)) {
			Cart cart = userCarts.get(userId);
			menuItemList = cart.getMenuItemList();
			menuItemList.add(menuItem);
		} else {
			List<MenuItem> menuItemList1 = new ArrayList<MenuItem>();
			menuItemList1.add(menuItem);
			Cart cart = new Cart(menuItemList1, 0);
			userCarts.put(userId, cart);
		}
	}

	public Cart getAllCartItems(long userId) throws CartEmptyException {
		Cart cart = null;
		if (userCarts.containsKey(userId)) {
			cart = userCarts.get(userId);
			List<MenuItem> menuItemList = cart.getMenuItemList();
			if (menuItemList.size() == 0) {
				throw new CartEmptyException("Cart is Empty...");
			}
			double tot = 0;
			for (MenuItem menuItem : menuItemList) {
				tot = tot + menuItem.getPrice();
			}
			cart.setTotal(tot);
		}
		return cart;
	}

	public void removeCartItem(long userId, long menuItemId) {

		Cart cart = userCarts.get(userId);
		ListIterator<MenuItem> iterator = cart.getMenuItemList().listIterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId() == menuItemId) {
				iterator.remove();
			}
		}

	}
}
