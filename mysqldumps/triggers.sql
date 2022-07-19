DROP TABLE IF EXISTS totalpurchaseperpackage;
DROP TRIGGER IF EXISTS addPurchaseToPackage;
DROP TRIGGER IF EXISTS updatePurchaseToPackage;
drop trigger if exists insertServicePackage;

drop table if exists totalPurchasePerPackAndValidityPeriod;
drop trigger if exists insertServicePackageAndValPeriod;
drop trigger if exists addPurchaseToPackageAndValPeriod;
drop trigger if exists updatePurchaseToPackageAndValPeriod;

drop table if exists salesPackage;
drop trigger if exists addSales;
drop trigger if exists updateSales;
drop trigger if exists createSales;

drop table if exists avgnumofproductsperpackage;
drop table if exists optionalProductsPerOrder;
drop table if exists totalnumberofoptionalproduct;
drop trigger if exists createOrder;
drop trigger if exists createPackOptProd;
drop trigger if exists addOptProduct;
drop trigger if exists updateOptProduct;
drop trigger if exists updateSales;

drop table if exists insolventuser;
drop trigger if exists updateInsolventUser;

drop table if exists alertsForReport;
drop trigger if exists addAlert;

drop table if exists suspendedorders;
drop trigger if exists addSuspendedOrder;
drop trigger if exists updateSuspendedOrder;

drop table if exists salesForEachOptproduct;
drop trigger if exists addSalesForEachProduct;
drop trigger if exists updateSalesForEachProduct;
drop trigger if exists createSalesForEachProduct;




create table totalpurchaseperpackage
(
    package_id int not null primary key ,
    totalPurchases int default 0 not null ,
    foreign key (package_id) references service_package(id)
);

delimiter //
create definer = current_user trigger insertServicePackage
    after insert on service_package for each row begin
    insert into totalpurchaseperpackage(package_id)
    values (NEW.id);
end //
delimiter ;

delimiter //
create definer = current_user trigger addPurchaseToPackage
    after insert on `order` for each row
