/* SELECT LINKED CONTACTS */
SELECT 
	sc.linkedContact 
FROM 
	SeasonContact sc 
WHERE 
			sc.linkedContact.contactGroup = ?1 
	AND sc.season = ?2  
	AND sc.linkedContact IS NOT NULL 
ORDER BY 
	sc.linkedContact.contact.lastName, 
	sc.linkedContact.contact.firstName

/* SELECT BASE CONTACTS */
SELECT 
	sc.baseContact 
FROM 
	SeasonContact sc 
WHERE 
			sc.baseContact.contactGroup = ?1 
	AND sc.season = ?2  
	AND sc.baseContact IS NOT NULL 
ORDER BY 
	sc.baseContact.lastName, 
	sc.baseContact.firstName	