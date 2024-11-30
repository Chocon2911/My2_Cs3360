package Obj.Controller;

import DataBase.Child.ShopDb;
import Obj.Data.Shop;
import Util.ObjUtil;

public class App2Ctrl extends ObjUtil
{
    //===========================================Login============================================
    public int login(String userName, String password)
    {
        Shop shop = new ShopDb().queryShopByUserName(userName);
        if (shop == null) return 1; // UserName Not Found
        else if (!shop.getPassword().equals(password)) return 2; // Password Wrong
        return 0;
    }

    public String getIdByUserName(String userName) 
    { 
        return new ShopDb().queryShopByUserName(userName).getId(); 
    }

    //==========================================Sign Up===========================================
    public int signUp(String name, String userName, String password, String checkInCode, String systemCode)
    {
        String shopId = this.getRandomStr(10);
        Shop shop = new Shop(shopId, name, userName, password, systemCode, checkInCode, null, null, null, null, null);

        String e = new ShopDb().insertShopData(shop);
        if (e == null) return 0;
        else if (e.contains("Shops.Id"))
        {
            System.out.println("Error: Id already exists");
            return this.signUp(name, userName, password, checkInCode, systemCode);
        }
        else if (e.contains("Shops.UserName")) return 1;

        return 0;
    }
}
