package com.example.communityapplication.service;

import com.example.communityapplication.model.*;

import java.util.List;

public interface ContentService {
    public Content getById(int contentId);
    public Content saveContent(ContentForm contentForm, User user);
    public List<ContentCard> getContentCardsByUser(User user);
}
