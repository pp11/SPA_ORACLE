package com.example.SPA_APPS.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Data
public class IpAddressUtils {
    public static String getLocalIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress(); // Returns the local IP address
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Unknown IP"; // Return a fallback value in case of an error
        }
    }

    // Method to get the client IP address from HttpServletRequest
    public static String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For"); // Check for forwarded IP address
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr(); // Fallback to remote address
        }
        return clientIp;
    }
}
