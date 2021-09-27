package com.generator.generatestater.entity;

import java.io.Serializable;

/**
 * Generator 配置类
 */
public class Configuration implements Serializable {
    /**
     * 代码作者
     */
    private String author;
    /**
     * 顶级包名
     */
    private String packageName;
    /**
     * 类型转换器全限定类名
     */
    private String convertor;
    /**
     * 启用lombok
     */
    private boolean lombokEnable;
    /**
     * 是否将mybatis的xml映射文件放在源文件目录下
     */
    private boolean mapperUnderSource;
    /**
     * 启用swagger
     */
    private boolean swaggerEnable;
    /**
     * mybatis-plus模式
     */
    private boolean mybatisPlusEnable;
    /**
     * jpa模式
     */
    private boolean jpaEnable;
    /**
     * 文件覆盖
     */
    private boolean fileOverride;
    /**
     * id策略（auto：数据库自增，uuid：生成uuid）
     */
    private IdStrategy idStrategy;
    /**
     * 代码生成路径
     */
    private Path path;
    /**
     * 数据库配置
     */
    private Db db;
    /**
     * 代码文件后缀
     */
    private Name name;

    private TkMapper tkMapper;

    private boolean tkMapperEnable;

    private boolean generateEmptyMapper;

    public void setTkMapperEnable(boolean tkMapperEnable) {
        this.tkMapperEnable = tkMapperEnable;
    }

    public boolean isTkMapperEnable() {
        return tkMapperEnable;
    }

    public boolean isGenerateEmptyMapper() {
        return generateEmptyMapper;
    }

    public void setGenerateEmptyMapper(boolean generateEmptyMapper) {
        this.generateEmptyMapper = generateEmptyMapper;
    }

    public TkMapper getTkMapper() {
        return tkMapper;
    }

    public void setTkMapper(TkMapper tkMapper) {
        this.tkMapper = tkMapper;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getConvertor() {
        return convertor;
    }

    public void setConvertor(String convertor) {
        this.convertor = convertor;
    }

    public boolean isLombokEnable() {
        return lombokEnable;
    }

    public void setLombokEnable(boolean lombokEnable) {
        this.lombokEnable = lombokEnable;
    }

    public boolean isMapperUnderSource() {
        return mapperUnderSource;
    }

    public void setMapperUnderSource(boolean mapperUnderSource) {
        this.mapperUnderSource = mapperUnderSource;
    }

    public boolean isSwaggerEnable() {
        return swaggerEnable;
    }

    public void setSwaggerEnable(boolean swaggerEnable) {
        this.swaggerEnable = swaggerEnable;
    }

    public boolean isMybatisPlusEnable() {
        return mybatisPlusEnable;
    }

    public void setMybatisPlusEnable(boolean mybatisPlusEnable) {
        this.mybatisPlusEnable = mybatisPlusEnable;
    }

    public boolean isJpaEnable() {
        return jpaEnable;
    }

    public void setJpaEnable(boolean jpaEnable) {
        this.jpaEnable = jpaEnable;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public IdStrategy getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(IdStrategy idStrategy) {
        this.idStrategy = idStrategy;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    /**
     * 数据库配置
     */
    public static class Db {
        /**
         * 数据库URL
         */
        private String url;
        /**
         * 数据库用户名
         */
        private String username;
        /**
         * 数据库密码
         */
        private String password;

        /**
         * 数据库驱动
         */
        private String driverClass;

        public Db() {
        }

        public Db(String url, String username, String password,String driverClass) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClass = driverClass;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClass() {
            return driverClass;
        }

        public void setDriverClass(String driverClass) {
            this.driverClass = driverClass;
        }
    }

    public static class TkMapper{

        private String baseMapper;

        public TkMapper() {
        }

        public TkMapper(String baseMapper) {
            this.baseMapper = baseMapper;
        }

        public String getBaseMapper() {
            return baseMapper;
        }

        public void setBaseMapper(String baseMapper) {
            this.baseMapper = baseMapper;
        }

    }

    /**
     * 代码路径配置
     */
    public static class Path {
        /**
         * Controller代码包路径
         */
        private String controller = "";
        /**
         * Service或ServiceImpl代码包路径
         */
        private String service = "";
        /**
         * Service接口代码包路径
         */
        private String interf = "";
        /**
         * Dao代码包路径
         */
        private String dao = "";
        /**
         * Entity代码包路径
         */
        private String entity = "";
        /**
         * Mapper映射文件路径
         */
        private String mapper = "";

        /**
         * req包路径
         */
        private String req = "";

        /**
         * res包路径
         */
        private String res = "";

        /**
         * search包路径
         */
        private String search = "";

        public Path() {
        }

        public Path(String controller, String service, String interf, String dao, String entity, String mapper,String req,String res,String search) {
            this.controller = controller;
            this.service = service;
            this.interf = interf;
            this.dao = dao;
            this.entity = entity;
            this.mapper = mapper;
            this.req = req;
            this.res = res;
            this.search = search;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public String getRes() {
            return res;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public String getReq() {
            return req;
        }

        public void setReq(String req) {
            this.req = req;
        }

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getInterf() {
            return interf;
        }

        public void setInterf(String interf) {
            this.interf = interf;
        }

        public String getDao() {
            return dao;
        }

        public void setDao(String dao) {
            this.dao = dao;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getMapper() {
            return mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

    }

    /**
     * 类名配置
     */
    public static class Name {
        /**
         * Controller类的类名，默认为 $sController
         */
        private String controller = Constant.PLACEHOLDER + "Controller";
        /**
         * Service类或ServiceImpl类的类名，默认为$sService或$sServiceImpl
         */
        private String service = Constant.PLACEHOLDER + "Service";
        /**
         * Service接口类的类名，默认为$sService
         */
        private String interf = Constant.PLACEHOLDER + "Service";
        /**
         * Dao类的类名，默认为$sDao
         */
        private String dao = Constant.PLACEHOLDER + "Dao";
        /**
         * Entity类的类名，默认为$s
         */
        private String entity = Constant.PLACEHOLDER;
        /**
         * Mapper映射文件的文件名，默认$sMapper
         */
        private String mapper = Constant.PLACEHOLDER + "Mapper";
        /**
         * Req类名，默认$sReq
         */
        private String req = Constant.PLACEHOLDER + "Req";
        /**
         * Res类名，默认$sRes
         */
        private String res = Constant.PLACEHOLDER + "Res";

        public Name() {
        }

        public Name(String controller, String service, String dao, String entity, String mapper,String req,String res) {
            this.controller = controller;
            this.service = service;
            this.dao = dao;
            this.entity = entity;
            this.mapper = mapper;
            this.req = req;
            this.res = res;
        }

        public String getReq() {
            return req;
        }

        public String getRes() {
            return res;
        }

        public void setReq(String req) {
            this.req = req;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public String getController() {
            return controller;
        }

        public void setController(String controller) {
            this.controller = controller;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getInterf() {
            return interf;
        }

        public void setInterf(String interf) {
            this.interf = interf;
        }

        public String getDao() {
            return dao;
        }

        public void setDao(String dao) {
            this.dao = dao;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getMapper() {
            return mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

    }

}
