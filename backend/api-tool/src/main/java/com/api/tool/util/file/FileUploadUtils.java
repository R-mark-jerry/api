package com.api.tool.util.file;

import com.api.common.constant.Constants;
import com.api.common.exception.ServiceException;
import com.api.common.utils.StringUtils;
import com.api.common.utils.security.SecurityUtils;
import com.api.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件上传工具类
 * 
 * @author api
 */
public class FileUploadUtils
{
    /**
     * 默认大小 50MB
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = "profile";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.path}")
    private String uploadFolder;

    /**
     * 资源映射路径
     * 
     * @param uploadPath 上传文件路径
     * @return 资源映射路径
     */
    public static String getPathFileName(String uploadPath)
    {
        return Paths.get(uploadPath).getFileName().toString();
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 允许的文件类型
     * @return 返回上传成功的文件名
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws IOException
    {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new ServiceException("文件名过长，请限制在" + FileUploadUtils.DEFAULT_FILE_NAME_LENGTH + "个字符内");
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir) + "/" + fileName;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(fileName);
        fileName = datePath() + "/" + encodingFilename(fileName);
        if (StringUtils.isNotEmpty(extension))
        {
            fileName = fileName + "." + extension;
        }
        return fileName;
    }

    /**
     * 获取文件名的日期路径
     * 
     * @return 日期路径
     */
    private static final String datePath()
    {
        Date now = new Date();
        return DateUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String filename)
    {
        return filename.replace(" ", "");
    }

    /**
     * 获取文件名的后缀
     * 
     * @param file 文件名
     * @return 后缀名
     */
    private static final String getExtension(String file)
    {
        int separatorIndex = file.lastIndexOf(Constants.DOT);
        if (separatorIndex < 0)
        {
            return "";
        }
        return file.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 获取上传路径
     * 
     * @param uploadDir 上传路径
     * @param fileName 文件名
     * @return 完整路径
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir, fileName);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(fileName);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException(allowedExtension, MimeTypeUtils.IMAGE_EXTENSION);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException(allowedExtension, MimeTypeUtils.FLASH_EXTENSION);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException(allowedExtension, MimeTypeUtils.MEDIA_EXTENSION);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException(allowedExtension, MimeTypeUtils.VIDEO_EXTENSION);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, allowedExtension);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名称
     * 
     * @param filename 文件名
     * @return 不带后缀的文件名
     */
    public static final String getName(String filename)
    {
        if (filename == null)
        {
            return null;
        }
        int separatorIndex = filename.lastIndexOf(Constants.DOT);
        return filename.substring(0, separatorIndex);
    }

    /**
     * 获取不带后缀的文件名
     * 
     * @param file 文件
     * @return 不带后缀的文件名
     */
    public static final String getName(MultipartFile file)
    {
        return getName(Objects.requireNonNull(file.getOriginalFilename()));
    }

    /**
     * 获取文件后缀
     * 
     * @param file 文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        return getExtension(Objects.requireNonNull(file.getOriginalFilename()));
    }

    /**
     * 获取下载路径
     * 
     * @param filename 文件名称
     * @return 下载路径
     */
    public static String getDownloadPath(String filename)
    {
        return getDefaultBaseDir() + "/" + filename;
    }

    /**
     * 获取默认的上传路径
     * 
     * @return 上传路径
     */
    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

    /**
     * 设置默认的上传路径
     * 
     * @param defaultBaseDir 上传路径
     */
    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }
}