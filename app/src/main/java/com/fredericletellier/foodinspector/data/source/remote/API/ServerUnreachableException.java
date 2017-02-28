package com.fredericletellier.foodinspector.data.source.remote.API;

/**
 * Created by Frédéric Letellier for AtReal Ouest on 28/02/17.
 * Company : http://www.opengst.fr/
 * Developer : fletellier@atreal.fr ; frederic.letellier.pro@gmail.com
 */

public class ServerUnreachableException extends RuntimeException {

    public ServerUnreachableException(){
        super("OpenFoodFacts database is unreachable");

    }
}
