SELECT "id" FROM (SELECT
  sc.id AS "id",
  g.name AS "name",
  c.subGroup AS "subGroup",
  c.lastName AS "lastName",
  c.firstName AS "firstName"
FROM 
  SeasonContact sc 
  INNER JOIN Contact c 
    ON 
      sc.baseContact_id = c.id
  INNER JOIN ContactGroup g
    ON 
      c.contactGroup_id = g.id
  WHERE
        sc.baseContact_id IS NOT NULL
    AND sc.season_id = ?1
    
UNION ALL

SELECT
  sc.id AS "id",
  g.name AS "name",
  l.subGroup AS "subGroup",
  c.lastName AS "lastName",
  c.firstName AS "firstName"
FROM 
  SeasonContact sc 
  INNER JOIN LinkedContact l
    ON
      sc.linkedContact_id = l.id
  INNER JOIN Contact c
    ON 
      l.contact_id = c.id
  INNER JOIN ContactGroup g
    ON 
      l.contactGroup_id = g.id
  WHERE
        sc.linkedContact_id IS NOT NULL
    AND sc.season_id = ?1 
ORDER BY 
  "name",
  "subGroup",
  "lastName",
  "firstName"
  )
SELECT 
	c.id 
FROM 
	Contact c 
WHERE 
	c.id NOT IN 
	(
		SELECT 
			s.contact_id 
		FROM 
			SeasonContact s 
		WHERE 
			s.season_id = ?1) 
		ORDER BY 
			c.lastName, c.firstName 
	)
UNION ALL
SELECT 
	c.id 
FROM 
	Contact c 
WHERE 
	c.id NOT IN 
	(
		SELECT 
			s.contact_id 
		FROM 
			SeasonContact s 
		WHERE 
			s.season_id = ?1) 
		ORDER BY 
			c.lastName, c.firstName 
	)