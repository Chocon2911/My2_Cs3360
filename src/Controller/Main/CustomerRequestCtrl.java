package Controller.Main;

import Controller.Base.AbstractMainObjCtrl;
import DataBase.Child.CustomerRequestDb;
import Obj.Main.*;
import java.util.List;

public class CustomerRequestCtrl extends AbstractMainObjCtrl
{
    //========================================Constructor=========================================
    public CustomerRequestCtrl(String id) { super(id); }
    public CustomerRequestCtrl() { super(); }

    //============================================Get=============================================
    private Shop getShop() { return this.queryInfo().getShop(); }
    private Customer getRequestedCustomer() { return this.queryInfo().getRequestedCustomer(); }
    private Staff getHandledStaff() { return this.queryInfo().getHandledStaff(); }
    private List<RequestedItem> getRequestedItems() { return this.queryInfo().getRequestedItems(); }

    //============================================Set=============================================
    private void setShop(Shop shop) 
    { 
        CustomerRequest info = this.queryInfo();
        info.setShop(shop); 
        this.updateInfo(info);
    }
    private void setRequestedCustomer(Customer customer) 
    { 
        CustomerRequest info = this.queryInfo();
        info.setRequestedCustomer(customer); 
        this.updateInfo(info);
    }
    private void setHandledStaff(Staff staff) 
    { 
        CustomerRequest info = this.queryInfo();
        info.setHandledStaff(staff); 
        this.updateInfo(info);
    }
    private void setRequestedItems(List<RequestedItem> requestedItems) 
    { 
        CustomerRequest info = this.queryInfo();
        info.setRequestedItems(requestedItems); 
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    protected CustomerRequest queryInfo()
    {
        return new CustomerRequestDb().queryCustomerRequestData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new CustomerRequestDb().updateCustomerRequestData((CustomerRequest)info);
    }
}
