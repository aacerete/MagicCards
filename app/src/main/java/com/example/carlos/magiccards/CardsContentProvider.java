package com.example.carlos.magiccards;

import nl.littlerobots.cupboard.tools.provider.CupboardContentProvider;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class CardsContentProvider extends CupboardContentProvider {

    //The content provider authority is used for building Uri's for the provider
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    static {
        cupboard().register(Card.class);
    }

    public CardsContentProvider() {
        super(AUTHORITY , 1);
    }
}
