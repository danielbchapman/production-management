package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.ProductionDocument;
import com.danielbchapman.production.entity.Venue;

/**
 * Session Bean implementation class ProductionDocumentDao
 */
@Stateless
public class ProductionDocumentDao implements ProductionDocumentDaoRemote
{
//@PersistenceContext
  EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public ProductionDocumentDao()
  {
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#saveDocument(com.danielbchapman.production.entity.ProductionDocument)
   */
  @Override
  public void saveDocument(ProductionDocument document)
  {
    EntityInstance.saveObject(document);
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#getProductionDocument(java.lang.Long)
   */
  @Override
  public ProductionDocument getProductionDocument(Long id)
  {
    return em.find(ProductionDocument.class, id);
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#getProductionDocumentsForProduction(com.danielbchapman.production.entity.Production)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<ProductionDocument> getProductionDocumentsForProduction(Production production)
  {
    Query q = em.createQuery("SELECT doc FROM ProductionDocument doc WHERE doc.production = ?1");
    q.setParameter(1, production);
    
    List<ProductionDocument> docs = (List<ProductionDocument>)q.getResultList();
    ArrayList<ProductionDocument> results = new ArrayList<ProductionDocument>();
    
    if(docs != null)
      for(ProductionDocument d : docs)
        results.add(d);
    
    return results;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#getProductionDocuemntsForVenue(com.danielbchapman.production.entity.Venue)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<ProductionDocument> getProductionDocuemntsForVenue(Venue venue)
  {
    Query q = em.createQuery("SELECT doc FROM ProductionDocument doc WHERE doc.venue = ?1 AND doc.isGlobalDocument = ?2");
    q.setParameter(1, venue);
    q.setParameter(2, false);
    
    List<ProductionDocument> docs = (List<ProductionDocument>)q.getResultList();
    ArrayList<ProductionDocument> results = new ArrayList<ProductionDocument>();
    
    if(docs != null)
      for(ProductionDocument d : docs)
        results.add(d);
    
    return results;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#removeDocument(com.danielbchapman.production.entity.ProductionDocument)
   */
  @Override
  public void removeDocument(ProductionDocument document)
  {
    EntityInstance.deleteObject(document);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDocumentDaoRemote#getGlobalProductionDocumentsForProduction(com.danielbchapman.production.entity.Production)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<ProductionDocument> getGlobalProductionDocumentsForProduction(Production production)
  {
    Query q = em.createQuery("SELECT doc FROM ProductionDocument doc WHERE doc.production = ?1 AND doc.isGlobalDocument = ?2");
    q.setParameter(1, production);
    q.setParameter(2, true);
    
    List<ProductionDocument> docs = (List<ProductionDocument>)q.getResultList();
    ArrayList<ProductionDocument> results = new ArrayList<ProductionDocument>();
    
    if(docs != null)
      for(ProductionDocument d : docs)
        results.add(d);
    
    return results;
  }
}
