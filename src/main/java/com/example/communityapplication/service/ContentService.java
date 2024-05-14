package com.example.communityapplication.service;

import com.example.communityapplication.model.*;

import java.util.List;

public interface ContentService {
    Content getById(int contentId);
    Content saveContent(ContentForm contentForm, User user);
    List<ContentCard> searchByTitle(String title);
    List<ContentCard> searchByFieldValues(String keyword);
    List<ContentCard> search(String keyword);
    List<ContentCard> getContentCardsByUser(User user);
}
