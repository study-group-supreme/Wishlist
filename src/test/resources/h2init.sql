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
                      description varchar(255)
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
                               url varchar(500),
                               price DECIMAL(10,2),

                               primary key (wishlist_id, item_id),
                               foreign key (wishlist_id) references wishlist (id) ON DELETE CASCADE,
                               foreign key (item_id) references item (id) ON DELETE CASCADE
);


create table saved_wishlist (
                                wishlist_id int not null,
                                member_id int not null,
                                primary key (wishlist_id, member_id),
                                foreign key (wishlist_id) references wishlist (id) ON DELETE CASCADE,
                                foreign key (member_id) references member (id) ON DELETE CASCADE
);

insert into member (username, password, name, email)
values  ('Shaz', '1234', 'August Skipper', 'funkskipper@hotmail.com'),
        ('Goegl', '5678', 'Andreas Jensen', 'goegl12@gmail.com'),
        ('Ellidena', 'pw', 'Daniella Norgren', 'ellidena@gmail.com'),
        ('Sv4nh0lm', '58504942', 'Mads Svanholm', 'svanholm200@gmail.com');

insert into item (title, description)
values ('Life-size Darth Vader', 'A fully sized replica statue of Dark Vader, of course'),
('Spider-Man figure', 'Action figure');

insert into wishlist (member_id, title, description, is_public)
values  (1, 'August List of Hopes and Dreams', 'My deepest, darkest desires', true),
        (2, 'Andreas Filthy Dirty Wishes', 'If it is covered in grease, I want it', false);

insert into wishlist_item (wishlist_id, item_id, note, url, price)
values (1,1, 'To get chicks, of course','https://www.superheltenlegetoej.dk/da/star-wars-life-size-statue-darth-vader-233-cm', 105329.95 ),
(1, 2, 'Test','https://www.bog-ide.dk/produkt/5978790/spider-man-titanpEALw_wcB', 129 );