begin
    if NEW.confirmed=true then
        update totalpurchaseperpackage set totalPurchases=totalPurchases+1
        where package_id in (select o.serviceId
                             from `order` o
                             where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;


delimiter //
create definer = current_user trigger updatePurchaseToPackage
    after update on `order` for each row begin
    if NEW.confirmed=true then
        update totalpurchaseperpackage set totalPurchases=totalPurchases+1
        where package_id in (select o.serviceId
                             from `order` o
                             where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;


create table totalPurchasePerPackAndValidityPeriod(
             id int not null primary key auto_increment,
             package_id int not null ,
             valPeriod_id int not null ,
             totalPurchases int not null DEFAULT 0,
             foreign key (package_id) references service_package(id),
             foreign key (valPeriod_id) references validityperiod(id)
);

delimiter //
create definer = current_user trigger insertServicePackageAndValPeriod
    after insert on service_package_validity_periods for each row begin
    insert into totalPurchasePerPackAndValidityPeriod(package_id, valPeriod_id)
    values (NEW.servicePackage_id,NEW.validityPeriod_id);
end //
delimiter ;

delimiter //
create definer = current_user trigger addPurchaseToPackageAndValPeriod
    after insert on `order` for each row begin
    if NEW.confirmed=true then
        update totalPurchasePerPackAndValidityPeriod set totalPurchases=totalPurchases+1
        where (package_id,valPeriod_id) in (select o.serviceId,o.validityId
                                            from `order` o
                                            where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger updatePurchaseToPackageAndValPeriod
    after update on `order` for each row begin
    if NEW.confirmed=true then
        update totalPurchasePerPackAndValidityPeriod set totalPurchases=totalPurchases+1
        where (package_id,valPeriod_id) in (select o.serviceId,o.validityId
                                            from `order` o
                                            where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;



create table salesPackage
(
    package_id int not null primary key ,
    totalSalesWithProduct int not null DEFAULT 0,
    totalSalesWithoutProduct int not null default 0,
    foreign key (package_id) references service_package(id)

);

delimiter //
create definer =current_user trigger createSales
    after insert on `service_package` for each row begin
    insert into salesPackage(package_id)
    values (NEW.id);
end //
delimiter ;

delimiter //
create definer = current_user trigger addSales
    after insert on `order` for each row begin
    declare x,y,z float;
    if NEW.confirmed=true then
        select o.totalvalueservices,o.totalvalueproducts into x,y
        from `order` o
        where o.serviceId=NEW.serviceId;
        update salesPackage s
        set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y,
            s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
        where s.package_id in (select o.serviceId
                               from `order` o
                               where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger updateSales
    after update on `order` for each row
begin
    declare x,y,z float;
    if NEW.confirmed=true then
        select o.totalvalueservices,o.totalvalueproducts into x,y
        from `order` o
        where o.serviceId=NEW.serviceId;
        update salesPackage s
        set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y,
            s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
        where s.package_id in (select o.serviceId
                               from `order` o
                               where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;



create table avgnumofproductsperpackage
(
    package_id int not null primary key ,
    avg float default -1 not null ,
    foreign key (package_id) references service_package(id)
);

create table totalnumberofoptionalproduct
(
    package_id int not null primary key ,
    total int default 0,
    foreign key (package_id) references service_package(id)
);

create table optionalProductsPerOrder
(
    order_id int not null primary key,
    total int default 0
);


delimiter //
create definer = current_user trigger createOrder
    after insert on service_package for each row begin
    insert into avgnumofproductsperpackage(package_id)
    values(NEW.id);
end //
delimiter ;

delimiter //
create definer = current_user trigger createPackOptProd
    after insert on service_package for each row begin
    insert into totalnumberofoptionalproduct(package_id)
    values(NEW.id);
end //
delimiter ;


delimiter //
create definer =current_user trigger updateOptProduct
    after update on product for each row begin
    if NEW.orderId is not null and
       NEW.orderId in (select o.id from `order` o where o.confirmed= true) then
        delete from optionalProductsPerOrder where order_id=NEW.orderId;
        insert into optionalProductsPerOrder(order_id, total)
            ( select p.orderId, COUNT(p.id)
              from product p
              where p.orderId is not null and p.id=NEW.id
            );

        update totalnumberofoptionalproduct t, optionalProductsPerOrder op
        set t.total=t.total +  op.total
        where t.package_id in (select o.serviceId from `order` o where o.id=NEW.orderId);


        delete from avgnumofproductsperpackage
        where package_id in (select o.serviceId from `order` o where o.id=NEW.orderId);
        insert into avgnumofproductsperpackage(package_id, avg)
        select t.package_id, (o.total/t.totalPurchases)
        from totalpurchaseperpackage t left outer join totalnumberofoptionalproduct o
                                        on t.package_id=o.package_id
        where t.package_id in (select o.serviceId from `order` o where o.id=NEW.orderId);
    end if;
end //
delimiter ;


create table alertsForReport
(
    alert_id int not null ,
    foreign key (alert_id) references alert(id)
);

create table insolventuser
(
    user_id int not null ,
    foreign key (user_id) references user(id)
);

create table suspendedorders
(
    order_id int not null ,
    foreign key (order_id) references `order`(id)
);

delimiter //
create definer = current_user trigger addAlert
    after insert on alert for each row begin
    insert into alertsForReport
    values (NEW.id);
end//
delimiter ;

delimiter //
create definer = current_user trigger updateInsolventUser
    after update on user for each row begin
    if NEW.insolvent=true then
        if(NEW.id not in (select user_id from insolventuser)) then
            insert into insolventuser
            values (NEW.id);
        end if;
    else
        delete from insolventuser i
        where i.user_id=NEW.id;
    end if;
end //
delimiter ;

delimiter //
create definer = current_user trigger addSuspendedOrder
    after insert on `order` for each row begin
    if(NEW.confirmed=false) then
        if(NEW.id not in (select order_id from suspendedorders)) then
            insert into suspendedorders
            select o.id
            from `order` o
            where NEW.id=o.id;
        end if;
    else
        if(NEW.id in (select order_id from suspendedorders)) then
            delete from suspendedorders s
            where s.order_id=NEW.id;
        end if;
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger updateSuspendedOrder
    after update on `order` for each row begin
    if(NEW.confirmed=false) then
        if(NEW.id not in (select order_id from suspendedorders)) then
            insert into suspendedorders
            select o.id
            from `order` o
            where NEW.id=o.id;
        end if;
    else
        if(NEW.id in (select order_id from suspendedorders)) then
            delete from suspendedorders s
            where s.order_id=NEW.id;
        end if;
    end if;
end //
delimiter ;

create table salesForEachOptproduct
(
    optionalProduct_id int not null primary key ,
    sales float not null default 0,
    foreign key (optionalProduct_id) references product(id)
);

delimiter //
create definer=current_user trigger createSalesForEachProduct
    after insert on product for each row
begin
    insert into salesForEachOptproduct(optionalProduct_id)
    values (NEW.id);
end //
delimiter ;

delimiter //
create definer =current_user trigger addSalesForEachProduct
    after update on product for each row begin
    declare y float;
    declare z,id int;
    if NEW.orderId is not null and NEW.orderId in (select o.id from `order` o where o.confirmed= true) then
        select p.id,p.monthly_fee,v.numOfMonths into id,y,z
        from product p join `order` o on p.orderId = o.id
                       join validityperiod v on o.validityId = v.id
        where o.id=NEW.orderId and p.id=NEW.id;
        update salesForEachOptproduct s
        set s.sales=s.sales+(y*z)
        where s.optionalProduct_id = id and id = NEW.id;
    end if;
end //
delimiter ;
