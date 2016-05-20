package com.zack.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class NoteGreenDao {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.zack.bean");
        schema.setDefaultJavaPackageDao("com.zack.dao");
        initUserBean(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java-green");
    }

    private static void initUserBean(Schema schema) {
        Entity Note = schema.addEntity("Notes");
        Note.addLongProperty("id").primaryKey().autoincrement();
        Note.addStringProperty("title");
        Note.addStringProperty("content");
        Note.addStringProperty("createTime");
        Note.addStringProperty("lastModifyTime");
        Note.addStringProperty("isDeleted");
    }
}
