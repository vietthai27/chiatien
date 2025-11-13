package com.thai27.chiatien.ImageKit;

import com.thai27.chiatien.DTO.Response.UploadImageResponse;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.ResponseMetaData;
import io.imagekit.sdk.models.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageKitService {

    private final ImageKit imageKit;

    public UploadImageResponse uploadImage(MultipartFile file) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        byte[] fileBytes = file.getBytes();
        FileCreateRequest request = new FileCreateRequest(fileBytes, file.getOriginalFilename());
        Result result = imageKit.upload(request);
        return new UploadImageResponse(result.getUrl(), result.getFileId());
    }

    public boolean deleteImage(String fileId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException {
        ResponseMetaData response = imageKit.deleteFile(fileId).getResponseMetaData();
        return response.getHttpStatusCode() == 204;
    }
}
