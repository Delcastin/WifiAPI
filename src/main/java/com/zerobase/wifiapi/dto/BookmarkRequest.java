package com.zerobase.wifiapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkRequest {

    private Long bookmarkGroupId;
    private String mgrNo;
    private String wifiName;
}
