

use hotelmanagement;
drop table staff;
create table customer(
	customerId int auto_increment primary key,
    customerName varchar(255),
    phoneNumber varchar(10),
    gender varchar(10),
    address varchar(255),
    countryName varchar(255),
    identificationNumber varchar(255),
    email varchar(255),
    `password` varchar(255)
);
create table `role`(
	roleId int auto_increment primary key,
    roleName varchar(255)
);
create table staff(
	staffId int auto_increment primary key,
    staffName varchar(255),
    phoneNumber varchar(10),
    gender varchar(255),
    address varchar(255),
    identificationNumber varchar(255),
    email varchar(255),
    birthday date,
    salary double,
    roleId int,
    `password` varchar(255)
);
create table feedback(
	feedbackId int auto_increment primary key,
    content varchar(255),
    rating int,
    feedbackDate date,
    customerId int
);
create table roomtype(
	roomTypeId int auto_increment primary key,
    price double,
    roomTypeName varchar(255)
);
create table room(
	roomId int auto_increment primary key,
    bedQuantity int not null,
    `status` varchar(255) not null,
    `description` varchar(255) not null,
    roomTypeId int not null
);
create table booking(
	bookingId int auto_increment primary key,	
    customerId int,
    roomId int not null,
    bookingDate date not null,
    bookingStatus varchar(255),
    quantity int not null,
    totalAmount double not null,
    checkoutDate date not null,
    checkinDate date not null,
    staffId int
);
create table payment(
	paymentId int auto_increment primary key,
    bookingId int,
    customerId int,
    paymentDate date,
    paymentMethod varchar(255),
    paymentAmount double
);

use hotelmanagement;
create table useraccount(
	userAccountId int primary key auto_increment,
    userName varchar(255),
    `password` varchar(255),
    activationCode text,
    enabled boolean,
    roleId int,
    customerId int,
    staffId int
);

create table image(
	imageId int auto_increment primary key,
    roomTypeId int,
    `name` varchar(255),
    `imageUrl` text
);