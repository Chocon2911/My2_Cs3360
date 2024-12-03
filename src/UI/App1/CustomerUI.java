package UI.App1;

import Controller.Child.CustomerCtrl;

public class CustomerUI 
{
    private CustomerCtrl ctrl;
    
    public CustomerUI()
    {
        this.ctrl = null;
    }
    
    public CustomerUI(String id)
    {
        this.ctrl = new CustomerCtrl(id);
    }
}
