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

-- MEDICAL RECORD
insert into medical_record (id, allergy, blood_group, dioptre_left_eye, dioptre_right_eye, height, weight, patient_id)
    values (2,'polen','A+',0.5,1,160,50,2);

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

insert into tip_pregleda (active, name) values (true,'Стоматологија');
insert into tip_pregleda (active, name) values (true,'Кардиологија');

-- za proveru KLINIKA-DOKTOR
insert into clinic (address, description, location, name, sum_ratings, number_ratings, version)
    values ('Тополска 18', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        'Нови Сад', 'Моја клиника', 49, 5, 0);
insert into clinic (address, description, location, name, sum_ratings, number_ratings, version)
    values ('Пионирска 10', 'Aliquet eget sit amet tellus cras adipiscing. Senectus et netus et malesuada. Morbi tristique senectus et netus et. Sed turpis tincidunt id aliquet risus. Ultrices sagittis orci a scelerisque purus. Elementum facilisis.',
    'Футог', 'Наша клиника', 87, 11, 0);
--d1
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, id, clinic_id, tip_id, work_shift, sum_ratings, number_ratings)
    values (0, 4200.00, 'Марко', 'Марковић', 'doctor', 3, 1, 1, 1, 87, 9);
--d2
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor1@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, id, clinic_id, tip_id, work_shift, sum_ratings, number_ratings)
    values (5, 5500.00, 'Маринко', 'Маринковић', 'doctor', 4, 2, 1, 2, 75, 11);
-- d3
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'doctor3@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into doctor (discount, price, first_name, last_name, role, id, clinic_id, tip_id, work_shift, sum_ratings, number_ratings)
    values (10, 6400.00, 'Бојан', 'Бојанић', 'doctor', 5, 1, 2, 1, 42, 8);
-- d1
insert into clinic_doctors (clinic_id, doctors_id) values (1,3);
insert into user_authority (user_id, authority_id) values (3, 5);
-- d2
insert into clinic_doctors (clinic_id, doctors_id) values (2,4);
insert into user_authority (user_id, authority_id) values (4, 5);
--d3
insert into clinic_doctors (clinic_id, doctors_id) values (1,5);
insert into user_authority (user_id, authority_id) values (5, 5);



-- CLINIC ADMINISTRATOR
insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'cadmin@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into clinic_administrator (address, city, country, first_name, last_name, role, telephone, id, clinic_id)
    values ('Цара Лазара 12', 'Нови Сад', 'Србија', 'Предраг', 'Шпагић', 'cadmin', '879-456', 6, 1);

insert into users (enabled, active, first_login, last_password_reset_date, mail, password)
    values (true, true, true, '2017-10-01 21:58:58.508-07', 'cadmin1@gmail.com','$2a$04$SwzgBrIJZhfnzOw7KFcdzOTiY6EFVwIpG7fkF/D1w26G1.fWsi.aK');
insert into clinic_administrator (address, city, country, first_name, last_name, role, telephone, id, clinic_id)
    values ('Царице Милице', 'Београд', 'Србија', 'Душко', 'Кртола', 'cadmin', '895-456', 7, 2);
--ca1
insert into user_authority (user_id, authority_id) values (6, 3);
--ca2
insert into user_authority (user_id, authority_id) values (7, 3);

-- VACATION
--insert into vacation (id, active, start_vacation, end_vacation, doctor_id) values (1, true, 1579824000000, 1580428800000, 3);     -- 24.01. - 31.01.
--insert into vacation (id, active, start_vacation, end_vacation, doctor_id) values (2, true, 1580428800000, 1581033600000, 5);     -- 31.01. - 07.02.

