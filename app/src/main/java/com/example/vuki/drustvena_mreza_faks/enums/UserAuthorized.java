package com.example.vuki.drustvena_mreza_faks.enums;

/**
 * Created by Vuki on 4.11.2015..
 */
public enum UserAuthorized {

    AUTHORIZED(1) , UNAUTHORIZED(0);

    private int key;
    UserAuthorized(int key){
        this.key=key;
    }
    public int getKey(){
        return key;
    }
    public static UserAuthorized fromKey(int key){
        for(UserAuthorized userAuthorized:values()){
            if(key==userAuthorized.key){
                return userAuthorized;
            }
        }
        return UNAUTHORIZED;
    }
}
