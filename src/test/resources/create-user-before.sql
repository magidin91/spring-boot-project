delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'admin', '$2a$08$A2FL7u2ueipKg5RiL4RedeBj2tObGb1595PGpoRv06IaKAq9NX4.S', true),
(2, 'user', '$2a$08$by4Cp567QcBdwgGjiv6ED.hpWJnsFaxgbQ7M9rN4nsvivFGP3UwIq', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER');