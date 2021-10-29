drop database if exists cattery;
create database cattery;
use cattery;

create table users(
id int auto_increment,
login varchar(200) not null,
password_hash varchar(200) not null,
language varchar(20) not null,
first_and_last_name varchar(300),
is_enabled boolean,
registration_datetime datetime,
delete_datetime datetime,
primary key (id)
);

create table user_authorities (
    id int auto_increment,
    user_id int not null,
	authority varchar(50) not null,
	primary key (id),
    foreign key (user_id) references users(id)
);
create unique index ix_auth on user_authorities (user_id, authority);

create table contact_form (
	id int auto_increment,
    name varchar(100) not null,
    e_mail varchar(100),
    phone_number varchar(100),
    message_topic varchar(1000) not null,
    content varchar(10000) not null,
    regulation_accepted boolean not null,
    is_served boolean not null,
    contact_datetime datetime not null,
    delete_datetime datetime,
	primary key (id)
);

create table species_details (
id int auto_increment,
details varchar(100),
primary key(id)
);

create table species (
id int auto_increment,
name varchar(100) not null unique,
species_detail_id int,
primary key(id),
foreign key(species_detail_id) references species_details(id)
);

create table breeds (
id int auto_increment,
name varchar(100) not null unique,
short_name varchar(100) not null unique,
species_id int not null,
primary key(id),
foreign key (species_id) references species(id)
);

create table dictionaries (
id int auto_increment,
domain varchar(100) not null,
code varchar(20) not null,
value varchar(100) not null,
lang varchar(20) not null,
primary key (id));

create table images (
id int auto_increment,
image_file_name varchar(1000) not null,
primary key(id)
);

create table animals (
id int auto_increment,
breed_id int not null,
name varchar(100),
lineage_name varchar(100),
gender_code varchar(20) not null,
birth_date date,
color varchar(100),
chip_number varchar(100),
lineage_number varchar(100),
weight_kg decimal(6,3),
cattery_status_code varchar(20),
mother_animal_id int,
father_animal_id int,
price decimal(6,2),
sale_status_code varchar(20),
image_id int,
note varchar(1000),
website_description varchar(1000),
website_visibility_status varchar(10),
delete_datetime datetime,
primary key(id),
foreign key (image_id) references images(id)
);

create table births (
id int auto_increment,
birth_date date not null,
amount int not null,
name varchar(100),
note varchar(1000),
mother_id int,
father_id int,
main_image_id int,
website_description varchar (1000),
website_detail_description varchar (1000),
website_visibility_status varchar (10),
delete_datetime datetime,
foreign key (mother_id) references animals(id),
foreign key (father_id) references animals(id),
foreign key (main_image_id) references images(id),
primary key (id));

ALTER TABLE animals ADD birth_id int;
ALTER TABLE animals ADD FOREIGN KEY (birth_id) REFERENCES births(id);


create table images_to_births (
id int auto_increment,
image_id int not null,
birth_id int not null,
primary key(id),
foreign key (image_id) references images(id),
foreign key (birth_id) references births(id)
);

create table vaccinations_and_medicines (
id int auto_increment,
name varchar (100) not null,
type_code int not null,
purpose varchar(1000),
note varchar (1000),
primary key(id)
);

create table diseases (
id int auto_increment,
name varchar(100) not null,
note varchar (1000),
primary key(id)
);

create table animal_diseases (
id int auto_increment,
disease_id int not null,
animal_id int not null,
start_date datetime,
finish_date datetime,
note varchar (1000),
primary key(id)
);

create table animal_medicines_therapy (
id int auto_increment,
animal_disease_id int not null,
medicine_id int not null,
start_date datetime,
finish_date datetime,
note varchar (1000),
primary key(id),
foreign key (animal_disease_id) references animal_diseases(id),
foreign key (medicine_id) references vaccinations_and_medicines(id)
);

create table weights (
id int auto_increment,
animal_id int not null,
weight_kg decimal(6,3),
date datetime,
primary key(id)
);

create table customers (
id int auto_increment,
first_name varchar(100) not null,
last_name varchar(100) not null,
phone_number varchar(100),
postal_code varchar(100),
country varchar(100),
city varchar(100),
street varchar(100),
building_no varchar(100),
flat_no varchar(100),
e_mail varchar(100),
personal_id_number varchar(100),
note varchar (1000),
delete_datetime datetime,
primary key(id)
);

create table transactions_categories(
id int auto_increment,
name varchar(100) not null unique,
type_code varchar(10) not null,
primary key(id)
);

create table transactions_subcategories(
id int auto_increment,
transaction_category_id int,
name varchar(100) not null unique,
primary key(id),
foreign key (transaction_category_id)references transactions_categories(id)
);

create table transactions (
id int auto_increment,
customer_id int,
animal_id int,
price decimal(10,2),
reservation_price decimal(10,2),
final_date date,
reservation_date date,
status_code varchar(20),
transaction_category_id int,
transaction_subcategory_id int,
note varchar (1000),
primary key(id),
foreign key (transaction_category_id)references transactions_categories(id),
foreign key (transaction_subcategory_id)references transactions_subcategories(id)
);