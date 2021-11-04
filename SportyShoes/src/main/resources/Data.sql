--Drop tables and Sequesce
drop table if exists category CASCADE;
drop table if exists order_item CASCADE;
drop table if exists prod_order CASCADE;
drop table if exists product CASCADE;
drop table if exists user CASCADE;
drop sequence if exists hibernate_sequence;

--Create sequence
create sequence hibernate_sequence start with 20 increment by 1;

--Create tables
create table category (
       id integer not null,
        name varchar(255),
        primary key (id)
    );
	
create table order_item (
       id integer not null,
        order_item_total float,
        price float,
        quantity integer,
        status varchar(255),
        order_id integer,
        product_id integer,
        primary key (id)
    );
	
 create table prod_order (
       id integer not null,
        order_date date,
        order_number varchar(255),
        order_total float,
        status varchar(255),
        user_id integer,
        primary key (id)
    );
	
  create table product (
       id integer not null,
        deleted boolean not null,
        price float not null check (price>=1),
        title varchar(255),
        category_id integer,
        primary key (id)
    );

create table prod_user (
       id integer not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    );
	
--Define constraints

 alter table prod_order 
       add constraint UK_o397oef24qu43nhl9v4rp9w82 unique (order_number);
       
 alter table category 
       add constraint UK_CAT_NAME unique (name);

alter table order_item 
       add constraint FKmka4fcbxdyvh3ucsa8m0sme5b 
       foreign key (order_id) 
       references prod_order;
	   
alter table order_item 
       add constraint FK551losx9j75ss5d6bfsqvijna 
       foreign key (product_id) 
       references product;
	   
alter table product 
       add constraint FK1mtsbur82frn64de7balymq9s 
       foreign key (category_id) 
       references category;
	   
 alter table prod_order 
       add constraint FKbdhd0fvhn48u2qd8dtp6dpaa6 
       foreign key (user_id) 
       references prod_user;
	   
--Data insertion

insert into category(id,name) values(1,'Cricket');
insert into category(id,name) values(2,'Football');
insert into category(id,name) values(3,'Hockey');

insert into product(id,price,title,category_id,deleted) values(4,200,'CSK Shoes',1,false);
insert into product(id,price,title,category_id,deleted) values(5,150,'MI Shoes',1,false);
insert into product(id,price,title,category_id,deleted) values(6,100,'RCB Shoes',1,false);

insert into product(id,price,title,category_id,deleted) values(7,500,'MANUTD Shoes',2,false);
insert into product(id,price,title,category_id,deleted) values(8,50,'Chelsea Shoes',2,false);
insert into product(id,price,title,category_id,deleted) values(9,10,'Arsenal Shoes',2,false);

insert into product(id,price,title,category_id,deleted) values(10,2000,'India Shoes',3,false);
insert into product(id,price,title,category_id,deleted) values(11,450,'Japan Shoes',3,false);
insert into product(id,price,title,category_id,deleted) values(12,100,'England Shoes',3,false);
