package org.theactingcompany.help.entity;

import org.theactingcompany.persistence.AbstractEntityInstance;

/**
 * The DelegateInstance for Entity management
 * @author dchapman
 * @since Aug 18, 2011
 * @copyright The Acting Company Aug 18, 2011 @link www.theactingcompany.org
 */
class DelegateInstance extends AbstractEntityInstance
{

	@Override
	protected String getPersistenceUnitId()
	{
		return "help";
	}

	@Override
	protected boolean isContainerManaged()
	{
		return false;
	}
}