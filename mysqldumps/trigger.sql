DROP TABLE IF EXISTS totalpurchaseperpackage;
DROP TRIGGER IF EXISTS addPurchaseToPackage;
DROP TRIGGER IF EXISTS updatePurchaseToPackage;
drop trigger if exists createOrderAndAssociatedServicePackage;

drop table if exists totalPurchasePerPackAndValidityPeriod;
drop trigger if exists createOrderAndAssociatedServicePackageAndValPeriod;
drop trigger if exists addPurchaseToPackageAndValPeriod;
drop trigger if exists updatePurchaseToPackageAndValPeriod;

drop table if exists salesPackage;
drop trigger if exists addSales;
drop trigger if exists updateSales;
drop trigger if exists createSales;

drop table if exists avgnumofproductsperpackage;
drop table if exists totalnumberofoptionalproduct;
drop trigger if exists createOrder;
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




create table totalpurchaseperpackage
(
    package_id int not null primary key ,
    totalPurchases int default 0 not null ,
    foreign key (package_id) references service_package(id)
);

delimiter //
create definer = current_user trigger createOrderAndAssociatedServicePackage
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
     package_id int not null ,
     valPeriod_id int not null ,
     totalPurchases int not null DEFAULT 0,
     foreign key (package_id) references service_package(id),
     foreign key (valPeriod_id) references validityperiod(id)
);

delimiter //
create definer = current_user trigger createOrderAndAssociatedServicePackageAndValPeriod
    after insert on service_package for each row begin
    insert into totalPurchasePerPackAndValidityPeriod(package_id, valPeriod_id)
    values (NEW.id,NEW.validityPeriodId);
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
create definer = current_user trigger addSales
    after insert on `order` for each row begin
    declare x,y float;
    if NEW.confirmed=true then
        select o.totalvalueservices,o.totalvalueproducts into x,y
        from `order` o
        where o.serviceId=NEW.serviceId;
        update salesPackage s
        set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y, s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
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
    declare x,y float;
    if NEW.confirmed=true then
        select o.totalvalueservices,o.totalvalueproducts into x,y
        from `order` o
        where o.serviceId=NEW.serviceId;
        update salesPackage s
        set s.totalSalesWithProduct=s.totalSalesWithProduct+x+y, s.totalSalesWithoutProduct=s.totalSalesWithoutProduct+x
        where s.package_id in (select o.serviceId
                               from `order` o
                               where o.serviceId=NEW.serviceId);
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger createSales
    after insert on `order` for each row begin
    if NEW.confirmed=true then
        insert into salesPackage(package_id)
        values (NEW.serviceId);
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger createUpdateSales
    after update on `order` for each row begin
    if NEW.confirmed=true then
        insert into salesPackage(package_id)
        values (NEW.serviceId);
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
    total int not null default 0,
    foreign key (package_id) references service_package(id)
);


delimiter //
create definer = current_user trigger createOrder
    after insert on service_package for each row begin
    insert into avgnumofproductsperpackage(package_id)
    values(NEW.id);
end //
delimiter ;

delimiter //
create definer =current_user trigger addOptProduct
    after insert on `order` for each row begin
    if NEW.confirmed=true then
        delete from totalnumberofoptionalproduct t
        where t.package_id in (select s.id
                               from service_package s
                               where s.id=NEW.serviceId);
        insert into totalnumberofoptionalproduct
        select sp.id, count(*)
        from `order` as o
                 join order_product op on o.id = op.order_id
                 join service_package sp on sp.id = o.serviceId
        where o.confirmed=true
        group by sp.id;

        delete from avgnumofproductsperpackage
        where package_id in (select s.id
                             from service_package s
                             where s.id=NEW.serviceId);
        insert into avgnumofproductsperpackage
        select t.package_id,ifnull((o.total/t.totalPurchases),0.0)
        from totalpurchaseperpackage t left outer join totalnumberofoptionalproduct o on t.package_id=o.package_id
        where t.package_id in (select sp.id
                               from service_package sp
                               where sp.id=NEW.serviceId);
    end if;
end //
delimiter ;

delimiter //
create definer = current_user trigger updateOptProduct
    after update on `order` for each row begin
    if NEW.confirmed=true then
        delete from totalnumberofoptionalproduct t
        where t.package_id in (select s.id
                               from service_package s
                               where s.id=NEW.serviceId);
        insert into totalnumberofoptionalproduct
        select sp.id, count(*)
        from `order` as o
                 join order_product op on o.id = op.order_id
                 join service_package sp on sp.id = o.serviceId
        where o.confirmed=true
        group by sp.id;

        delete from avgnumofproductsperpackage
        where package_id in (select s.id
                             from service_package s
                             where s.id=NEW.serviceId);
        insert into avgnumofproductsperpackage
        select t.package_id,ifnull((o.total/t.totalPurchases),0.0)
        from totalpurchaseperpackage t left outer join totalnumberofoptionalproduct o on t.package_id=o.package_id
        where t.package_id in (select sp.id
                               from service_package sp
                               where sp.id=NEW.serviceId);
    end if;
end //
delimiter ;

create table alertsForReport
(
    alert_id int not null ,
    foreign key (alert_id) references alert(id)
);

delimiter //
create definer = current_user trigger addAlert
    after insert on alert for each row begin
    insert into alertsForReport
    values (NEW.id);
end//
delimiter ;


create table insolventuser
(
    user_id int not null ,
    foreign key (user_id) references user(id)
);

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

create table suspendedorders
(
    order_id int not null ,
    foreign key (order_id) references `order`(id)
);

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
    sales float not null ,
    foreign key (optionalProduct_id) references product(id)
);

delimiter //
create definer =current_user trigger addSalesForEachProduct
    after insert on `order` for each row begin
    declare y float;
    declare id int;
    if NEW.confirmed=true then
        select p.id,(p.monthly_fee*v.numOfMonths) into id,y
        from product p join order_product op on p.id = op.order_id
                       join `order` o on op.order_id = o.id
                       join validityperiod v on v.id = o.validityId
        where o.id=NEW.id
        group by p.id;
        update salesForEachOptproduct s
        set s.sales=s.sales+y
        where s.optionalProduct_id = id;
    end if;
end //
delimiter ;

delimiter //
create definer =current_user trigger updateSalesForEachProduct
    after update on `order` for each row begin
    declare y float;
    declare id int;
    if NEW.confirmed=true then
        select p.id,(p.monthly_fee*v.numOfMonths) into id,y
        from product p join order_product op on p.id = op.order_id
                       join `order` o on op.order_id = o.id
                       join validityperiod v on v.id = o.validityId        where o.id=NEW.id
        group by p.id;
        update salesForEachOptproduct s
        set s.sales=s.sales+y
        where s.optionalProduct_id = id;
    end if;
end //
delimiter ;