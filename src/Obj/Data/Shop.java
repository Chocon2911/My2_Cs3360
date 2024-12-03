package Obj.Data;

import Obj.Base.AbstractAccount;
import java.util.List;


public class Shop extends AbstractAccount
{
	//==========================================Variable==========================================
	private String systemCode;
	private String checkInCode;
	private List<Manager> activeManagers;
	private List<Staff> activeStaffs;
	private List<Customer> activeCustomers;
	private List<Item> items;
	private List<CustomerRequest> customerRequests;

	//========================================Constructor=========================================
	public Shop()
	{
        super();
		this.systemCode = null;
		this.checkInCode = null;
		this.activeManagers = null;
		this.activeStaffs = null;
		this.activeCustomers = null;
		this.items = null;
		this.customerRequests = null;
	}

	public Shop(String id, String name, String userName, String password, boolean isLogin, 
	String systemCode, String checkInCode)
	{
		super(id, name, userName, password, isLogin);
		this.systemCode = systemCode;
		this.checkInCode = checkInCode;
	}

	public Shop(String id, String name, String userName, String password, boolean isLogin,
	String systemCode, String checkInCode, List<Manager> activeManagers, List<Staff> activeStaffs, 
    List<Customer> activeCustomers, List<Item> items, List<CustomerRequest> customerRequests)
	{
        super(id, name, userName, password, isLogin);
		this.systemCode = systemCode;
		this.checkInCode = checkInCode;
		this.activeManagers = activeManagers;
		this.activeStaffs = activeStaffs;
		this.activeCustomers = activeCustomers;
		this.items = items;
		this.customerRequests = customerRequests;
	}

	//============================================Get=============================================
	public String getSystemCode() { return this.systemCode; }
	public String getCheckInCode() { return this.checkInCode; }
	public List<Manager> getActiveManagers() { return this.activeManagers; }
	public List<Staff> getActiveStaffs() { return this.activeStaffs; }
	public List<Customer> getActiveCustomers() { return this.activeCustomers; }
	public List<Item> getItems() { return this.items; }
	public List<CustomerRequest> getCustomerRequests() { return this.customerRequests; }

	//============================================Set=============================================
	public void setSystemCode(String systemCode) { this.systemCode = systemCode; }
	public void setCheckInCode(String checkInCode) { this.checkInCode = checkInCode; }
	public void setActiveManagers(List<Manager> activeManagers) { this.activeManagers = activeManagers; }
    public void setActiveStaffs(List<Staff> activeStaffs) { this.activeStaffs = activeStaffs; }
	public void setActiveCustomers(List<Customer> activeCustomers) { this.activeCustomers = activeCustomers; }
	public void setItems(List<Item> items) { this.items = items; }
	public void setCustomerRequests(List<CustomerRequest> customerRequests) { this.customerRequests = customerRequests; }
}