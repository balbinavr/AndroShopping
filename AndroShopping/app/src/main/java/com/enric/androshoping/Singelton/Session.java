package com.enric.androshoping.Singelton;

import com.enric.androshoping.Objects.Usuari;

/**
 * Created by home on 26/05/15.
 */
public class Session {
    public static Usuari usuari;
    
    
    public static void initUser(Usuari user){
        usuari = user;
    }
    
}
