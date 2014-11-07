package org.theactingcompany.inventory.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class AdministrationDao
 */
@Stateless
public class AdministrationDao implements AdministrationDaoRemote
{

  /**
   * Default constructor.
   */
  public AdministrationDao()
  {
  }

  public void initializeFullTextSearches()
  {
    Connection connection = EntityInstance.getConnection();

    try
    {
      ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
      ArrayList<PreparedStatement> statements = new ArrayList<PreparedStatement>();

      statements.add(connection.prepareStatement("CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\"; CALL FT_INIT();"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'WardrobeElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'PropsElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'ScenicElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'SoundElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'StageManagementElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'LightingElement', NULL);"));
      statements.add(connection.prepareStatement("CALL FT_CREATE_INDEX('PUBLIC', 'GeneralElement', NULL);"));

      for(PreparedStatement p : statements)
      {
        Throwable t = runStatement(p);
        if(t != null)
          exceptions.add(t);
      }


    }
    catch (SQLException e)
    {
      System.out.println("\n\n------------------SQLException " + this.getClass().getName());
      e.printStackTrace();
      System.out.println("\n\n------------------END SQLException  " + this.getClass().getName() + "\n");
    }
    finally
    {
      try
      {
        if(connection != null && !connection.isClosed())
        {
          connection.close();            
        }
      }
      catch (SQLException e)
      {
        throw new RuntimeException("Connection was not able to be closed. This might indicate a fatal error in the application.", e);
      }      
    }

  }

  private Throwable runStatement(PreparedStatement statement)
  {
    try
    {
      statement.execute();
      return null;
    }
    catch (SQLException e)
    {
      return e;
    }
  }
}
