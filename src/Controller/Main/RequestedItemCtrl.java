package Controller.Main;

import Controller.Base.AbstractObjCtrl;
import DataBase.Child.*;
import Obj.Main.*;

public class RequestedItemCtrl extends AbstractObjCtrl
{
    //========================================Constructor=========================================
    public RequestedItemCtrl() { super(); }
    public RequestedItemCtrl(String id) { super(id); }

    //============================================Get=============================================
    private Shop getShop() { return this.queryInfo().getShop(); }
    private CustomerRequest getCustomerRequest() { return this.queryInfo().getCustomerRequest(); }
    private Customer getCustomer() { return this.queryInfo().getCustomer(); }
    private Item getItem() { return this.queryInfo().getItem(); }
    private int getAmount() { return this.queryInfo().getAmount(); }

    //============================================Set=============================================
    private void setShop(Shop shop) 
    {
        RequestedItem info = this.queryInfo();
        info.setShop(shop);
        this.updateInfo(info);
    }
    private void setCustomerRequest(CustomerRequest customerRequest) 
    {
        RequestedItem info = this.queryInfo();
        info.setCustomerRequest(customerRequest);
        this.updateInfo(info);
    }
    private void setCustomer(Customer customer) 
    {
        RequestedItem info = this.queryInfo();
        info.setCustomer(customer);
        this.updateInfo(info);
    }
    private void setItem(Item item) 
    {
        RequestedItem info = this.queryInfo(); 
        info.setItem(item);
        this.updateInfo(info);
    }
    private void setAmount(int amount) 
    {
        RequestedItem info = this.queryInfo();
        info.setAmount(amount); 
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    @SuppressWarnings("unchecked")
    protected RequestedItem queryInfo()
    {
        return new RequestedItemDb().queryRequestedItemData(id);
    }
    @Override
    protected <T> void updateInfo(T info) 
    {
        new RequestedItemDb().updateRequestedItemData((RequestedItem)info);    
    }
}
