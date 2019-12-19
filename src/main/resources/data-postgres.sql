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

-- inicijalni tipovi pregleda
insert into tip_pregleda (name) values ('стоматологија');
insert into tip_pregleda (name) values ('кардиологија');



-- za proveru KLINIKA-DOKTOR
insert into clinic (address, description, location, name, rating)
    values ('Topolska 18', 'Neki opis klinike', 'Novi Sad', 'Moja klinika', 8);
insert into clinic (address, description, location, name, rating)
    values ('Pionirska 10', 'Opis klinike', 'Futog', 'Nasa klinika', 4);
--d1
insert into users (enabled, last_password_reset_date, mail, password)
    values (true, '2017-10-01 21:58:58.508-07', 'doctor@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (field, first_name, last_name, role, id, clinic_id, tip_id)
    values ('Polje', 'Marko', 'Markovic', 'doctor', 1, 1, 1);
--d2
insert into users (enabled, last_password_reset_date, mail, password)
    values (true, '2017-10-01 21:58:58.508-07', 'doctor1@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (field, first_name, last_name, role, id, clinic_id, tip_id)
    values ('Polje', 'Marinko', 'Marinkovic', 'doctor', 2, 2, 1);
-- d3
insert into users (enabled, last_password_reset_date, mail, password)
    values (true, '2017-10-01 21:58:58.508-07', 'doctor3@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (field, first_name, last_name, role, id, clinic_id, tip_id)
    values ('Polje', 'Marinko', 'Marinkovic', 'doctor', 3, 1, 2);
-- d1
insert into clinic_doctors (clinic_id, doctors_id) values (1,1);
insert into user_authority (user_id, authority_id) values (2, 5);
-- d2
insert into clinic_doctors (clinic_id, doctors_id) values (2,2);
insert into user_authority (user_id, authority_id) values (3, 5);
--d3
insert into clinic_doctors (clinic_id, doctors_id) values (1,3);
insert into user_authority (user_id, authority_id) values (4, 5);

-- VACATION
insert into vacation (id, start_vacation, end_vacation, doctor_id) values (1, 1577404800000, 1577404800005, 2);
insert into vacation (id, start_vacation, end_vacation, doctor_id) values (2, 1577404800400, 1577404808000, 2);