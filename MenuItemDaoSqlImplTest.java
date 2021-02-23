package com.cognizant.truyum.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.util.DateUtil;

public class MenuItemDaoSqlImplTest {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		testGetMenuItemListAdmin();
		testGetMenuItemListCustomer();
		testModifyMenuItem();
		testGetMenuItem();
	}

	public static void testGetMenuItemListAdmin() {
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
		System.out.println(String.format("%-20s%-15s%-10s%-15s%-15s%-15s", "Name", "Price", "Active", "Date of Launch",
				"Category", "Free Delivery"));
		menuItemList.forEach(System.out::println);
		System.out.println(
				"======================================================================================================");

	}

	public static void testGetMenuItemListCustomer() {

		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		List<MenuItem> customerList = menuItemDao.getMenuItemListCustomer();
		System.out.println(String.format("%-20s%-15s%-15s%-15s", "Name", "Price", "Category", "FreeDelivery"));
		for (MenuItem menuItem : customerList) {

			System.out.println(String.format("%-20s%-15s%-15s%-15s", menuItem.getName(), menuItem.getPrice(),
					menuItem.getCategory(), (menuItem.isFreeDelivery() ? "Yes" : "No")));

		}
		System.out.println(
				"======================================================================================================");

	}

	public static void testModifyMenuItem() throws ParseException {
		MenuItem menuItem = new MenuItem(4, "Whiteforest Cake", 75, true, DateUtil.convertToDate("12/12/2021"),
				"Dessert", true);
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		menuItemDao.modifyMenuItem(menuItem);
		
			System.out.println("Modified menu Item");
			System.out.println(menuItemDao.getMenuItem(4));
		
		System.out.println(
				"============================================================================================================");
		List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
		System.out.println("Modified menu Item in table: ");
		System.out.println(String.format("%-20s%-15s%-10s%-15s%-15s%-15s", "Name", "Price", "Active", "Date of Launch",
				"Category", "Free Delivery"));
		menuItemList.forEach(System.out::println);
		System.out.println("Modified");
		System.out.println(
				"=============================================================================================================");

	}

	public static void testGetMenuItem() {

		
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		MenuItem item = menuItemDao.getMenuItem(4);
		System.out.println(item);
		System.out.println("=========================================================================================");
		

			
		}
	}

