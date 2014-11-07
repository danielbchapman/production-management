/* A Query to locate Groups inside the SeasonContacts for a season */
SELECT DISTINCT id FROM 
(
SELECT
  g.id,
  g.name
FROM
  SeasonContact s 
  INNER JOIN
  Contact c
    ON
      s.baseContact_id = c.id
  INNER JOIN
  ContactGroup g
    ON 
      c.contactGroup_id = g.id
WHERE
	s.season_id = ?1

UNION ALL

SELECT
  g.id,
  g.name
FROM
  SeasonContact s 
  INNER JOIN
  LinkedContact l
    ON
      s.linkedContact_id = l.id
  INNER JOIN
  ContactGroup g
    ON 
      l.contactGroup_id = g.id
WHERE
	s.season_id = ?1
	
ORDER BY
 2
)  

/* Query to locate LinkedContacts */
SELECT
  l.linkedContact_id
FROM
  LinkedContact l
WHERE
      l.season_id = ?1
  AND l.contactGroup_id = ?2
/* Query to locate Contacts */