package com.mercadolibre.apps.sim.data.bo.core

public enum SupplierAddressType {

    //Denotes address for main office
    MAIN_OFFICE(0),

    //Denotes remit to address (address to which checks are sent, etc)
    REMIT_TO(1),

    //Ship from address (used for tax information at buyer side)
    SHIP_FROM(2);


    final Integer id

    SupplierAddressType(Integer id) {
		if (id >= 0 && id <= 2)
        	this.id = id
    }

    public static SupplierAddressType getType(String type){
        type = type.toUpperCase()
        return SupplierAddressType."$type"
    }
}