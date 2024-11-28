package Obj.Main;

import Obj.Base.BaseAccount;

public class Staff extends BaseAccount
{
	//==========================================Variable==========================================
	private Shop shop;

	//========================================Constructor=========================================
	public Staff()
	{
        super();
		this.shop = null;
	}

	public Staff(String id, String name, String userName, String password, boolean isLogin, Shop shop)
	{
        super(id, name, userName, password, isLogin);
		this.shop = shop;
	}

	//============================================Get=============================================
	public Shop getShop() { return this.shop; }

	//============================================Set=============================================
	public void setShop(Shop shop) { this.shop = shop; }
}