package rs.zis.app.zis.constants;

import java.util.Date;

public class UserConstants {
    public static final int DB_PATIENT_COUNT = 2;
    public static final int DB_ADMIN_COUNT = 1;
    public static final int DB_DOCTOR_COUNT = 3;
    public static final int DB_CLINIC_COUNT = 2;
    public static final int DB_DOCTOR_TERMS_COUNT = 12;

    // patient
    public static final Long DB_PATIENT_LBO = 12345678912L;
    public static final Long DB_PATIENT_ID = 2L;
    public static final String DB_PATIENT_MAIL = "patient@gmail.com";
    public static final String DB_PATIENT2_MAIL = "patient2@gmail.com";
    public static final String DB_PATIENT_FIRST_NAME = "Петар";
    public static final String DB_PATIENT_LAST_NAME = "Петровић";
    public static final String DB_NEKI_MAIL = "nekimail@gmail.com";

    // doctor terms
    public static final Long DB_DOCTOR_TERMS_ID_REMOVE = 7L;
    public static final Long DB_DOCTOR_TERMS_ID_FIND_ONE = 1L;
    public static final Long DB_DOCTOR_TERMS_DOCTOR_FIND_ONE = 3L;
    public static final Long DB_DOCTOR_TERMS_DATE = 1578441600000L;         // 8. januar
    public static final Long DB_DOCTOR_TERMS_FREE_DATE = 1578441500000L;
    public static final int DB_DOCTOR_TERMS_DATE_COUNT = 11;
    public static final int DB_DOCTOR_TERMS_DOCTOR_COUNT = 2;
    public static final String DB_PATIENT_NONEXISTENT_MAIL = "nonex@gmail.com";
    public static final Long DB_DOCTOR_TERMS_DOCTOR_ID = 4L;
    public static final int DB_DOCTOR_TERMS_NUMBER_OF_TERMS = 9;
    public static final String DB_DOCTOR_TERMS_START_TERM = "12:00";
    public static final String DB_DOCTOR_TERMS_END_TERM = "12:30";
    public static final Long DB_DOCTOR_TERMS_PREDEFINED_ID = 2L;

    // doctor
    public static final String DB_DOCTOR_FIRST_NAME = "Марко";
    public static final String DB_DOCTOR_LAST_NAME = "Марковић";

    // clinic
    public static final Long DB_CLINIC_ID = 1L;


    //room
    public static final String DB_ROOM_NAME1 = "Сала 1";
    public static final String DB_ROOM_NAME2 = "Сала 2";
    public static final String DB_ROOM_NAME3 = "Сала 3";
    public static final String DB_ROOM_NAME4 = "Сала 4";
    public static final String DB_ROOM_NUMBER1 = "1";
    public static final String DB_ROOM_NUMBER2 = "2";
    public static final String DB_ROOM_NUMBER3 = "3";
    public static final String DB_ROOM_NUMBER4 = "4";

    //date
    public static final long DB_DATE = 1583280000000L;
}
