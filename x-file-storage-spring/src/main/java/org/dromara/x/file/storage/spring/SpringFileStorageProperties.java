package org.dromara.x.file.storage.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageProperties.AliyunOssConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.AmazonS3Config;
import org.dromara.x.file.storage.core.FileStorageProperties.AmazonS3V2Config;
import org.dromara.x.file.storage.core.FileStorageProperties.AzureBlobStorageConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.GoogleCloudStorageConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.MinioConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.QiniuKodoConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.TencentCosConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.UpyunUssConfig;
import org.dromara.x.file.storage.core.FileStorageProperties.VolcengineTosConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
@Component
@ConditionalOnMissingBean(SpringFileStorageProperties.class)
@ConfigurationProperties(prefix = "dromara.x-file-storage")
public class SpringFileStorageProperties {

    /**
     * 默认存储平台
     */
    private String defaultPlatform = "";
    /**
     * 缩略图后缀，例如【.min.jpg】【.png】
     */
    private String thumbnailSuffix = ".min.jpg";
    /**
     * 上传时不支持元数据时抛出异常
     */
    private Boolean uploadNotSupportMetadataThrowException = true;
    /**
     * 上传时不支持 ACL 时抛出异常
     */
    private Boolean uploadNotSupportAclThrowException = true;
    /**
     * 复制时不支持元数据时抛出异常
     */
    private Boolean copyNotSupportMetadataThrowException = true;
    /**
     * 复制时不支持 ACL 时抛出异常
     */
    private Boolean copyNotSupportAclThrowException = true;
    /**
     * 移动时不支持元数据时抛出异常
     */
    private Boolean moveNotSupportMetadataThrowException = true;
    /**
     * 移动时不支持 ACL 时抛出异常
     */
    private Boolean moveNotSupportAclThrowException = true;
    /**
     * 启用 byte[] 文件包装适配器
     */
    private Boolean enableByteFileWrapper = true;
    /**
     * 启用 URI 文件包装适配器，包含 URL 和 String
     */
    private Boolean enableUriFileWrapper = true;
    /**
     * 启用 InputStream 文件包装适配器
     */
    private Boolean enableInputStreamFileWrapper = true;
    /**
     * 启用 HttpServletRequest 文件包装适配器
     */
    private Boolean enableHttpServletRequestFileWrapper = true;
    /**
     * 启用 MultipartFile 文件包装适配器
     */
    private Boolean enableMultipartFileWrapper = true;
    /**
     * 阿里云 OSS
     */
    private List<? extends SpringAliyunOssConfig> aliyunOss = new ArrayList<>();
    /**
     * 七牛云 Kodo
     */
    private List<? extends SpringQiniuKodoConfig> qiniuKodo = new ArrayList<>();
    /**
     * 腾讯云 COS
     */
    private List<? extends SpringTencentCosConfig> tencentCos = new ArrayList<>();
    /**
     * 又拍云 USS
     */
    private List<? extends SpringUpyunUssConfig> upyunUss = new ArrayList<>();
    /**
     * MinIO
     */
    private List<? extends SpringMinioConfig> minio = new ArrayList<>();

    /**
     * Amazon S3
     */
    private List<? extends SpringAmazonS3Config> amazonS3 = new ArrayList<>();

    /**
     * Amazon S3
     */
    private List<? extends SpringAmazonS3V2Config> amazonS3V2 = new ArrayList<>();

    /**
     * GoogleCloud Storage
     */
    private List<? extends SpringGoogleCloudStorageConfig> googleCloudStorage = new ArrayList<>();

    /**
     * Azure Blob Storage
     */
    private List<? extends SpringAzureBlobStorageConfig> azureBlob = new ArrayList<>();

    /**
     * 火山引擎 TOS
     */
    private List<? extends SpringVolcengineTosConfig> volcengineTos = new ArrayList<>();

    /**
     * 转换成 FileStorageProperties ，并过滤掉没有启用的存储平台
     */
    public FileStorageProperties toFileStorageProperties() {
        FileStorageProperties properties = new FileStorageProperties();
        properties.setDefaultPlatform(defaultPlatform);
        properties.setThumbnailSuffix(thumbnailSuffix);
        properties.setUploadNotSupportMetadataThrowException(uploadNotSupportMetadataThrowException);
        properties.setUploadNotSupportAclThrowException(uploadNotSupportAclThrowException);
        properties.setCopyNotSupportMetadataThrowException(copyNotSupportMetadataThrowException);
        properties.setCopyNotSupportAclThrowException(copyNotSupportAclThrowException);
        properties.setMoveNotSupportMetadataThrowException(moveNotSupportMetadataThrowException);
        properties.setMoveNotSupportAclThrowException(moveNotSupportAclThrowException);
        properties.setAliyunOss(aliyunOss.stream()
                .filter(SpringAliyunOssConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setQiniuKodo(qiniuKodo.stream()
                .filter(SpringQiniuKodoConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setTencentCos(tencentCos.stream()
                .filter(SpringTencentCosConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setUpyunUss(
                upyunUss.stream().filter(SpringUpyunUssConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setMinio(
                minio.stream().filter(SpringMinioConfig::getEnableStorage).collect(Collectors.toList()));
        properties.setAmazonS3(
                amazonS3.stream().filter(SpringAmazonS3Config::getEnableStorage).collect(Collectors.toList()));
        properties.setAmazonS3V2(amazonS3V2.stream()
                .filter(SpringAmazonS3V2Config::getEnableStorage)
                .collect(Collectors.toList()));

        properties.setGoogleCloudStorage(googleCloudStorage.stream()
                .filter(SpringGoogleCloudStorageConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setAzureBlob(azureBlob.stream()
                .filter(SpringAzureBlobStorageConfig::getEnableStorage)
                .collect(Collectors.toList()));
        properties.setVolcengineTos(volcengineTos.stream()
                .filter(SpringVolcengineTosConfig::getEnableStorage)
                .collect(Collectors.toList()));

        return properties;
    }

    /**
     * 阿里云 OSS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAliyunOssConfig extends AliyunOssConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 七牛云 Kodo
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringQiniuKodoConfig extends QiniuKodoConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 腾讯云 COS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringTencentCosConfig extends TencentCosConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 又拍云 USS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringUpyunUssConfig extends UpyunUssConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * MinIO
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringMinioConfig extends MinioConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * Amazon S3
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAmazonS3Config extends AmazonS3Config {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * Amazon S3
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAmazonS3V2Config extends AmazonS3V2Config {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * GoogleCloud Storage
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringGoogleCloudStorageConfig extends GoogleCloudStorageConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * AzureBlob Storage
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringAzureBlobStorageConfig extends AzureBlobStorageConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }

    /**
     * 火山引擎 TOS
     */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class SpringVolcengineTosConfig extends VolcengineTosConfig {
        /**
         * 启用存储
         */
        private Boolean enableStorage = false;
    }
}
