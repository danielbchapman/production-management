package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.ProductionDocument;
import com.danielbchapman.production.entity.Venue;

/**
 * The production document data access object provides all I/O and DB related 
 * information for the ProductionDocument objects. 
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Dec 28, 2010 2010
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@Remote
public interface ProductionDocumentDaoRemote
{
  /**
   * @param document save this document to the database.  
   */
  public void saveDocument(ProductionDocument document);
  
  /**
   * @param id the id of the entity  
   * Return the document for this entity, though you probably will never need this.
   */
  public ProductionDocument getProductionDocument(Long id);
  
  /**
   * @param production
   * @return all the documents related to this production, but unassigned to a venue.  
   */
  public ArrayList<ProductionDocument> getProductionDocumentsForProduction(Production production);
  
  /**
   * @param venue
   * @return Return all the documents associated with this venue including documents associated with
   * the production globally.  
   */
  public ArrayList<ProductionDocument> getProductionDocuemntsForVenue(Venue venue);
  
  /**
   * Remove a document from the database.
   * @param document The document to delete.   
   */
  public ArrayList<ProductionDocument> getGlobalProductionDocumentsForProduction(Production production);
  
  /**
   * @param document the document to remove  
   */
  public void removeDocument(ProductionDocument document);
}
