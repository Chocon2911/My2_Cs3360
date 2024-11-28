package Controller.Main;

import Controller.Base.AbstractMainObjCtrl;
import DataBase.Child.ItemDb;
import Obj.Main.*;
import java.util.List;

public class ItemCtrl extends AbstractMainObjCtrl
{
    //========================================Constructor=========================================
    public ItemCtrl() { super(); }
    public ItemCtrl(String id) { super(id); }

    //============================================Get=============================================
    private Shop getShop() { return this.queryInfo().getShop(); }
    private float getPrice() { return this.queryInfo().getPrice(); }
    private ItemType getItemType() { return this.queryInfo().getItemType(); }
    private int getInitAmount() { return this.queryInfo().getInitAmount(); }
    private String getDescription() { return this.queryInfo().getDescription(); }
    private List<RequestedItem> getRequestedItems() { return this.queryInfo().getRequestedItems(); }
    
    //============================================Set=============================================
    private void setShop(Shop shop) 
    { 
        Item info = this.queryInfo();
        info.setShop(shop); 
        this.updateInfo(info);
    }
    private void setPrice(float price)
    {
        Item info = this.queryInfo();
        info.setPrice(price);
        this.updateInfo(info);
    }
    private void setItemType(ItemType itemType)
    {
        Item info = this.queryInfo();
        info.setItemType(itemType);
        this.updateInfo(info);
    }
    private void setInitAmount(int initAmount)
    {
        Item info = this.queryInfo();
        info.setInitAmount(initAmount);
        this.updateInfo(info);
    }
    private void setDescription(String description)
    {
        Item info = this.queryInfo();
        info.setDescription(description);
        this.updateInfo(info);
    }
    private void setRequestedItems(List<RequestedItem> requestedItems)
    {
        Item info = this.queryInfo();
        info.setRequestedItems(requestedItems);
        this.updateInfo(info);
    }

    //==========================================Override==========================================
    @Override
    protected Item queryInfo()
    {
        return new ItemDb().queryItemData(id);
    }
    @Override 
    protected <T> void updateInfo(T info)
    {
        new ItemDb().updateItemData((Item)info);
    }
}
