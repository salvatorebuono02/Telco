drop table if exists user;
create table if not exists user
(
    id             int auto_increment
    primary key,
    firstname      varchar(45) not null,
    lastname       varchar(45) not null,
    username       varchar(45) not null,
    password       varchar(45) not null,
    insolvent      tinyint(1)  null,
    failedPayments int         not null,
    email          varchar(45) not null,
    constraint username_UNIQUE
    unique (username)
    );

drop table if exists employee;
create table if not exists employee
(
    id       int auto_increment
        primary key,
    username varchar(45) not null,
    password varchar(45) not null,
    name     varchar(45) not null,
    lastname varchar(45) not null,
    email    varchar(45) not null,
    constraint employee_id_uindex
        unique (id),
    constraint employee_username_uindex
        unique (username)
);

drop table if exists product;
create table if not exists product
(
    id          int auto_increment
        primary key,
    name        varchar(50) null,
    monthly_fee int         not null
);

drop table if exists validityperiod;
create table if not exists validityperiod
(
    id          int auto_increment
    primary key,
    monthly_fee int null,
    numOfMonths int null
);


drop table if exists service_package;
create table if not exists service_package
(
    id   int auto_increment
        primary key,
    name varchar(50) null
);

drop table if exists service;
create table if not exists service
(
    serviceType     varchar(45) not null,
    id              int auto_increment
        primary key,
    numofGiga       int         null,
    feeForExtraGiga float       null,
    numOfSms        int         null,
    numOfMinutes    int         null,
    feeExtraMin     float       null,
    feeExtraSms     float       null
);


drop table if exists service_servicepkg;
create table if not exists service_servicepkg
(
    service_id int null,
    package_id int null,
    constraint service_servicepkg_service_id_fk
        foreign key (service_id) references service (id),
    constraint service_servicepkg_service_package_id_fk
        foreign key (package_id) references service_package (id)
);



drop table if exists alert;
create table if not exists alert
(
    userId        int         null,
    userName      varchar(45) null,
    userEmail     varchar(45) null,
    amount        float       null,
    lastRejection date        null,
    id            int auto_increment
        primary key,
    constraint alert_id_uindex
        unique (id),
    constraint alert_user_id_fk
        foreign key (userId) references user (id)
);

drop table if exists `order`;
create table if not exists `order`
(
    id                    int auto_increment
        primary key,
    date_of_subscription  date       null,
    date_of_creation      date       null,
    creator               int        null,
    confirmed             tinyint(1) null,
    serviceId             int        null,
    validityId            int        null,
    totalValueOrder       float      null,
    date_end_subscription date       null,
    totalvalueservices    float      null,
    totalvalueproducts    float      null,
    constraint orderBy
        foreign key (creator) references user (id),
    constraint order_service_package_id_fk
        foreign key (serviceId) references service_package (id),
    constraint order_validityperiod_id_fk
        foreign key (validityId) references validityperiod (id)
);

create index orderBy_id
    on `order` (creator);

drop table if exists order_product;
create table if not exists order_product
(
    product_id int null,
    order_id   int null,
    constraint order_product_order_id_fk
        foreign key (order_id) references `order` (id),
    constraint order_product_product_id_fk
        foreign key (product_id) references product (id)
);


drop table if exists service_package_validity_periods;
create table if not exists service_package_validity_periods
(
    servicePackage_id int null,
    validityPeriod_id int null,
    constraint service_package_validity_periods_service_package_id_fk
        foreign key (servicePackage_id) references service_package (id),
    constraint service_package_validity_periods_validityperiod_id_fk
        foreign key (validityPeriod_id) references validityperiod (id)
);


drop table if exists service_package_product;
create table if not exists service_package_product
(
    SERVICEPACKAGES_ID int null,
    PRODUCTS_ID        int null,
    constraint service_package_product_product_id_fk
    foreign key (PRODUCTS_ID) references product (id),
    constraint service_package_product_service_package_id_fk
    foreign key (SERVICEPACKAGES_ID) references service_package (id)
    );

create index I_SRVCDCT_ELEMENT
    on service_package_product (PRODUCTS_ID);

create index I_SRVCDCT_SERVICEPACKAGES_ID
    on service_package_product (SERVICEPACKAGES_ID);




