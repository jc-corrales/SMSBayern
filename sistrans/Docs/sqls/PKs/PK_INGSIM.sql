--------------------------------------------------------
--  DDL for Index PK_INGSIM
--------------------------------------------------------

  CREATE UNIQUE INDEX "ISIS2304B041720"."PK_INGSIM" ON "ISIS2304B041720"."INGREDIENTESSIMILARES" ("ID_INGREDIENTE1", "ID_INGREDIENTE2", "ID_REST") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS NOLOGGING 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TBSPROD" ;
