package com.generator.generatestater.utils;

import com.generator.generatestater.entity.Configuration;
import com.generator.generatestater.entity.Constant;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 代码生成器的配置工具
 */
public class ConfigUtil {
    /**
     * 代码生成器的配置信息
     */
    private static volatile Configuration configuration;

    /**
     * 获取配置信息
     *
     * @return 配置对象
     */
    public static Configuration getConfiguration() {
        // ConfigUtil.getConfiguration()会在生成代码之前调用（获取业务表元数据），这里为null通常表示用户通过generator.yaml进行配置，
        if (null == ConfigUtil.configuration) {
            synchronized (ConfigUtil.class) {
                if (null == ConfigUtil.configuration) {
                    readConfigurationFromFile(); // 从配置文件中读取配置信息
                }
            }
        }
        return ConfigUtil.configuration;
    }

    /**
     * 设置配置信息，提供给用户的外部配置接口，使用该接口则不会再到generator.yaml下读取配置
     *
     * @param configuration 配置对象
     */
    public static void setConfiguration(Configuration configuration) {
        ConfigUtil.configuration = configuration;
        checkConfiguration();
    }

    /**
     * 通过generator.yaml读取配置
     */
    private static void readConfigurationFromFile() {
        try {
            URL url = ConfigUtil.class.getClassLoader().getResource("generator.yaml");
            if (null == url || url.getPath().contains("jar")) {
                System.err.println("Can not find file named 'generator.yaml' under resources path, please make sure that you have defined that file.");
                System.exit(0);
            } else {
                String configStr = StringUtil.line2Camel(IOUtils.toString((InputStream) url.getContent()));
                InputStream inputStream = IOUtils.toInputStream(configStr, StandardCharsets.UTF_8.name());
                Yaml yaml = new Yaml();
                Configuration configuration = yaml.loadAs(inputStream,Configuration.class);
                ConfigUtil.setConfiguration(configuration);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 检查配置信息，用户可通过geneartor.yaml进行配置，或通过代码手动进行配置
     */
    private static void checkConfiguration() {
        try {
            // 用户未配置类名后缀，那么添加一个默认的空对象，这里是为了保证在用户不配置name属性时，程序能够取得默认值
            if (null == ConfigUtil.configuration.getName()) {
                Configuration.Name nameConfig = new Configuration.Name();
                if (ConfigUtil.configuration.isTkMapperEnable()) {
                    nameConfig.setDao(Constant.PLACEHOLDER + "Mapper");
                }
                ConfigUtil.configuration.setName(nameConfig);
            }
            // 检查db属性
            if (null == ConfigUtil.configuration.getDb()) {
                throw new Exception("Can not find configuration attribute named 'db', please make sure that you have configured that attribute.");
            }
            // 检查path属性
            if (null == ConfigUtil.configuration.getPath()) {
                throw new Exception("Can not find configuration attribute named 'path', please make sure that you have configured that attribute.");
            }
            // 检查db属性是否配置
            if (StringUtil.isEmpty(ConfigUtil.configuration.getDb().getUrl()) || StringUtil.isEmpty(ConfigUtil.configuration.getDb().getUsername())) {
                throw new Exception("Please configure the correct db connection parameters, i.e. url, username and password.");
            }
            // 检查顶级包名是否配置
            if (StringUtil.isEmpty(ConfigUtil.configuration.getPackageName())) {
                throw new Exception("Please configure the correct attribute named 'package-name' or 'packageName'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
