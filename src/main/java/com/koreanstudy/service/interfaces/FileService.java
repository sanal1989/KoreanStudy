package com.koreanstudy.service.interfaces;

import java.io.IOException;

public interface FileService {

    byte[] getFileById(Long id) throws IOException;

}
