package LorenzoBaldassari.Capstone.Servicies;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinaryUploader;

    public String uploadPicture(MultipartFile file) throws IOException {
        String url= (String) cloudinaryUploader.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        return url;
    }


}
