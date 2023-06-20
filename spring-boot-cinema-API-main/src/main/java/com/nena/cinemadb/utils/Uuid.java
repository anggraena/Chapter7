package com.nena.cinemadb.utils;

import java.util.UUID;

public class Uuid {
    public static String uuidGenerator(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,5);
    }
}
