CREATE EVENT e_totals_5
         ON SCHEDULE EVERY 1 MONTH
         DO
         call product_list
		 
		 
		 DELIMITER $$
CREATE EVENT event1
ON SCHEDULE EVERY '1' MONTH
STARTS '2011-05-01 00:00:00'
DO 
BEGIN
 -- your code
END$$

DELIMITER ;
         