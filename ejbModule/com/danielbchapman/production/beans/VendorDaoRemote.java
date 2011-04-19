package com.danielbchapman.production.beans;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Vendor;

/**
 * A simple data access object for manipulating Vendor information. Vendors
 * are fairly obvious so these notes are sparse.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 18, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */

@Remote
public interface VendorDaoRemote
{
  /**
   * 
   * @param id the unique id of the entity
   * @return the vendor for that id, null if none
   * 
   */
  public Vendor getVendor(Long id);
  
  /**
   * @return a list of all vendors  
   * 
   */
  public ArrayList<Vendor> getAllVendors();
  
  /**
   * Saves a vendor to the database, if the ID is null it adds a new vendor
   * @param vendor the vendor to save
   * 
   */
  public void saveVendor(Vendor vendor);
  
  /**
   * Removes a vendor from the database
   * @param vendor the vendor to remove  
   * 
   */
  public void deleteVendor(Vendor vendor);
}
