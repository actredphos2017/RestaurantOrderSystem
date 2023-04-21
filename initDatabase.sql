drop database if exists RestaurantDatabase;
create database RestaurantDatabase;
use RestaurantDatabase;

create table Restaurants
(
    ID         varchar(16) not null,
    Name       varchar(64) not null,
    Address    text        not null,
    Phone      varchar(16) not null,
    HeadPic    text        not null,
    QRCodeUrl  text        not null,
    Commit     text        not null,
    UpdateFlag boolean default true,
    primary key (ID)
) engine = InnoDB,
  charset = utf8;

create table CategoryOfDishes
(
    ID           varchar(16) not null,
    RestaurantID varchar(16) not null,
    Ranking      int         not null,
    Name         varchar(64) not null,
    primary key (ID),
    index (RestaurantID, Ranking),
    foreign key (RestaurantID) references Restaurants (ID)
) engine = InnoDB,
  charset = utf8;

create table Dishes
(
    CategoryID varchar(16) not null,
    Ranking    int         not null,
    Name       varchar(32) not null,
    Pic        text        not null,
    Commit     text        not null,
    DIYOptions longtext    not null,
    primary key (CategoryID, Ranking),
    index (CategoryID, Ranking),
    foreign key (CategoryID) references CategoryOfDishes (ID)
) engine = InnoDB,
  charset = utf8;

create table ManagerAccounts
(
    Username     varchar(16) not null,
    Password     varchar(32) not null,
    RestaurantID varchar(16) not null,
    Phone        varchar(16) not null,
    Power        int         not null,
    primary key (RestaurantID, Username),
    foreign key (RestaurantID) references Restaurants (ID)
) engine = InnoDB,
  charset = utf8;

create table CustomerAccounts
(
    ID              varchar(16) not null,
    Username        varchar(16) not null,
    Password        varchar(32) not null,
    Phone           varchar(16) not null,
    SessionAuthCode varchar(32) default null,
    primary key (ID)
) engine = InnoDB,
  charset = utf8;

create table Orders
(
    ID              varchar(32) not null,
    RestaurantID    varchar(16) not null,
    CustomerMessage text        not null,
    ValueSum        double      not null,
    primary key (ID),
    foreign key (RestaurantID) references Restaurants (ID)
) engine = InnoDB,
  charset = utf8;

create table OrderDetails
(
    OrderID         varchar(32) not null,
    BuyerID         varchar(16) default null,
    DishesName      varchar(32) not null,
    CustomerMessage text        not null,
    DIYOptions      longtext    not null,
    foreign key (OrderID) references Orders (ID)
) engine = InnoDB,
  charset = utf8;