insert into authority (name) values ('ROLE_CCADMIN');
insert into authority (name) values ('ROLE_PATIENT');
insert into authority (name) values ('ROLE_CADMIN');
insert into authority (name) values ('ROLE_NURSE');
insert into authority (name) values ('ROLE_DOCTOR');

insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'admin@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into clinic_centre_admin (first_name, last_name, predefined, role, id)
    values ('Aдмин', 'Aдмин', true, 'ccadmin', 1);


insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'patient@gmail.com','$2a$10$dHUB7Rc9.w6lCB1QRcNIJe2ZPSnbRORDQPtVszxtsSSAN965P8MRm'); --password: 12345678
insert into patient (address, city, country, first_name, last_name, lbo, role, telephone, id)
    values ('Моја адреса 25', 'Нови Сад', 'Србија', 'Петар', 'Петровић', 12345678912, 'patient', 456789, 2);

insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 2);

-- TERM DEFINITION
insert into term_definition (end_term, start_term, work_shift) values ('08:30', '08:00', 1);
insert into term_definition (end_term, start_term, work_shift) values ('09:00', '08:30', 1);
insert into term_definition (end_term, start_term, work_shift) values ('09:30', '09:00', 1);
--insert into term_definition (end_term, start_term, work_shift) values ('10:00', '09:30', 1);          PAUZA
insert into term_definition (end_term, start_term, work_shift) values ('10:30', '10:00', 1);
insert into term_definition (end_term, start_term, work_shift) values ('11:00', '10:30', 1);
insert into term_definition (end_term, start_term, work_shift) values ('11:30', '11:00', 1);
insert into term_definition (end_term, start_term, work_shift) values ('12:00', '11:30', 1);
insert into term_definition (end_term, start_term, work_shift) values ('12:30', '12:00', 1);
insert into term_definition (end_term, start_term, work_shift) values ('13:00', '12:30', 1);
insert into term_definition (end_term, start_term, work_shift) values ('13:30', '13:00', 1);
insert into term_definition (end_term, start_term, work_shift) values ('14:00', '13:30', 2);
insert into term_definition (end_term, start_term, work_shift) values ('14:30', '14:00', 2);
insert into term_definition (end_term, start_term, work_shift) values ('15:00', '14:30', 2);
insert into term_definition (end_term, start_term, work_shift) values ('15:30', '15:00', 2);
--insert into term_definition (end_term, start_term, work_shift) values ('16:00', '15:30', 2);          PAUZA
insert into term_definition (end_term, start_term, work_shift) values ('16:30', '16:00', 2);
insert into term_definition (end_term, start_term, work_shift) values ('17:00', '16:30', 2);
insert into term_definition (end_term, start_term, work_shift) values ('17:30', '17:00', 2);
insert into term_definition (end_term, start_term, work_shift) values ('18:00', '17:30', 2);
insert into term_definition (end_term, start_term, work_shift) values ('18:30', '18:00', 2);
insert into term_definition (end_term, start_term, work_shift) values ('19:00', '18:30', 2);
------------------

-- inicijalni tipovi pregleda
insert into tip_pregleda (name) values ('Стоматологија');
insert into tip_pregleda (name) values ('Кардиологија');

-- za proveru KLINIKA-DOKTOR
insert into clinic (address, description, location, name, rating)
    values ('Тополска 18', 'Неки опис клинике', 'Нови Сад', 'Моја клиника', 8);
insert into clinic (address, description, location, name, rating)
    values ('Пионирска 10', 'Опис клинике', 'Футог', 'Наша клиника', 4);
--d1
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, rating, id, clinic_id, tip_id, work_shift)
    values (0, 4200.00, 'Mарко', 'Марковић', 'doctor', 7.4, 3, 1, 1, 1);
--d2
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor1@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, rating, id, clinic_id, tip_id, work_shift)
    values (5, 5500.00, 'Mаринко', 'Mаринковић', 'doctor', 4.5, 4, 2, 1, 2);
-- d3
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor3@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, rating, id, clinic_id, tip_id, work_shift)
    values (10, 6400.00, 'Бојан', 'Бојанић', 'doctor', 8.2, 5, 1, 2, 1);
-- d1
insert into clinic_doctors (clinic_id, doctors_id) values (1,3);
insert into user_authority (user_id, authority_id) values (3, 5);
-- d2
insert into clinic_doctors (clinic_id, doctors_id) values (2,4);
insert into user_authority (user_id, authority_id) values (4, 5);
--d3
insert into clinic_doctors (clinic_id, doctors_id) values (1,5);
insert into user_authority (user_id, authority_id) values (5, 5);

-- VACATION
insert into vacation (id, start_vacation, end_vacation, doctor_id) values (1, 1577404800000, 1577404800005, 3);
insert into vacation (id, start_vacation, end_vacation, doctor_id) values (2, 1577404800400, 1577404808000, 4);

-- DOCTOR_TERM
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 1); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 2); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 3); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 4); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 5); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 6); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 7); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 8); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 9); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
    values (true, 1578441600000, 'Преглед успешно завршен. Пацијент у добром стању. Контрола по потреби', 3, 2, 10); -- 8. januar
insert into doctor_terms (active, date, report, doctor_id, patient_id, term_definition_id)
values (true, 1578441600000, 'Пацијенту се нагло погоршало стање. Хитна операција.', 4, 2, 11);

