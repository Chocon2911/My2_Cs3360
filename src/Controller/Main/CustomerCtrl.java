package Controller.Main;

import Controller.Base.AbstractAccountCtrl;
import DataBase.Child.*;
import Obj.Main.*;
import java.util.*;

public class CustomerCtrl extends AbstractAccountCtrl
{
    //========================================Constructor=========================================
    public CustomerCtrl() { super(); }
    public CustomerCtrl(String id) { super(id); }

    //============================================Get=============================================
    private float getBalance() { return this.queryInfo().getBalance(); }
    private Shop getShop() { return this.queryInfo().getShop(); }
    private List<CustomerRequest> getCustomerRequests() { return this.queryInfo().getCustomerRequests(); }
    private List<RequestedItem> getUnRequestedItems() { return this.queryInfo().getUnRequestedItems(); }

    //============================================Set=============================================
    private void setBalance(float balance) 
    { 
        Customer info = this.queryInfo();
        info.setBalance(balance); 
        this.updateInfo(info);
    }
    private void setShop(Shop shop) 
    { 
        Customer info = this.queryInfo();
        info.setShop(shop); 
        this.updateInfo(info);
    }
    private void setCustomerRequests(List<CustomerRequest> customerRequests) 
    { 
        Customer info = this.queryInfo();
        info.setCustomerRequests(customerRequests); 
        this.updateInfo(info);
    }
    private void setUnRequestedItems(List<RequestedItem> unRequestedItems) 
    { 
        Customer info = this.queryInfo();
        info.setUnRequestedItems(unRequestedItems);
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    protected Customer queryInfo()
    {
        return new CustomerDb().queryCustomerData(id);
    }
    @Override
    protected <T> void updateInfo(T info)
    {
        new CustomerDb().updateCustomerData((Customer)info);
    }
}
