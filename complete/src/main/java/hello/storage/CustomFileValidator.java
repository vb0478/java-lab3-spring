package hello.storage;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CustomFileValidator implements Validator{
    public static final String MIME_TYPE="image/jpeg";
    public static final long MB_IN_BYTES = 2097152;
    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FileUploadModel fileUploadModel = (FileUploadModel)target;
        MultipartFile file = fileUploadModel.getFile();
        if(file.isEmpty()){
            errors.rejectValue("file", "upload.file.required");
        }
        else if(!MIME_TYPE.equalsIgnoreCase(file.getContentType())){
            errors.rejectValue("file", "upload.invalid.file.type");
        }

        else if(file.getSize() < MB_IN_BYTES){
            errors.rejectValue("file", "upload.exceeded.file.size");
        }

    }

}