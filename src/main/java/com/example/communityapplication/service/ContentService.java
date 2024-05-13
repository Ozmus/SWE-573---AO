package com.example.communityapplication.service;

import com.example.communityapplication.model.*;

import java.util.List;

public interface ContentService {
    Content getById(int contentId);
    Content saveContent(ContentForm contentForm, User user);
    List<ContentCard> searchByTitle(String title);
    List<ContentCard> getContentCardsByUser(User user);
}
