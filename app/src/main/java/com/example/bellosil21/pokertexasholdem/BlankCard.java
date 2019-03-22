package com.example.bellosil21.myapplication;

/**
 * To represent a blank or hidden card.
 *
 * @author Patrick Bellosillo
 * @author Jordan Ho
 * @author Kevin Hoser
 * @author Gabe Marcial
 */
public class BlankCard implements CardSlot {

    /**
     * BlankCard constructor
     *
     * With the exception of the toString, there are no instance variables or
     * methods in this class since a BlankCard contains no details of a Card.
     */
    public BlankCard() {
    }

    @Override
    public String toString() {
        return "Blank Card";
    }
}
