package Obj.Main;

import Obj.Base.BaseMainObj;
import java.util.*;

public class CustomerRequest extends BaseMainObj
{
	//==========================================Variable==========================================
	private Shop shop;
	private Customer requestedCustomer;
	private Staff handledStaff;
	private List<RequestedItem> requestedItems;
	private boolean isSold;

	//========================================Constructor=========================================
	public CustomerRequest()
	{
        super();
		this.shop = null;
		this.requestedCustomer = null;
		this.handledStaff = null;
		this.requestedItems = null;
		this.isSold = false;
	}

	public CustomerRequest(String id, String name, Shop shop, Customer requestedCustomer, 
    Staff handledStaff, List<RequestedItem> requestedItems, boolean isSold)
	{
        super(id, name);
		this.shop = shop;
		this.requestedCustomer = requestedCustomer;
		this.handledStaff = handledStaff;
		this.requestedItems = requestedItems;
		this.isSold = isSold;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }
	public Customer getRequestedCustomer() { return this.requestedCustomer; }
	public Staff getHandledStaff() { return this.handledStaff; }
	public List<RequestedItem> getRequestedItems() { return this.requestedItems; }
	public boolean getIsSold() { return this.isSold; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
	public void setRequestedCustomer(Customer requestedCustomer) { this.requestedCustomer = requestedCustomer; }
	public void setHandledStaff(Staff handledStaff) { this.handledStaff = handledStaff; }
	public void setRequestedItems(List<RequestedItem> requestedItems) { this.requestedItems = requestedItems; }
	public void setIsSold(boolean isSold) { this.isSold = isSold; }
}