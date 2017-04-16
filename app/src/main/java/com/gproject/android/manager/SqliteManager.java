package com.gproject.android.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gproject.android.consts.DBConsts;
import com.gproject.android.db.DaoMaster;
import com.gproject.android.db.DaoSession;
import com.gproject.android.db.UserEntity;
import com.gproject.android.db.UserEntityDao;

import de.greenrobot.dao.query.QueryBuilder;


public class SqliteManager {

    private static SqliteManager instance;
    private DaoMaster daoMaster;
    private SQLiteDatabase sqLiteDatabase;
    private DaoSession daoSession;
    private UserEntityDao userEntityDao;


    public SqliteManager(Context context) {
        initDaoSession(context);
    }

    public static SqliteManager GetInstance(Context context) {
        if (instance == null) {
            synchronized (SqliteManager.class) {
                if (instance == null) {
                    instance = new SqliteManager(context);
                }
            }
        }
        return instance;
    }


    private void initDaoSession(Context context) {
        if (daoSession == null) {
            synchronized (DaoSession.class) {
                if (daoSession == null) {
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DBConsts.DB_NAME, null);
                    sqLiteDatabase = helper.getWritableDatabase();
                    daoMaster = new DaoMaster(sqLiteDatabase);
                    daoSession = daoMaster.newSession();

                    userEntityDao = daoSession.getUserEntityDao();
                }
            }
        }
    }

    public void clearAllTables() {
        daoMaster.dropAllTables(sqLiteDatabase, true);
        daoMaster.createAllTables(sqLiteDatabase, true);
    }

    public void updateUserEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return;
        }
        if (getActivityOptionEntityById(userEntity.getId()) != null) {
            userEntityDao.update(userEntity);
        } else {
            userEntityDao.insert(userEntity);
        }
    }

    public UserEntity getActivityOptionEntityById(long id) {
        QueryBuilder<UserEntity> builder = userEntityDao.queryBuilder().where(UserEntityDao.Properties.Id.eq(id));
        return builder.unique();
    }
}
