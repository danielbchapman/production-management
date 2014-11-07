package org.theactingcompany.persistence;

public class TestEntityInstance extends AbstractEntityInstance
{

	@Override
	protected String getPersistenceUnitId()
	{
		return "testunit";
	}

	@Override
	protected boolean isContainerManaged()
	{
		return false;
	}

	@Override
	protected ClassLoader getClassLoader()
	{
		return TestEntityInstance.class.getClassLoader();
	}
}