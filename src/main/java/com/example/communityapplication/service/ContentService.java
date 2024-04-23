package com.example.communityapplication.service;

import com.example.communityapplication.model.*;

import java.util.List;

public interface ContentService {
    public Content getById(Long contentId);
    public void saveContentAndFieldValues(ContentForm contentForm, User user);
}
