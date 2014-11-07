package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Vendor;

/**
 * Session Bean implementation class VendorDao
 */
@Stateless
public class VendorDao implements VendorDaoRemote
{

	private static final long serialVersionUID = 1L;
	EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public VendorDao()
  {
  }

  @Override
  public Vendor getVendor(Long id)
  {
    return em.find(Vendor.class, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Vendor> getAllVendors()
  {
    Query q = em.createQuery("SELECT v FROM Vendor v ORDER BY v.department, v.companyName");
    ArrayList<Vendor> ret = new ArrayList<Vendor>();

    List<Vendor> results = q.getResultList();
    if(results != null)
      for(Vendor v : results)
        ret.add(v);

    return ret;
  }

  @Override
  public void saveVendor(Vendor vendor)
  {
    EntityInstance.saveObject(vendor);
  }

  @Override
  public void deleteVendor(Vendor vendor)
  {
    EntityInstance.deleteObject(vendor);
  }

}
