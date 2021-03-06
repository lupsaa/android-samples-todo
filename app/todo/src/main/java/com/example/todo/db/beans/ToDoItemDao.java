package com.example.todo.db.beans;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TO_DO_ITEM".
*/
public class ToDoItemDao extends AbstractDao<ToDoItem, Long> {

    public static final String TABLENAME = "TO_DO_ITEM";

    /**
     * Properties of entity ToDoItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Position = new Property(1, Integer.class, "position", false, "POSITION");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Description = new Property(3, String.class, "description", false, "DESCRIPTION");
        public final static Property SectionId = new Property(4, long.class, "sectionId", false, "SECTION_ID");
    };

    private DaoSession daoSession;

    private Query<ToDoItem> section_ToDoItemsQuery;

    public ToDoItemDao(DaoConfig config) {
        super(config);
    }
    
    public ToDoItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TO_DO_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"POSITION\" INTEGER," + // 1: position
                "\"TITLE\" TEXT," + // 2: title
                "\"DESCRIPTION\" TEXT," + // 3: description
                "\"SECTION_ID\" INTEGER NOT NULL );"); // 4: sectionId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TO_DO_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ToDoItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer position = entity.getPosition();
        if (position != null) {
            stmt.bindLong(2, position);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
        stmt.bindLong(5, entity.getSectionId());
    }

    @Override
    protected void attachEntity(ToDoItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ToDoItem readEntity(Cursor cursor, int offset) {
        ToDoItem entity = new ToDoItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // position
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // description
            cursor.getLong(offset + 4) // sectionId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ToDoItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPosition(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSectionId(cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ToDoItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ToDoItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "toDoItems" to-many relationship of Section. */
    public List<ToDoItem> _querySection_ToDoItems(long sectionId) {
        synchronized (this) {
            if (section_ToDoItemsQuery == null) {
                QueryBuilder<ToDoItem> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.SectionId.eq(null));
                section_ToDoItemsQuery = queryBuilder.build();
            }
        }
        Query<ToDoItem> query = section_ToDoItemsQuery.forCurrentThread();
        query.setParameter(0, sectionId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getSectionDao().getAllColumns());
            builder.append(" FROM TO_DO_ITEM T");
            builder.append(" LEFT JOIN SECTION T0 ON T.\"SECTION_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ToDoItem loadCurrentDeep(Cursor cursor, boolean lock) {
        ToDoItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Section section = loadCurrentOther(daoSession.getSectionDao(), cursor, offset);
         if(section != null) {
            entity.setSection(section);
        }

        return entity;    
    }

    public ToDoItem loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ToDoItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ToDoItem> list = new ArrayList<ToDoItem>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ToDoItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ToDoItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
