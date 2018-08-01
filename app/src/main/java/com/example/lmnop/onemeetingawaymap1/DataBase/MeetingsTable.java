package com.example.lmnop.onemeetingawaymap1.DataBase;
public class MeetingsTable {

        public static final String TABLE_ITEMS = "meetings";
        public static final String COLUMN_ID = "idNumber";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_STARTTIME = "startTime";
        public static final String COLUMN_ENDTIME = "endTime";
        public static final String COLUMN_OC = "oc";
        public static final String COLUMN_MEETINGNAME = "meetingName";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_CODES = "codes";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LNG = "lng";

        public static final String[] ALL_COLUMNS =
                {COLUMN_ID, COLUMN_DAY, COLUMN_STARTTIME, COLUMN_ENDTIME, COLUMN_OC,
                COLUMN_MEETINGNAME, COLUMN_LOCATION, COLUMN_ADDRESS,
                        COLUMN_CODES, COLUMN_LAT, COLUMN_LNG};

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_ITEMS + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_DAY + " TEXT," +
                        COLUMN_STARTTIME + " TEXT," +
                        COLUMN_ENDTIME + " TEXT," +
                        COLUMN_OC + " TEXT," +
                        COLUMN_MEETINGNAME + " TEXT," +
                        COLUMN_LOCATION + " TEXT," +
                        COLUMN_ADDRESS + " TEXT," +
                        COLUMN_CODES + " TEXT," +
                        COLUMN_LAT + " TEXT," +
                        COLUMN_LNG + " TEXT" + ");";
        public static final String SQL_DELETE =
                "DROP TABLE " + TABLE_ITEMS;

}

