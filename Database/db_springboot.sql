use master
go

if db_id('project_java6') is not null
drop database project_java6
go

create database project_java6
go

use project_java6
go

create table roles
(
	id				tinyint			primary key identity,  -- auto_increment
	[name]	nvarchar(20)	not null unique,
	[description] nvarchar(20)
)
go

create table users
(
	id				bigint			primary key identity,
	username		varchar(20)		not null unique,
	fullname		nvarchar(50)	null,
	phoneNumber		varchar(11)		not null,
	hashPassword	varchar(255)	not null,
	email			varchar(100)	not null unique,
	createdDate		datetime		not null default getdate(), -- now()
	imgUrl			varchar(255)	null,
	isDeleted		bit				not null default 0 -- 0: false, 1: true
)
go
create table authorities
(
	id				tinyint			primary key identity,  -- auto_increment
	userId			bigint			foreign key references users(id),
	rolesId			tinyint			foreign key references roles(id)
)
go

create table product_types
(
	id				tinyint			primary key identity,
	[description]	ntext			null,
	slug			varchar(255)	not null,
	isDeleted		bit				not null default 0
)
go

create table unit_types
(
	id				tinyint			primary key identity,
	[description]	ntext			null,
	isDeleted		bit				not null default 0
)
go

create table product_sale
(
	id tinyint primary  key identity,
	sale	tinyint,
	[description]	ntext		null
)
go
create table products
(
	id				bigint			primary key identity,
	[name]			nvarchar(255)	not null,
	typeId			tinyint			foreign key references product_types(id),
	quantity		int				not null,
	price			decimal(12,3)	not null,
	priceSale		decimal(12,3)	not null,
	unitId			tinyint			foreign key references unit_types(id),
	imgUrl			varchar(255)	null,
	imgUrl1			varchar(255)	null,
	imgUrl2		varchar(255)	null,
	imgUrl3		varchar(255)	null,
	[description]	ntext			null,
	slug			varchar(255)	not null,
	saleId			tinyint			foreign key references product_sale(id),
	isDeleted		bit				not null default 0
)
go

create table orders
(
	id				bigint			primary key identity,
	userId			bigint			foreign key references users(id),
	[address]		nvarchar(255)	not null,
	phone			varchar(11)		not null,
	createdDate		datetime		not null default getdate(),
	isActive		bit				not null default 0
)
go

create table order_details
(
	id				bigint			primary key identity,
	orderId			bigint			foreign key references orders(id),
	productId		bigint			foreign key references products(id),
	price			decimal(12,3)	not null,
	quantity		int				not null
)
go

insert into roles([name],[description]) values
('CUST','Customers'),
('DIRE','Directors'),
('STAF','Staffs')
go



-- duynt: pass 111
insert into users(username, fullname, hashPassword, email, imgUrl) values
('duynt', N'Nguyễn Trần Duy', '$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO', 'duynt@abc.com', 'default.png')
go

insert into authorities(userId,rolesId) values
(1,1)
go
insert into product_types([description], slug) values
(N'Smart Phone', 'dien-thoai'),
(N'Laptop',		'laptop')
go

insert into unit_types([description]) values
(N'Chiếc'),
(N'Bộ')
go

insert into product_sale(sale,[description]) values
(10,'Sale 10%'),
(30,'Sale 30%'),
(50,'Sale 50%'),
(0,'')
go

