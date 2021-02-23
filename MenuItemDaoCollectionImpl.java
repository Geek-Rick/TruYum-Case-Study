package com.cognizant.truyum.dao;

import java.text.ParseException;
import java.util.*;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.util.DateUtil;

public class MenuItemDaoCollectionImpl implements MenuItemDao {
	private static List<MenuItem> menuItemList;

	public MenuItemDaoCollectionImpl() {
		if (menuItemList == null) {
			menuItemList = new ArrayList<MenuItem>();

			try {
				menuItemList.add(new MenuItem(1, "Sandwich", 99, true, DateUtil.convertToDate("15/03/2017"),
						"Main Course", true));
				menuItemList.add(new MenuItem(2, "Burger", 129, true, DateUtil.convertToDate("23/12/2017"),
						"Main Course", false));
				menuItemList.add(new MenuItem(3, "Pizza", 149, true, DateUtil.convertToDate("21/08/2018"),
						"Main Course", false));
				menuItemList.add(new MenuItem(4, "French Fries", 57, false, DateUtil.convertToDate("02/07/2017"),
						"Starters", true));
				menuItemList.add(new MenuItem(5, "Chocolate Brownie", 32, true, DateUtil.convertToDate("02/11/2022"),
						"Dessert", true));
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		return menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		List<MenuItem> customerList = new ArrayList<MenuItem>();
		Date today = new Date();
		for (MenuItem temp : menuItemList) {
			if (temp.getDateOfLaunch().before(today) || temp.getDateOfLaunch().equals(today)) {
				if (temp.isActive()) {
					customerList.add(temp);
				}
			}
		}
		return customerList;
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		Iterator<MenuItem> it = menuItemList.iterator();
		while (it.hasNext()) {
			MenuItem m = it.next();
			if (m.equals(menuItem)) {

				m.setName(menuItem.getName());
				m.setActive(menuItem.isActive());
				m.setCategory(menuItem.getCategory());
				m.setDateOfLaunch(menuItem.getDateOfLaunch());
				m.setPrice(menuItem.getPrice());
				m.setFreeDelivery(menuItem.isFreeDelivery());
				break;
			}

		}

	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		Optional<MenuItem> op = menuItemList.stream().filter(item -> item.getId() == menuItemId).findFirst();
		MenuItem menuItem = (op.isPresent() == true) ? op.get() : null;
		return menuItem;

	}
}
