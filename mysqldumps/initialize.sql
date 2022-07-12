insert into telco.employee (id, username, password,name,lastname,email)
values  (1, 'emp', 'emp','emp','emp','emp'),
        (2, 'employee', 'test','employee','employee','employee');

insert into telco.fixedinternetservice (id, id_servicePkg, numOfGiga, feeForExtraGiga)
values  (1, null, 5, 40),
        (2, null, 4, 10);

insert into telco.fixedphoneservice (id, id_servicePkg, numOfMinutes, numOfSms, feeExtraMin, feeExtraSms)
values  (1, null, 1500, 100, 0, 0),
        (2, null, 5000, 500, 1, 2);

insert into telco.mobileinternetservice (id, id_servicePkg, numOfGiga, feeForExtraGiga)
values  (1, null, 20, 2),
        (2, null, 50, 3);

insert into telco.product (id, name, monthly_fee)
values  (1, 'iphone 13', 15),
        (2, 'macbook', 25),
        (3, 'xiaomi 11', 10),
        (4, 'ipad 2019', 20),
        (5, 'airpods', 5),
        (6, 'samsung tablet', 18);

insert into telco.service_package (id, name)
values  (1001, 'pack1'),
        (1002, 'pack2'),
        (1003, 'pack3'),
        (1004, 'pack4'),
        (1005, 'pack5'),
        (1006, 'pack6');

insert into telco.user (id, firstname, lastname, username,email, password, insolvent, failedPayments)
values  (1, 'salvatore', 'buono', 'tottobuono','tottobuono', '0807mary', 0, 0),
        (2, 'ciao', 'ciao','ciao', 'ciao', 'ciao', 0, 0),
        (3, 'test', 'test','test', 'test', 'test', 0, 0);

insert into telco.validityperiod (id, monthly_fee, numOfMonths)
values  (1, 20, 12),
        (2, 18, 24),
        (3, 15, 36),
        (4, 30, 12);

insert into telco.service_package_validity_periods(servicePackage_id, validityPeriod_id)
values  (1001,2),
        (1001,4),
        (1002,1),
        (1002,3),
        (1003,2),
        (1003,4),
        (1004,2),
        (1005,1),
        (1005,3),
        (1005,4),
        (1006,3);

insert into telco.service_package_product(SERVICEPACKAGES_ID, PRODUCTS_ID)
values  (1001,2),
        (1001,4),
        (1001,5),
        (1002,1),
        (1002,6),
        (1003,2),
        (1003,4),
        (1003,5),
        (1004,2),
        (1005,5),
        (1005,3),
        (1005,1),
        (1006,3);