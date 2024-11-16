

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

use hotelmanagement;

-- Khóa ngoại cho bảng staff
ALTER TABLE staff
ADD CONSTRAINT FK_Staff_Role FOREIGN KEY (roleId) REFERENCES role(roleId);

-- Khóa ngoại cho bảng feedback
ALTER TABLE feedback
ADD CONSTRAINT FK_Feedback_Customer FOREIGN KEY (customerId) REFERENCES customer(customerId);

-- Khóa ngoại cho bảng room
ALTER TABLE room
ADD CONSTRAINT FK_Room_RoomType FOREIGN KEY (roomTypeId) REFERENCES roomtype(roomTypeId);

-- Khóa ngoại cho bảng booking
ALTER TABLE booking
ADD CONSTRAINT FK_Booking_Customer FOREIGN KEY (customerId) REFERENCES customer(customerId),
ADD CONSTRAINT FK_Booking_Room FOREIGN KEY (roomId) REFERENCES room(roomId),
ADD CONSTRAINT FK_Booking_Staff FOREIGN KEY (staffId) REFERENCES staff(staffId);

-- Khóa ngoại cho bảng payment
ALTER TABLE payment
ADD CONSTRAINT FK_Payment_Booking FOREIGN KEY (bookingId) REFERENCES booking(bookingId),
ADD CONSTRAINT FK_Payment_Customer FOREIGN KEY (customerId) REFERENCES customer(customerId);

-- Khóa ngoại cho bảng useraccount
ALTER TABLE useraccount
ADD CONSTRAINT FK_UserAccount_Role FOREIGN KEY (roleId) REFERENCES role(roleId),
ADD CONSTRAINT FK_UserAccount_Customer FOREIGN KEY (customerId) REFERENCES customer(customerId),
ADD CONSTRAINT FK_UserAccount_Staff FOREIGN KEY (staffId) REFERENCES staff(staffId);

-- Khóa ngoại cho bảng image
ALTER TABLE image
ADD CONSTRAINT FK_Image_RoomType FOREIGN KEY (roomTypeId) REFERENCES roomtype(roomTypeId);

ALTER TABLE room
ADD COLUMN roomNumber VARCHAR(50) NOT NULL;

use hotelmanagement;
alter table staff
drop foreign key FK_Staff_Role;

ALTER TABLE customer
ADD COLUMN user_account_id INT;
alter table customer
ADD CONSTRAINT fk_customer_account FOREIGN KEY (user_account_Id) REFERENCES useraccount(userAccountId) ON DELETE CASCADE;  

alter table useraccount
drop column StaffId;

INSERT INTO `role` VALUES ('ADMIN'),('CUSTOMER'),('STAFF');
INSERT INTO `roomtype` VALUES (100000,'Phong Don');
INSERT INTO `image` VALUES (1,'Phong Don','http://res.cloudinary.com/dy79ojxbo/image/upload/v1730452414/Phong%20Don_6f22f250-1f3f-42f6-91e2-126f8e3f2170.png'),(25,12,'Phong Don','http://res.cloudinary.com/dy79ojxbo/image/upload/v1730452432/Phong%20Don_6716aa0b-654d-466b-abbb-fdfd22be795e.png');
INSERT INTO `room` VALUES (1,'Available','Add room test1',1,'102');
INSERT INTO `useraccount` VALUES ('tahuynhduc','$2a$12$uSrREQRLbcMQn7HbyYv3yeBp44.MZgIOLEGbu42OhixwaCnspLJ1m',NULL,1,1);

alter table customer
drop column `password`;

use hotelmanagement;
alter table booking
add column quantityRoom int;

create table bookingdetail(
	bookingDetailId int primary key auto_increment,
    bookingId int,
    roomId int,
    price float,
    foreign key (bookingId) references booking(bookingId),
    foreign key (roomId) references room(roomId)
);

alter table booking 
drop foreign key FK_Booking_Room;

alter table booking
drop column roomType;

alter table roomtype
add column `description` text;

alter table booking
add column roomType int;

alter table roomtype
add column capacity int;

use hotelmanagement;
alter table booking
add column cancleStatus text;

alter table staff
add column user_account_id int;

alter table staff
ADD CONSTRAINT fk_staff_account FOREIGN KEY (user_account_id) REFERENCES useraccount(userAccountId) ON DELETE CASCADE;  


