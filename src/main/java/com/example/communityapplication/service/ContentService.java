package com.example.communityapplication.service;

import com.example.communityapplication.model.*;

import java.util.List;

public interface ContentService {
    public Content getById(Long contentId);
    public Content saveContent(ContentForm contentForm, User user);
}
