# ZackNote

new ：

    public static  void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.zack.bean");
        schema.setDefaultJavaPackageDao("com.zack.dao");
        initUserBean(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java-green");
    }


    private static void initUserBean(Schema schema) {
        Entity SIM = schema.addEntity("name");                                   
        SIM.addLongProperty("_ID").primaryKey().autoincrement();                      
        SIM.addStringProperty("Note");                                              
    }
    
    
    
    
    private void initDatabase() {
        DaoMaster.DevOpenHelper openHelp = new DaoMaster.DevOpenHelper(this, "notes.db", null);
        SQLiteDatabase database = openHelp.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        simInfoDao = daoSession.getdao();
    }
