package com.cognizant.truyum.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
          private List<MenuItem> menuItemList;
          @Override
		public String toString() {
			return "Cart [menuItemList=" + menuItemList + ", total=" + total + "]";
		}
		public List<MenuItem> getMenuItemList() {
			return menuItemList;
		}
		public void setMenuItemList(List<MenuItem> menuItemList) {
			this.menuItemList = menuItemList;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		private double total;
          public Cart(List<MenuItem> menuItemList, double total) {
        	  this.menuItemList = menuItemList;
        	  this.total =total;
          }
          public Cart() {
        	  menuItemList = new ArrayList<MenuItem>();//To avoid null pointer exception
        	  total =0;
          }
}
