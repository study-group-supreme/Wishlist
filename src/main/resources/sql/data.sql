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