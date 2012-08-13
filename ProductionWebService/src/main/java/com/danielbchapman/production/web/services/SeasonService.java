package com.danielbchapman.production.web.services;

import java.util.ArrayList;
import java.util.Map;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.Utility.Namespace;
import com.danielbchapman.production.beans.SeasonDaoRemote;
import com.danielbchapman.production.entity.Season;
import com.google.gson.Gson;

public class SeasonService extends AbstractJsonService
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String process(Map<String, String> parameters) throws InvalidRequestException
	{	
		Gson parse = new Gson();
		
		ArrayList<Season> seasons = Utility.getObjectFromContext(SeasonDaoRemote.class, Namespace.PRODUCTION).getSeasons();
		return parse.toJson(seasons);
	}

}
