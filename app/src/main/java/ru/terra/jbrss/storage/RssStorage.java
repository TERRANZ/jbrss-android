package ru.terra.jbrss.storage;

import android.database.Cursor;

import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.dto.FeedPostDto;

public interface RssStorage {
    Cursor getFeeds(Long fromDate);

    Cursor getPosts(Integer feedId);

    void saveFeeds(FeedListDto dtos);

    void savePosts(FeedPostDto dtos);
}
