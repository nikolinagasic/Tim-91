insert into authority (name) values ('ROLE_CCADMIN');
insert into authority (name) values ('ROLE_PATIENT');
insert into authority (name) values ('ROLE_CADMIN');
insert into authority (name) values ('ROLE_NURSE');
insert into authority (name) values ('ROLE_DOCTOR');

insert into users (enabled, last_password_reset_date, mail, password)
    values (true, '2017-10-01 21:58:58.508-07', 'admin@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into clinic_centre_admin (first_name, last_name, predefined, role, id)
    values ('Admin', 'Admin', true, 'ccadmin', 1);

insert into user_authority (user_id, authority_id) values (1, 1);