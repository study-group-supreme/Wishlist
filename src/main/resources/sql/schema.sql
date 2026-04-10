drop table if exists saved_wishlist;
drop table if exists wishlist_item;
drop table if exists member;
drop table if exists item;
drop table if exists wishlist;

create table wishlist (
                          id int auto_increment primary key,
                          member_id int not null,
                          title varchar(100) not null,
                          description varchar(255),
                          is_public boolean
);

create table item (
                      id int auto_increment primary key,
                      title varchar(100) not null,
                      description varchar(255),
                      url varchar(255),
                      price long
);

create table member (
                        id int auto_increment primary key,
                        username varchar(100) unique not null,
                        password varchar(100) not null,
                        name varchar(200) not null,
                        email varchar(200) unique not null
);

create table wishlist_item (
                               wishlist_id int not null,
                               item_id int not null,
                               note varchar(255),
                               primary key (wishlist_id, item_id),
                               foreign key (wishlist_id) references wishlist (id),
                               foreign key (item_id) references item (id)
);

create table saved_wishlist (
                                wishlist_id int not null,
                                member_id int not null,
                                primary key (wishlist_id, member_id),
                                foreign key (wishlist_id) references wishlist (id),
                                foreign key (member_id) references member (id)
);