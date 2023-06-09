CREATE TABLE Person(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100) NOT NULL,
    address varchar(100) NOT NULL,
    phone varchar(12) NOT NULL UNIQUE,
    email varchar(100),
    password varchar NOT NULL,
    role varchar(100) NOT NULL DEFAULT 'ROLE_USER'
);
CREATE TABLE Category(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE
);
CREATE TABLE Item(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_name varchar(100) NOT NULL,
    price int NOT NULL,
    item_quantity int,
    category_id int REFERENCES Category(id) ON DELETE CASCADE
);
CREATE TABLE Ordered_items(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    person_id int REFERENCES Person(id) ON DELETE CASCADE,
    status varchar(50),
    total_price int
);
CREATE TABLE Item_in_order(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_id int REFERENCES Item(id) ON DELETE SET NULL,
    order_id int REFERENCES Ordered_items(id) ON DELETE SET NULL,
    quantity int
);
CREATE TABLE Delivery(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    order_id int UNIQUE REFERENCES Ordered_items(id) ON DELETE CASCADE ,
    start_time timestamp,
    finish_time timestamp
);
INSERT INTO Person (name, address, phone, email, password, role) Values
    ('user', 'moscow', '123456789123', 'sfa@dsd.er', '$2a$12$2TxK2NzrKwt8VgK77N0dEuukCYqu3hv/FqKXoBAdtSpkSTdgqSev.', 'ROLE_USER');
INSERT INTO Person (name, address, phone, email, password, role) Values
    ('admin', 'moscow', '123455589123', 'sfa@dsd.er', '$2a$12$2TxK2NzrKwt8VgK77N0dEuukCYqu3hv/FqKXoBAdtSpkSTdgqSev.', 'ROLE_ADMIN');
INSERT INTO Person (name, address, phone, email, password, role) Values
    ('courier', 'moscow', '555456789123', 'sfa@dsd.er', '$2a$12$2TxK2NzrKwt8VgK77N0dEuukCYqu3hv/FqKXoBAdtSpkSTdgqSev.', 'ROLE_COURIER');