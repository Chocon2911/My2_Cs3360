package Obj.Data;

import Obj.Base.AbstractMainObj;
import java.util.*;

public class CustomerRequest extends AbstractMainObj
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

	public CustomerRequest(String id, String name, boolean isSold)
	{
		super(id, name);
		this.isSold = isSold;
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

	public float getTotalMoney()
	{
		if (requestedItems == null) return 0.0f;

		float totalMoney = 0.0f;
		for (RequestedItem requestedItem : requestedItems)
		{
			totalMoney += requestedItem.getAmount() * requestedItem.getTotalMoney();
		}

		return totalMoney;
	}

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
	public void setRequestedCustomer(Customer requestedCustomer) { this.requestedCustomer = requestedCustomer; }
	public void setHandledStaff(Staff handledStaff) { this.handledStaff = handledStaff; }
	public void setRequestedItems(List<RequestedItem> requestedItems) { this.requestedItems = requestedItems; }
	public void setIsSold(boolean isSold) { this.isSold = isSold; }
}