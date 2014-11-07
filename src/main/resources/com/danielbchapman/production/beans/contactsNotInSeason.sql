/* Query to detrimine LinkedContact not in the season */
SELECT id FROM
(
	SELECT 
		l.id,
		c.lastName,
		c.firstName
	FROM 
		LinkedContact l 
		INNER JOIN Contact c
			ON
				l.contact_id = c.id
	WHERE 
	  (SELECT 
	  		Count(s.id) 
			FROM 
				SeasonContact s 
			WHERE 
				    s.linkedContact_id = l.id
				AND s.season_id = ?1
			) < 1
	ORDER BY
		2, 3
);

/* Query to detrimine Contact not in the season */
SELECT id FROM
(
	SELECT 
		c.id,
		c.lastName,
		c.firstName
	FROM 
		Contact c
	WHERE
	  (
			SELECT 
	  		Count(s.id) 
			FROM 
				SeasonContact s 
			WHERE 
				    s.baseContact_id = c.id
				AND s.season_id = ?1
			) < 1
	ORDER BY
		2, 3
);

/* Query to detrimine Contact not in the season, for some reason this was returning null, replaced with above*/
SELECT id FROM 
(
	SELECT 
		c.id,
		c.lastName,
		c.firstName
	FROM 
		Contact c
	WHERE 
		c.id NOT IN 
		(
			SELECT 
				s.baseContact_id 
			FROM 
				SeasonContact s 
			WHERE 
				s.season_id = ?1  
		)
	ORDER BY
	  2, 3
)

/* TEMP */
SELECT * FROM CONTACT c
LEFT OUTER JOIN
(SELECT * FROM SeasonContact s WHERE BaseContact_ID IS NOT NULL) sub
  ON c.id <> sub.baseContact_Id
;