insert into products([name],typeId,quantity,price,priceSale,unitId,imgUrl,imgUrl1,imgUrl2,imgUrl3,[description],slug,saleId,isDeleted) values
('iPhone 13 Pro Max 128GB',1,1000,39000000,0,1,'iphone-13-pro-max-gold.jpg','iphone-13-pro-max-1-2.jpg','iphone-13-pro-max-1-3.jpg','iphone-13-pro-max-1-1.jpg',N'iPhone 13 Pro Max 128 GB - siêu phẩm được mong chờ nhất ở nửa cuối năm 2021 đến từ Apple.','iphone-13',4,0),
('OPPO Reno7 Z 5G',1,5900,10490000,0,1,'oppo-reno7-z-5g.jpg','oppo-reno7-z-1-1.jpg','oppo-reno7-z-1-2.jpg','oppo-reno7-z-5-3',N'OPPO đã trình làng mẫu Reno7 Z 5G với thiết kế OPPO Glow độc quyền, camera mang hiệu ứng như máy DSLR chuyên nghiệp cùng viền sáng kép, máy có một cấu hình mạnh mẽ và đạt chứng nhận xếp hạng A về độ mượt','oppo-reno7',4,0),
('Realme C35',1,2500,3900000,0,1,'realme-c35-thumb-new-600x600.jpg','realme-c35-1-2.jpg','realme-c35-4-2.jpg','realme-c35-12-1.jpg',N'Realme C35 thuộc phân khúc giá rẻ được nhà Realme cho ra mắt với thiết kế trẻ trung, dung lượng pin lớn cùng camera hỗ trợ nhiều tính năng. Đây sẽ là thiết bị liên lạc, giải trí và làm việc ổn định,… cho các nhu cầu sử dụng của bạn.','c35',4,0),
('IPhone 11 64GB',1,1000,25000000,0,1,'iphone-xi-do-600x600.jpg','iphone-11-xanh-la-1-1-org.jpg','iphone-11-tim-1-1-1-org.jpg','iphone-11-trang-1-2-org.jpg',N'Apple đã chính thức trình làng bộ 3 siêu phẩm iPhone 11, trong đó phiên bản iPhone 11 64GB có mức giá rẻ nhất nhưng vẫn được nâng cấp mạnh mẽ như iPhone Xr ra mắt trước đó.','iphone-11',4,0),

