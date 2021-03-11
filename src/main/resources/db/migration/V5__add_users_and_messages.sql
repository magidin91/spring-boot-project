/* пароль = user */
insert into usr (id, username, password, active)
    values (2, 'bridget', '$2a$08$by4Cp567QcBdwgGjiv6ED.hpWJnsFaxgbQ7M9rN4nsvivFGP3UwIq', true);

insert into user_role (user_id, roles)
    values (2, 'USER');

insert into usr (id, username, password, active)
    values (3, 'john', '$2a$08$by4Cp567QcBdwgGjiv6ED.hpWJnsFaxgbQ7M9rN4nsvivFGP3UwIq', true);

insert into user_role (user_id, roles)
    values (3, 'USER');

insert into usr (id, username, password, active)
    values (4, 'annie', '$2a$08$by4Cp567QcBdwgGjiv6ED.hpWJnsFaxgbQ7M9rN4nsvivFGP3UwIq', true);

insert into user_role (user_id, roles)
    values (4, 'USER');

insert into usr (id, username, password, active)
    values (5, 'nick', '$2a$08$by4Cp567QcBdwgGjiv6ED.hpWJnsFaxgbQ7M9rN4nsvivFGP3UwIq', true);

insert into user_role (user_id, roles)
    values (5, 'USER');

insert into message (id, tag, text, user_id)
values (1, 'reading email message',
'Gigi!!
Did you get my message?
Please call me.
Love, John.
P.S: Did you get the flowers?',
2),

(2, 'sitcom',
'Is he stupid?! (Writes email)
‘I told you last night, it’s finished.
Sorry. B.
(And don’t call me GIGI.)’', 2),
(3, 'on phone',
'But please don’t leave me.', 3),
(4, 'sitcom', 'Don’t cry.
Oh, goodbye.
Oh and happy birthday!
Aah, men!',2 ),
(5, 'sitcom', 'Come on Charley, come on.
Post, Bridget.
Charley and I have the post, haven’t we Charley.',4 ),
(6, 'sitcom', 'Give me the post, Charley. (Dog growls)
Give me the post, Charley.', 2),
(7, 'sitcom', 'Drop it, Charley.
Oh, good boy!
Oh, telephone bill, gas bill, electricity bill.
Oh.
Ah, what’s this?
Mmm, a parcel for you, Bridget.',4 ),
(8, 'sitcom', 'Oh, good.
What is it?
Oh, it’s from mother.', 2),
(9, 'sitcom', 'Oh, ‘Bridget darling, this arrived for you and I made you this. Love Mummy.’
Oh, ho-ho, oh very you, Bridget!', 4),
(10, 'sitcom', 'Mother!
Ooh, this is from Argentina.', 2),
(11, 'sitcom', 'Argentina. Who from? What does it say?', 4),
(12, 'sitcom', '(Erm), it says ‘Hello, do you remember me?’
No.
‘Seven years ago we was pen pals.’
‘We was’ — we were pen pals.
Oh yeah, now I remember, it’s Hector!', 2),
(13, 'sitcom', 'Who’s Hector?', 4),
     (14, 'sitcom', 'He was my pen pal seven years ago.', 2),
     (15, 'sitcom', 'Oh.', 4),
     (16, 'sitcom', '‘I speak English good now.’ I speak English good.
I speak English well now – ‘and I am coming to England.’', 2),
     (17, 'other', 'Oh, Latin Americans!', 4),
     (18, 'sitcom', 'I would like to sleep with you. Do you have a bed for me?’
Oh, he wants to stay here! (Oh). Ah, ha, ha.', 2),
     (19, 'sitcom', 'But (Erm) what about the rules?', 4),
     (20, 'sitcom', 'Ooh, a Latin American here, ooh, like Ricky Martin!
Tall, handsome, rich!
So (erm) when is he coming?', 4),
     (21, 'sitcom', 'Oh (erm) let me see.
It says ‘November 5th.’', 2),
     (22, 'sitcom', 'Oh, November 5th.
But that’s …', 4),
     (23, 'sitcom', '… Today!', 2),
     (24, 'Bridget and Annie', 'Aaaah!!!', 4),
     (25, 'sitcom', 'Oh, oh it’s you Nick!', 2),
     (26, 'sitcom', 'Hi, babes!', 5),
     (27, 'sitcom', 'Hello Nick.
How are you?', 2),
     (28, 'sitcom', 'Gr-eat.
Nice muscles.
Here’s your milk.',5),
     (29, 'sitcom', 'Our milk.
You mean our milk you borrowed three weeks ago.',2 ),
     (30, 'sitcom', 'Oh, thanks Nick.', 4);
