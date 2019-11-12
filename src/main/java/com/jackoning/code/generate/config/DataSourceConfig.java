package com.jackoning.code.generate.config;

import com.jackoning.code.generate.config.converts.MySqlTypeConvert;
import com.jackoning.code.generate.config.converts.OracleTypeConvert;
import com.jackoning.code.generate.config.converts.PostgreSqlTypeConvert;
import com.jackoning.code.generate.po.TableField;
import com.jackoning.code.generate.po.TableInfo;
import com.jackoning.code.generate.utils.CommonUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DataSourceConfig {
    private DbType dbType;
    private String schemaname = "public";
    private ITypeConvert typeConvert;
    private String url;
    private String driverName;
    private String username;
    private String password;
    private Connection conn = null;

    public DataSourceConfig() {
    }

    public DbType getDbType() {
        if (null == this.dbType) {
            if (this.driverName.contains("mysql")) {
                this.dbType = DbType.MYSQL;
            } else if (this.driverName.contains("oracle")) {
                this.dbType = DbType.ORACLE;
            } else {
                if (!this.driverName.contains("postgresql")) {
                    throw new RuntimeException("Unknown type of Database!");
                }

                this.dbType = DbType.POSTGRE_SQL;
            }
        }

        return this.dbType;
    }

    /**
     * 获取数据库下的所有表名
     */
    public  List<String> getTableNames() {
        List<String> tableNames = new ArrayList();
        Connection conn = getConn();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Get TableNames Failure", e);
        } finally {
            closeConnection();
        }
        return tableNames;
    }

    public DataSourceConfig setDbType(DbType dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getSchemaname() {
        return this.schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public ITypeConvert getTypeConvert() {
        if (null == this.typeConvert) {
            switch(this.getDbType()) {
                case ORACLE:
                    this.typeConvert = new OracleTypeConvert();
                    break;
                case POSTGRE_SQL:
                    this.typeConvert = new PostgreSqlTypeConvert();
                    break;
                default:
                    this.typeConvert = new MySqlTypeConvert();
            }
        }

        return this.typeConvert;
    }

    public DataSourceConfig setTypeConvert(ITypeConvert typeConvert) {
        this.typeConvert = typeConvert;
        return this;
    }

    public Connection getConn() {
        if(conn != null){
            return conn;
        }
        try {
            Class.forName(this.driverName);
            Properties props =new Properties();
            props.setProperty("user", this.username);
            props.setProperty("password", this.password );
            props.setProperty("remarks", "true");
            //mysql设置可以获取tables remarks信息
            props.setProperty("useInformationSchema", "true");
            conn = DriverManager.getConnection(this.url, props);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    /**
     * 关闭数据库连接
     * @param
     */
    public void closeConnection() {
        try {
            if(conn!=null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Close Connection Failure", e);
        }
    }

    /**
     * 获取生成的表信息
     * @param tables
     * @return
     */
    public List<TableInfo> getTablesInfo(String[] tables) {
        List<TableInfo> tableList = new ArrayList();
        Connection conn = getConn();
        DatabaseMetaData dmd = null;
        ResultSet rs = null;
        try {
            TableInfo table = null;
            dmd = conn.getMetaData();
            if(null == tables || 0 == tables.length){
                rs = dmd.getTables(conn.getCatalog(), getSchema(conn), "%", new String[]{"TABLE"});
                while (rs.next()){
                    table = new TableInfo();
                    getTableInfo(table, rs, dmd);
                    tableList.add(table);
                }
            }else{
                for(String tName : tables){
                    rs = dmd.getTables(conn.getCatalog(), getSchema(conn), tName, new String[]{"TABLE"});
                    rs.next();
                    table = new TableInfo();
                    getTableInfo(table, rs, dmd);
                    tableList.add(table);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("Get Database Info Failure", e);
        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (SQLException e){
                throw new RuntimeException("Exception ResultSet Close Failure", e);
            }
            closeConnection();
        }
        return tableList;
    }

    /**
     * 根据表名获取该表信息
     * @param table 表信息
     * @param dmd
     * @param rs 表结果集
     */
    public void getTableInfo(TableInfo table, ResultSet rs, DatabaseMetaData dmd) throws SQLException, Exception {
        //添加表信息
        table.setName(rs.getString("TABLE_NAME"));
        table.setComment(rs.getString("REMARKS"));
        List<TableField> tfs = new ArrayList<TableField>();
        table.setFields(tfs);
        ResultSet rsKey = dmd.getPrimaryKeys(conn.getCatalog(),getSchema(conn), table.getName().toUpperCase());
        String keyName = null;
        while(rsKey.next()){
            keyName = rsKey.getString("COLUMN_NAME").toLowerCase();
            keyName = CommonUtils.getNoUnderlineStr(keyName);
        }
        ResultSet rsFiled = dmd.getColumns(conn.getCatalog(), getSchema(conn), table.getName().toUpperCase(), "%");
        while (rsFiled.next()){
            TableField tf = new TableField();
            String fieldNm = rsFiled.getString("COLUMN_NAME").toLowerCase();
            //表字段名
            tf.setName(fieldNm);
            //字段名
            tf.setPropertyName(CommonUtils.getNoUnderlineStr(fieldNm));
            //字段注释
            tf.setComment(rsFiled.getString("REMARKS"));
            //字段类型
            tf.setType(rsFiled.getString("TYPE_NAME"));
            //设置类属性类型
            tf.setColumnType(this.getTypeConvert().processTypeConvert(tf.getType()));

            if(keyName!=null && keyName.equals(tf.getName())){
                tf.setKeyIdentityFlag(true);
                tf.setKeyFlag(true);
            }else{
                tf.setKeyIdentityFlag(false);
                tf.setKeyFlag(false);
            }
            tfs.add(tf);
        }
        table.setFields(tfs);
    }

    /**
     * 其他数据库不需要这个方法 oracle需要
     * @param conn
     * @return
     * @throws Exception
     */
    private static String getSchema(Connection conn) throws Exception {
        if(!DbType.ORACLE.getValue().equals("oracle")){
            return null;
        }
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();

    }


    public String getUrl() {
        return this.url;
    }

    public DataSourceConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public DataSourceConfig setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public DataSourceConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public DataSourceConfig setPassword(String password) {
        this.password = password;
        return this;
    }
}
