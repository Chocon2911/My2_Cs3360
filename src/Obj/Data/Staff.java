package Obj.Data;

import Obj.Base.AbstractAccount;
import java.util.List;

public class Staff extends AbstractAccount
{
	//==========================================Variable==========================================
	private Shop shop;
	private List<CustomerRequest> customerRequests;

	//========================================Constructor=========================================
	public Staff()
	{
        super();
		this.shop = null;
		this.customerRequests = null;
	}

	public Staff(String id, String name, String userName, String password, boolean isLogin)
	{
		super(id, name, userName, password, isLogin);
	}

	public Staff(String id, String name, String userName, String password, boolean isLogin,
	Shop shop, List<CustomerRequest> customerRequests)
	{
        super(id, name, userName, password, isLogin);
		this.shop = shop;
		this.customerRequests = customerRequests;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }
	public List<CustomerRequest> getCustomerRequests() { return this.customerRequests; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
	public void setCustomerRequests(List<CustomerRequest> customerRequests) { this.customerRequests = customerRequests; }
}