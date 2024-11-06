package com.project1.HotelManagement.Security;

public class Endpoint {
    public static final String front_end_host = "http://localhost:3000";
    public static final String[] PRIVATE_GET_ENDPOINTS = {

    };
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/room",
            "/room/**",
            "/roomtype",
            "/roomtype/**",
            "/image",
            "/image/**",
            "/customer",
            "/customer/**",
            "/useraccount",
            "/useraccount/**",
    };
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/userAccount/register",
            "/api/userAccount"
    };
    public static final String[] PRIVATE_POST_ENDPOINTS = {
            "/api/roomType/add",
            "/api/room/addRoom",
            "/api/roomType/delete",

    };
    public static final String[] PRIVATE_DELETE_ENDPOINTS = {
            "/api/room/deleteRoom/{roomId}",
    };
    public static final String[] PRIVATE_PUT_ENDPOINTS = {
            "/api/roomType/update",
            "/api/room/updateRoom",
    };
}
