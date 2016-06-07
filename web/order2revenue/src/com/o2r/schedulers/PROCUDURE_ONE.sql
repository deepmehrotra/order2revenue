DELIMITER //

CREATE PROCEDURE product_list
()
BEGIN
DECLARE aProductId long;
DECLARE bQuantity long;
DECLARE cProductPrice double;
DECLARE dProductDate DATETIME;
DECLARE today DATE;
DECLARE stockIdVar long;
DECLARE cur1 CURSOR FOR SELECT productId,quantity,productPrice,productDate FROM order2revenue_schema.product;
SELECT MAX(closingStocks_stockId) into stockIdVar from product_productstocklist;

OPEN cur1;

  read_loop: LOOP
  
    FETCH cur1 INTO aProductId, bQuantity,cProductPrice,dProductDate;
    SET stockIdVar=stockIdVar+1;
    
    insert into product_productstocklist values (aProductId,stockIdVar);
    insert into productstocklist values(stockIdVar,today,month(today),cProductPrice,bQuantity,DAY(dProductDate),YEAR(today));
                                        /* stockId  CRDate MONTH  PRICE STOCKAVAILABLE UDDate   YEAR */
    
   END LOOP;
   commit;
END //