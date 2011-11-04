package com.danielbchapman.production.web.production.beans;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.VendorDaoRemote;
import com.danielbchapman.production.entity.Vendor;

/**
 * A simple bean that stores the state of Vendors.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 18, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@SessionScoped
public class VendorBean
{
  private boolean classOffsetToggle;
  private Vendor newVendor = new Vendor();

  private VendorDaoRemote vendorDao;
  private ArrayList<VendorUpdateWrapper> vendors; //This is failing for some reason.
  public String getClassNameOffset()
  {
    classOffsetToggle = !classOffsetToggle;//toggle
    if(classOffsetToggle)
      return "repeatShadedDark";
    else
      return "repeatShadedLight";
  }
  
  public Vendor getNewVendor()
  {
    return newVendor;
  }

  private VendorDaoRemote getVendorDao()
  {
    if (vendorDao == null)
      vendorDao = Utility.getObjectFromContext(VendorDaoRemote.class, Utility.Namespace.PRODUCTION);

    return vendorDao;
  }
  
  /**
   * @return An ordered map of all vendors.  
   * @note This structure is used because we're getting really weak references 
   * from the JSF model (casts to strings). As such, we need to grab the thing
   * that has been modified (the Vendor not the ID).
   */
  public ArrayList<VendorUpdateWrapper> getVendors()
  {
    if(vendors == null)
    {
      vendors = new ArrayList<VendorUpdateWrapper>();
      
      for(Vendor v : getVendorDao().getAllVendors())
        vendors.add(new VendorUpdateWrapper(v));
    }
    
    return vendors;
  }
  
  public void refreshVendors(ActionEvent evt)
  {
    if(evt != null)
    {
      vendors = null;  
    }
    
  }
  
  public void saveNewVendor(ActionEvent evt)
  {
    if(evt != null)
    {
      if(newVendor.getCompanyName() == null || newVendor.getCompanyName().trim().length() < 2)
      {
        FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Validation error",
                "Please enter a useful vendor name"
                )
            );        
        return;
      }
      
      if(newVendor.getDepartment() == null || newVendor.getDepartment().trim().length() < 2)
      {
        FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Validation error",
                "Please enter a useful departmental description"
                )
            );        
        return;
      }      
      getVendorDao().saveVendor(newVendor);

      FacesContext.getCurrentInstance().addMessage(
          null, 
          new FacesMessage(
              FacesMessage.SEVERITY_INFO, 
              newVendor.getCompanyName() + " saved.",
              "The vendor was successfully saved to the database."
              )
          );
      
      newVendor = new Vendor();    
      vendors = null;
    }
  }
  
  public void setNewVendor(Vendor newVendor)
  {
    this.newVendor = newVendor;
  }
  public void saveRowEdit(ActionEvent evt)
  {
    if(evt != null)
    {
      Vendor vend = (Vendor) evt.getComponent().getAttributes().get("vendorObject");
      if(vend.getCompanyName() == null || vend.getCompanyName().trim().length() < 2)
      {
        FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Validation error",
                "Please enter a useful vendor name"
                )
            );        
        return;
      }
      
      if(vend.getDepartment() == null || vend.getDepartment().trim().length() < 2)
      {
        FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(
                FacesMessage.SEVERITY_ERROR, 
                "Validation error",
                "Please enter a useful departmental description"
                )
            );        
        return;
      }      
      
      getVendorDao().saveVendor(vend);

      FacesContext.getCurrentInstance().addMessage(
          null, 
          new FacesMessage(
              FacesMessage.SEVERITY_INFO, 
              vend.getCompanyName() + " saved.",
              "The vendor was successfully saved to the database."
              )
          );
    }

  }
  
  public class VendorUpdateWrapper
  {
    private Vendor vendor;
    
    public VendorUpdateWrapper(Vendor vendor)
    {
      this.vendor = vendor;
    }
    
    public Vendor getVendor()
    {
      return vendor;
    }

    public void setVendor(Vendor vendor)
    {
      this.vendor = vendor;
    }

    public void updateVendor(ActionEvent evt)
    {
      if(evt != null)
      {
        if(vendor.getCompanyName() == null || vendor.getCompanyName().trim().length() < 2)
        {
          FacesContext.getCurrentInstance().addMessage(
              null, 
              new FacesMessage(
                  FacesMessage.SEVERITY_ERROR, 
                  "Validation error",
                  "Please enter a useful vendor name"
                  )
              );        
          return;
        }
        
        if(vendor.getDepartment() == null || vendor.getDepartment().trim().length() < 2)
        {
          FacesContext.getCurrentInstance().addMessage(
              null, 
              new FacesMessage(
                  FacesMessage.SEVERITY_ERROR, 
                  "Validation error",
                  "Please enter a useful departmental description"
                  )
              );        
          return;
        }      
        
        getVendorDao().saveVendor(vendor);

        FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(
                FacesMessage.SEVERITY_INFO, 
                vendor.getCompanyName() + " saved.",
                "The vendor was successfully saved to the database."
                )
            );
      }
    }
  }
}
