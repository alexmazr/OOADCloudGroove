package com.cloudgroove.ContentService.util;

public class DeliveryServiceFactory
{
    public static DeliveryService create (String provider)
    {
        if (provider.equals("local")) return new LocalDelivery();
        else if (provider.equals("aws")) return new AWSDelivery();
        else return null;
    }
}
