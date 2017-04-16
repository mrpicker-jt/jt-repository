package com.gproject.android.db.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.gproject.android.db");

        Entity userEntity = schema.addEntity("UserEntity");
        userEntity.addIdProperty().notNull();
        userEntity.addStringProperty("token");


        new DaoGenerator().generateAll(schema, "./app/src/main/java/");
    }

}

