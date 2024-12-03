package Obj.Data;

import Obj.Base.AbstractAccount;
import java.util.*;

public class Customer extends AbstractAccount
{
	//==========================================Variable==========================================
	private float balance;
	private Shop shop;
	private List<CustomerRequest> customerRequests;
	private List<RequestedItem> unRequestedItems;

	//========================================Constructor=========================================
	public Customer()
	{
        super();
		this.balance = -1;
		this.shop = null;
		this.customerRequests = null;
		this.unRequestedItems = null;
	}

	public Customer(String id, String name, String userName, String password, 
	boolean isLogin, float balance)
	{
		super(id, name, userName, password, isLogin);
		this.balance = balance;
	}

	public Customer(String id, String name, String userName, String password, 
	boolean isLogin, float balance, Shop shop, List<CustomerRequest> customerRequests, 
	List<RequestedItem> unRequestedItems)	
    {
		super(id, name, userName, password, isLogin);
		this.balance = balance;
		this.shop = shop;
		this.customerRequests = customerRequests;
		this.unRequestedItems = unRequestedItems;
	}

	//============================================Get=============================================
	public float getBalance() { return this.balance; }
	public Shop getShop() { return this.shop; }
	public List<CustomerRequest> getCustomerRequests() { return this.customerRequests; }
	public List<RequestedItem> getUnRequestedItems() { return this.unRequestedItems; }

	//============================================Set=============================================
	public void setBalance(float balance) { this.balance = balance; }
	public void setShop(Shop shop) { this.shop = shop; }
	public void setCustomerRequests(List<CustomerRequest> customerRequests) { this.customerRequests = customerRequests; }
	public void setUnRequestedItems(List<RequestedItem> unRequestedItems) { this.unRequestedItems = unRequestedItems; }
}