-- ROOM
insert into room(active, name, number, clinic_id) values (true, 'Сала 1', 1, 1);
insert into room(active, name, number, clinic_id) values (true, 'Сала 2', 2, 1);
insert into room(active, name, number, clinic_id) values (true, 'Сала 3', 3, 1);
insert into room(active, name, number, clinic_id) values (true, 'Сала 4', 4, 1);
insert into room(active, name, number, clinic_id) values (true, 'Роом 1', 1, 2);
insert into room(active, name, number, clinic_id) values (true, 'Роом 2', 2, 2);
insert into room(active, name, number, clinic_id) values (true, 'Роом 3', 3, 2);

-- CLINIC_ROOMS
insert into clinic_rooms(clinic_id, rooms_id) values (1, 1);
insert into clinic_rooms(clinic_id, rooms_id) values (1, 2);
insert into clinic_rooms(clinic_id, rooms_id) values (1, 3);
insert into clinic_rooms(clinic_id, rooms_id) values (1, 4);
insert into clinic_rooms(clinic_id, rooms_id) values (2, 5);
insert into clinic_rooms(clinic_id, rooms_id) values (2, 6);
insert into clinic_rooms(clinic_id, rooms_id) values (2, 7);

-- DOCTOR_TERM
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
            values (true, 1578441600000, true, 3, 2, true, 0, false, 1, 1, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, true, 3, 2, true, 0, false, 3, 2, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, true, 3, 2, true, 0, false, 1, 3, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, true, 3, 2, true, 0, false, 2, 4, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 3, 2, true, 0, false, 2, 5, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 3, 2, true, 0, false, 2, 6, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 3, 2, true, 0, false, 2, 7, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 157844160001, false, 3, 2, true, 0, false, 4, 8, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 3, 2, true, 0, false, 2, 9, 4200.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 3, 2, true, 0, false, 3, 10, 4500.00, 0, false, false); -- 8. januar
insert into doctor_terms (active, date, examination, doctor_id, patient_id, processed_by_cadmin, version, predefined, room_id, term_definition_id,
            price, discount, rate_clinic, rate_doctor)
             values (true, 1578441600000, false, 4, 2, true, 0, false, 6, 11, 4700.00, 0, false, false);

insert into room_doctor_terms (room_id, doctor_terms_id) values (1, 1);
insert into room_doctor_terms (room_id, doctor_terms_id) values (3, 2);
insert into room_doctor_terms (room_id, doctor_terms_id) values (1, 3);
insert into room_doctor_terms (room_id, doctor_terms_id) values (2, 4);
insert into room_doctor_terms (room_id, doctor_terms_id) values (2, 5);
insert into room_doctor_terms (room_id, doctor_terms_id) values (2, 6);
insert into room_doctor_terms (room_id, doctor_terms_id) values (2, 7);
insert into room_doctor_terms (room_id, doctor_terms_id) values (4, 8);
insert into room_doctor_terms (room_id, doctor_terms_id) values (2, 9);
insert into room_doctor_terms (room_id, doctor_terms_id) values (3, 10);
insert into room_doctor_terms (room_id, doctor_terms_id) values (6, 11);


--DIAGNOSIS AND CURES
insert into diagnosis (id, cure_name, cure_password, diagnosis_name, diagnosis_password)
   values(2,'AMOKSIKLAV(500mg+125mg)','10215','Zapaljenje sinusa','J01');
insert into diagnosis (id, cure_name, cure_password, diagnosis_name, diagnosis_password)
   values(3,'AMOKSIKLAV(500mg)','10216','Zapaljenje sinusa','J01');
insert into diagnosis (id, cure_name, cure_password, diagnosis_name, diagnosis_password)
   values(4,'AMOKSIKLAV(500mg+125mg)','10215','Zapaljenje krajnika','J07');
insert into diagnosis (id, cure_name, cure_password, diagnosis_name, diagnosis_password)
   values(5,'BRUFEN(400mg)','2035','Temperatura','K168');
insert into diagnosis (id, cure_name, cure_password, diagnosis_name, diagnosis_password)
   values(6,'BRUFEN(800mg)','2036','Temperatura','K168');

