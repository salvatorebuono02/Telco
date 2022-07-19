insert into telco.employee (id, username, password,name,lastname,email)
values  (1, 'emp', 'emp','emp','emp','emp'),
        (2, 'employee', 'test','employee','employee','employee');

insert into telco.service (serviceType,id,numOfGiga,feeForExtraGiga,numOfSms,numOfMinutes,feeExtraMin,feeExtraSms)
values ('FixedInternetService',1,500,3.0,null,null,null,null),
       ('FixedInternetService',2,350,2.5,null,null,null,null),
       ('MobilePhoneService',3,null,null,500,1000,2.4,1.2),
       ('MobilePhoneService',4,null,null,100,500,1.2,0.7),
       ('MobileInternetService',5,50,2.0,null,null,null,null),
       ('MobileInternetService',6,30,1.5,null,null,null,null),
       ('FixedPhoneService',7,null,null,null,null,null,null);

insert into telco.product (id, name, monthly_fee)
values  (1, 'iphone 13', 8),
        (2, 'iphone 13', 8),
        (3, 'iphone 13', 8),
        (4, 'iphone 13', 8),
        (5, 'iphone 13', 8),
        (6, 'iphone 13', 8),
        (7, 'macbook', 25),
        (8, 'macbook', 25),
        (9, 'macbook', 25),
        (10, 'macbook', 25),
        (11, 'macbook', 25),
        (12, 'xiaomi 11', 5),
        (13, 'xiaomi 11', 5),
        (14, 'xiaomi 11', 5),
        (15, 'xiaomi 11', 5),
        (16, 'xiaomi 11', 5),
        (17, 'ipad 2019', 15),
        (18, 'ipad 2019', 15),
        (19, 'ipad 2019', 15),
        (20, 'ipad 2019', 15),
        (21, 'ipad 2019', 15),
        (22, 'airpods', 4),
        (23, 'airpods', 4),
        (24, 'airpods', 4),
        (25, 'airpods', 4),
        (26, 'airpods', 4),
        (27, 'samsung tablet', 12),
        (28, 'samsung tablet', 12),
        (29, 'samsung tablet', 12),
        (30, 'samsung tablet', 12),
        (31, 'samsung tablet', 12);


insert into telco.service_package (id, name)
values  (1001, 'pack1'),
        (1002, 'pack2'),
        (1003, 'pack3'),
        (1004, 'pack4'),
        (1005, 'pack5'),
        (1006, 'pack6');

insert into telco.user (id, firstname, lastname, username,email, password, insolvent, failedPayments)
values  (1, 'salvatore', 'buono', 'totto','tottobuono@polimi.it', 'password', 0, 0),
        (2, 'camilla', 'blasucci','cami99', 'camilla@gmail.it', 'ciao', 0, 0),
        (3, 'test', 'test','test', 'test', 'test', 0, 0);

insert into telco.validityperiod (id, monthly_fee, numOfMonths)
values  (1, 30, 12),
        (2, 15, 12),
        (3, 25, 24),
        (4, 10, 24),
        (5, 20, 36),
        (6, 8, 36),
        (7, 15, 48),
        (8, 5, 48);


insert into telco.service_package_validity_periods(servicePackage_id, validityPeriod_id)
values  (1001,1),
        (1001,3),
        (1002,1),
        (1002,3),
        (1003,2),
        (1003,4),
        (1004,4),
        (1004,8),
        (1005,1),
        (1005,3),
        (1005,6),
        (1006,3),
        (1006,5),
        (1006,7);

insert into telco.service_package_product(servicePackage_id, product_id)
values  (1001,1),
        (1001,7),
        (1001,12),
        (1002,17),
        (1002,7),
        (1002,27),
        (1003,22),
        (1003,1),
        (1003,22),
        (1004,7),
        (1004,27),
        (1005,12),
        (1005,1),
        (1006,1),
        (1006,7),
        (1006,12),
        (1006,17),
        (1006,22),
        (1006,27);

insert into telco.service_servicepkg(service_id, package_id)
values (1,1002),
       (1,1004),
       (7,1004),
       (2,1001),
       (3,1003),
       (3,1001),
       (4,1005),
       (2,1006),
       (4,1006),
       (7,1006);