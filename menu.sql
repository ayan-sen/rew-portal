/*
-- Query: SELECT * FROM rewdb.menu
LIMIT 0, 1000

-- Date: 2022-08-05 16:03
*/
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (1,'Admin','add',1,NULL,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (2,'Unit','add',1,1,'admin/unit');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (3,'Raw Material','add',2,1,'admin/raw-material');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (4,'Customer','add',3,1,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (5,'Add','add',1,4,'admin/client/add');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (6,'Show','add',2,4,'admin/clients');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (7,'Projects','add',2,NULL,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (8,'Add','add',1,7,'transaction/project/add');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (9,'Show','add',2,7,'transaction/projects');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (10,'Orders','add',3,19,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (11,'Add','add',1,10,'transaction/order-placement/add');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (12,'Show','add',2,10,'transaction/orders');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (13,'Delivery','add',4,19,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (14,'Add','add',1,13,'transaction/order-delivery/add');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (15,'Show','add',2,13,'transaction/deliveries');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (16,'Daily Work Log','add',5,NULL,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (17,'Add','add',1,16,'transaction/log/add');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (18,'Show','add',2,16,'transaction/logs');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (19,'Raw Materials','add',2,NULL,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (20,'Expense','add',6,NULL,'');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (21,'Show','add',2,20,'transaction/expenses');
INSERT INTO `` (`id`,`displayName`,`iconName`,`menuOrder`,`parent`,`route`) VALUES (22,'Add','add',1,20,'transaction/expense/add');