('iPhone 12 Pro Max 128GB',1,1000,39000000,0,1,'iphone-13-pro-max-gold.jpg','iphone-13-pro-max-1-2.jpg','iphone-13-pro-max-1-3.jpg','iphone-13-pro-max-1-1.jpg',N'iPhone 13 Pro Max 128 GB - siêu phẩm được mong chờ nhất ở nửa cuối năm 2021 đến từ Apple.','iphone-12',4,0),
('OPPO Reno8 Z 5G',1,5900,10490000,0,1,'oppo-reno7-z-5g.jpg','oppo-reno7-z-1-1.jpg','oppo-reno7-z-1-2.jpg','oppo-reno7-z-5-3',N'OPPO đã trình làng mẫu Reno7 Z 5G với thiết kế OPPO Glow độc quyền, camera mang hiệu ứng như máy DSLR chuyên nghiệp cùng viền sáng kép, máy có một cấu hình mạnh mẽ và đạt chứng nhận xếp hạng A về độ mượt','oppo-reno8',4,0),
('Realme C36',1,2500,3900000,0,1,'realme-c35-thumb-new-600x600.jpg','realme-c35-1-2.jpg','realme-c35-4-2.jpg','realme-c35-12-1.jpg',N'Realme C35 thuộc phân khúc giá rẻ được nhà Realme cho ra mắt với thiết kế trẻ trung, dung lượng pin lớn cùng camera hỗ trợ nhiều tính năng. Đây sẽ là thiết bị liên lạc, giải trí và làm việc ổn định,… cho các nhu cầu sử dụng của bạn.','c36',4,0),
('IPhone 11 ProMax 64GB',1,1000,25000000,0,1,'iphone-xi-do-600x600.jpg','iphone-11-xanh-la-1-1-org.jpg','iphone-11-tim-1-1-1-org.jpg','iphone-11-trang-1-2-org.jpg',N'Apple đã chính thức trình làng bộ 3 siêu phẩm iPhone 11, trong đó phiên bản iPhone 11 64GB có mức giá rẻ nhất nhưng vẫn được nâng cấp mạnh mẽ như iPhone Xr ra mắt trước đó.','iphone-11-promax',4,0),
('Laptop Lenovo Ideapad 3 15ITL6 i5 1135G7/8GB/512GB/Win11',2,10200,16000000,0,1,'lenovo-ideapad-3-15itl6-i5-82h801p9vn-thumb-600x600.jpg','vi-vn-lenovo-ideapad-3-15itl6-i5-82h801p9vn-4.jpg','vi-vn-lenovo-ideapad-3-15itl6-i5-82h801p9vn-3.jpg','vi-vn-lenovo-ideapad-3-15itl6-i5-82h801p9vn-5.jpg',N'Laptop Lenovo Ideapad 3 15ITL6 i5 (82H801P9VN) sở hữu thiết kế mỏng nhẹ cùng hiệu năng mạnh mẽ đến từ CPU Intel thế hệ thứ 11 là người bạn đồng hành lý tưởng cho học sinh, sinh viên hay nhân viên văn phòng.','laptop-lenovo3',4,0),
('Laptop Apple MacBook Air M1 2020 16GB/256GB/7-Core',2,11000,29800000,0,1,'macbook-air-m1-2020-gray-600x600.jpg','apple-macbook-air-m1-2020-z124000de-1-org.jpg','apple-macbook-air-m1-2020-z124000de-3-org.jpg','apple-macbook-air-m1-2020-z124000de-5-org.jpg',N'Laptop Apple MacBook Air M1 2020 có thiết kế đẹp, sang trọng với CPU M1 độc quyền từ Apple cho hiệu năng đồ họa cao, màn hình Retina hiển thị siêu nét cùng với hệ thống bảo mật tối ưu.','laptop-macbook',4,0),
('Laptop Asus TUF Gaming FX516PM i7',2,10200,32900000,0,1,'asus-tuf-gaming-fx516pm-i7-11370h-8gb-512gb-600x600.jpg','vi-vn-asus-tuf-gaming-fx516pm-i7-hn002w-4.jpg','vi-vn-asus-tuf-gaming-fx516pm-i7-hn002w-9.jpg','vi-vn-asus-tuf-gaming-fx516pm-i7-hn002w-013.jpg',N'Mặc dù sở hữu kiểu dáng đơn giản nhưng chiếc Asus TUF Gaming FX516PM i7 11370H (HN002W) này lại mang một cấu hình vượt trội nhờ sở hữu chip thế hệ 11 cùng card đồ hoạ rời, đánh bật mọi đối thủ.','laptop-asus',4,0),
('Laptop MacBook Pro 14 M1 Pro 2021 10-core',2,13100,77990000,0,1,'macbook-pro-14-inch-m1-pro-2021-thumb-600x600.jpg','macbook-pro-14-inch-m1-max-2021-bac-1.jpg','macbook-pro-14-inch-m1-max-2021-bac-5.jpg','macbook-pro-14-inch-m1-max-2021-bac-2.jpg',N'Sự ra đời của chiếc MacBook Pro 14 inch M1 Pro 2021 như đại diện cho một thế hệ laptop mới, tân tiến và đầy tiềm năng với bộ vi xử lý hiện đại, cùng thiết kế sang trọng, thời thượng, xứng tầm người cộng sự đắc lực trên mọi cuộc hành trình của bạn.','laptop-macbook-pro',4,0)
go

CREATE PROC sp_getTotalPricePerMonth
(
	@month varchar(2),
	@year varchar(4)
)
AS BEGIN
	DECLARE @result varchar(15)
	SET @result = (
				SELECT
					SUM(order_details.price * order_details.quantity) as 'totalPrice'
				FROM 
					orders
						INNER JOIN order_details ON orders.id = order_details.orderId
				WHERE
					MONTH(orders.createdDate) = @month
					AND YEAR(orders.createdDate) = @year
	
	)
	if @result is null begin set @result ='0' END
	SELECT @result
END

