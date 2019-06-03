package deniskaminskiy.paperboy.data.api.json.importchannels

data class ImportChannelResponseJson(
    val id: Long? = null,
    val title: String? = null,
    val order: Long? = null
)


/**
 * Пример полного ответа:
 *
 * {
            "@type": "chat",
            "id": -1001117628569,
            "type": {
                "@type": "chatTypeSupergroup",
                "supergroup_id": 1117628569,
                "is_channel": true
            },
            "title": "Mash",
            "photo": {
                "@type": "chatPhoto",
                "small": {
                    "@type": "file",
                    "id": 238,
                    "size": 0,
                    "expected_size": 0,
                    "local": {
                        "@type": "localFile",
                        "path": "",
                        "can_be_downloaded": true,
                        "can_be_deleted": false,
                        "is_downloading_active": false,
                        "is_downloading_completed": false,
                        "downloaded_prefix_size": 0,
                        "downloaded_size": 0
                    },
                    "remote": {
                        "@type": "remoteFile",
                        "id": "AQADAgATfbRRDwAE4c5jDGOIyItlQAMAAQI",
                        "is_uploading_active": false,
                        "is_uploading_completed": true,
                        "uploaded_size": 0
                    }
                },
                "big": {
                    "@type": "file",
                    "id": 239,
                    "size": 0,
                    "expected_size": 0,
                    "local": {
                        "@type": "localFile",
                        "path": "",
                        "can_be_downloaded": true,
                        "can_be_deleted": false,
                        "is_downloading_active": false,
                        "is_downloading_completed": false,
                        "downloaded_prefix_size": 0,
                        "downloaded_size": 0
                    },
                    "remote": {
                        "@type": "remoteFile",
                        "id": "AQADAgATfbRRDwAElDqmo6apYh5nQAMAAQI",
                        "is_uploading_active": false,
                        "is_uploading_completed": true,
                        "uploaded_size": 0
                    }
                }
            },
            "last_message": {
                "@type": "message",
                "id": 12876513280,
                "sender_user_id": 0,
                "chat_id": -1001117628569,
                "is_outgoing": false,
                "can_be_edited": false,
                "can_be_forwarded": true,
                "can_be_deleted_only_for_self": false,
                "can_be_deleted_for_all_users": false,
                "is_channel_post": true,
                "contains_unread_mention": false,
                "date": 1559559662,
                "edit_date": 0,
                "reply_to_message_id": 0,
                "ttl": 0,
                "ttl_expires_in": 0,
                "via_bot_user_id": 0,
                "author_signature": "",
                "views": 1,
                "media_album_id": "0",
                "content": {
                    "@type": "messageVideo",
                    "video": {
                        "@type": "video",
                        "duration": 15,
                        "width": 480,
                        "height": 480,
                        "file_name": "!!!реклама.mp4",
                        "mime_type": "video/mp4",
                        "has_stickers": false,
                        "supports_streaming": true,
                        "thumbnail": {
                            "@type": "photoSize",
                            "type": "m",
                            "photo": {
                                "@type": "file",
                                "id": 501,
                                "size": 14256,
                                "expected_size": 14256,
                                "local": {
                                    "@type": "localFile",
                                    "path": "",
                                    "can_be_downloaded": true,
                                    "can_be_deleted": false,
                                    "is_downloading_active": false,
                                    "is_downloading_completed": false,
                                    "downloaded_prefix_size": 0,
                                    "downloaded_size": 0
                                },
                                "remote": {
                                    "@type": "remoteFile",
                                    "id": "AAQCABO6nYUPAAQtc3qQo_4v285xAAIC",
                                    "is_uploading_active": false,
                                    "is_uploading_completed": true,
                                    "uploaded_size": 14256
                                }
                            },
                            "width": 320,
                            "height": 320
                        },
                        "video": {
                            "@type": "file",
                            "id": 502,
                            "size": 1655609,
                            "expected_size": 1655609,
                            "local": {
                                "@type": "localFile",
                                "path": "",
                                "can_be_downloaded": true,
                                "can_be_deleted": false,
                                "is_downloading_active": false,
                                "is_downloading_completed": false,
                                "downloaded_prefix_size": 0,
                                "downloaded_size": 0
                            },
                            "remote": {
                                "@type": "remoteFile",
                                "id": "BAADAgADoQIAAiLRqUsRUL6cthA0RAI",
                                "is_uploading_active": false,
                                "is_uploading_completed": true,
                                "uploaded_size": 1655609
                            }
                        }
                    },
                    "caption": {
                        "@type": "formattedText",
                        "text": "Одна маленькая, но очень гордая птичка из Еревана чирикает по-армянски. Про говорящую Рио джан рассказал Armlink in russian — самый передовой и гостеприимный армянский канал на русском языке. \n\nГорячие новости из Армении, серьёзные и не очень, с песнями и танцами. Всё как надо. \nПодписывайся — https://t.me/armlinkrus",
                        "entities": [
                            {
                                "@type": "textEntity",
                                "offset": 105,
                                "length": 18,
                                "type": {
                                    "@type": "textEntityTypeTextUrl",
                                    "url": "https://t.me/armlinkrus"
                                }
                            },
                            {
                                "@type": "textEntity",
                                "offset": 295,
                                "length": 23,
                                "type": {
                                    "@type": "textEntityTypeUrl"
                                }
                            }
                        ]
                    },
                    "is_secret": false
                }
            },
            "order": "6698257744450826232",
            "is_pinned": false,
            "can_be_reported": true,
            "unread_count": 0,
            "last_read_inbox_message_id": 12876513280,
            "last_read_outbox_message_id": 2251799812636672,
            "unread_mention_count": 0,
            "notification_settings": {
                "@type": "notificationSettings",
                "mute_for": 587923844,
                "sound": "default",
                "show_preview": true
            },
            "reply_markup_message_id": 0,
            "client_data": ""
        }
 */