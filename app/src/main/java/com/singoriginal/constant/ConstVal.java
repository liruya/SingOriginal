package com.singoriginal.constant;

import com.singoriginal.R;

/**
 * 全局公用常量类
 * Created by lanouhn on 16/7/19.
 */
public class ConstVal {
    //系统常量
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    //颜色值 ARGB格式
    public static final int COLOR_WHITE = 0xFFFFFFFF;
    public static final int COLOR_DARKGREEN = 0xFF00BB9C;
    public static final int COLOR_HYALINE = 0x00FFFFFF;
    public static final int COLOR_GRAY = 0xFFA9B7B7;
    public static final int COLOR_BLACK = 0xFF000000;
    public static final int COLOR_SHALLOWBLACK = 0xFF333333;
    public static final int COLOR_LIGHT_BLACK = 0x55000000;

    //网络数据接口
    public static final String ADVERT_LINK = "http://goapi.5sing.kugou.com/getAdvert?t=101&tag=449";
    public static final String HOTLIST_LINK = "http://goapi.5sing.kugou.com/getRecommendSongList?";
    public static final String LIVEROOM_LINK = "http://5singlive.kugou.com/api.php?action=Mobile&fun=getLiveNew";
    public static final String MUSICTOPICS_LINK = "http://goapi.5sing.kugou.com/getTheme?t=101&o1=1&o2=20&tag=86";
    public static final String TOPRANK_LINK = "http://mobileapi.5sing.kugou.com/rank/list?";
    public static final String TOPICMORE_LINK
            = "http://topic.5sing.kugou.com/index.php?m=index&f=allThemes&sign=h2gZHDPIQ5I8xkQmuXOtwC%2FFIisI4I4%2FRRJNuFyaqj4Qp8DJc9jFNQ%3D%3D";
    public static final String SONGLIST_URL = "http://mobileapi.5sing.kugou.com/song/getsonglist?id=";
    public static final String SONGLIST_SONG_URL =
            "http://mobileapi.5sing.kugou.com/song/getsonglistsong?id=";
    public static final String SONGLIST_SONG_PARAM =
            "&songfields=ID%2CSN%2CSK%2CUID%2CST%2CDD%2CCT%2CGD%2CKM5&userfields=ID%2CNN%2CI";
    public static final String POPULAR_LINK
            = "http://mobileapi.5sing.kugou.com/song/listbysupportcard?songfields=ID%2CSN%2CSK%2CUID%2CST%2CDD%2CCT%2CGD%2CKM5%2CSC%2CSCSR&userfields=ID%2CNN%2CI";
    public static final String RANKDETAIL_LINK
            = "http://mobileapi.5sing.kugou.com/rank/detail?songfields=ID%2CSN%2CSK%2CUID%2CST%2CDD%2CCT%2CGD%2CKM5&userfields=ID%2CNN%2CI";
    public static final String DAILYRECMD_LINK
            = "http://mobileapi.5sing.kugou.com/song/getRecommendDailyList?songfields=ID%2CSN%2CSK%2CUID%2CST%2CDD%2CCT%2CGD%2CKM5&userfields=ID%2CNN%2CI";
    public static final String RECMD_MUSICIAN_LINK
            = "http://mobileapi.5sing.kugou.com/user/listmusician?songfields=ID%2CSN%2CSK%2CSW%2CSS%2CST%2CSI%2CCT%2CM%2CS%2CZQ%2CWO%2CZC%2CHY%2CYG%2CCK%2CD%2CRQ%2CDD%2CE%2CR%2CRC%2CSG%2CC%2CCS%2CLV%2CLG%2CSY%2CUID%2CPT%2CSCSR%2CSC&pageindex=1&pagesize=8";
    public static final String NEW_MUSICIAN_LINK
            = "http://mobileapi.5sing.kugou.com/musician/latestList?songfields=ID%2CSN%2CSK%2CSW%2CSS%2CST%2CSI%2CCT%2CM%2CS%2CZQ%2CWO%2CZC%2CHY%2CYG%2CCK%2CD%2CRQ%2CDD%2CE%2CR%2CRC%2CSG%2CC%2CCS%2CLV%2CLG%2CSY%2CUID%2CPT%2CSCSR%2CSC&pageindex=1&pagesize=8";
    public static final String GETSONGURL_LINK = "http://mobileapi.5sing.kugou.com/song/getSongUrl?";
    public static final String GETCURRENTDETAIL_LINK
            = "http://mobileapi.5sing.kugou.com/song/newget?songfields=ID%2CSN%2CSK%2CSW%2CSS%2CST%2CSI%2CCT%2CM%2CS%2CZQ%2CWO%2CZC%2CHY%2CYG%2CCK%2CD%2CRQ%2CDD%2CE%2CR%2CRC%2CSG%2CC%2CCS%2CLV%2CLG%2CSY%2CUID%2CPT%2CSCSR%2CSC&userfields=ID%2CNN%2CI%2CYCRQ%2CFCRQ";
    public static final String SONGLIST_INFO = "http://5sing.kugou.com/m/songlist/info/";
    public static final String SONG_INFO = "http://5sing.kugou.com/";

    /*id=yc&pagesize=20&pageindex=1*/
    /*&limit=20&maxid=0" pagesize=20&page=1*/
    //time=20160725
    public static final String VERSION = "6.0.1";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String SUCCESS = "success";
    public static final String DATA = "data";
    public static final String SONGS = "songs";
    public static final String LIST = "list";
    public static final int colorDKGreen = 0xFF00BB9C;
    public static final int colorWhite = 0xFFFFFFFF;
    public static final int colorHyaline = 0x00FFFFFF;
    public static final int ADVERT_CODE = 0x101;
    public static final int HOTLIST_CODE = 0x102;
    public static final int LIVEROOM_CODE = 0x103;
    public static final int TOPIC_CODE = 0x104;
    public static final int RANK_CODE = 0x121;
    public static final int ADVERT_DETAIL_CODE = 0x1011;
    public static final int SONGLIST_DETAIL_CODE = 0x1012;
    public static final int RANKYC_CODE = 0x1041;
    public static final int RANKFC_CODE = 0x1042;
    public static final int RANKTP_CODE = 0x1043;
    public static final int RANKPOP_CODE = 0x1044;
    public static final int DAILYRECMD_CODE = 0x1014;
    public static final int MUSICIAN_CODE = 0x1013;
    public static final int SONGBRIEF_CODE = 0x1100;

    public static final int PAGESIZE = 20;

    public static final int[] HOTLIST_RESID = {R.id.hot_tr_pic1,
            R.id.hot_tr_pic2,
            R.id.hot_tr_pic3,
            R.id.hot_tr_pic4,
            R.id.hot_tr_pic5,
            R.id.hot_tr_pic6};
    public static final int[] HOTLIST_NAMEID = {R.id.hot_tr_tv1,
            R.id.hot_tr_tv2,
            R.id.hot_tr_tv3,
            R.id.hot_tr_tv4,
            R.id.hot_tr_tv5,
            R.id.hot_tr_tv6};
    public static final int[] LIVEROOM_RESID = {R.id.live_tr_pic1,
            R.id.live_tr_pic2,
            R.id.live_tr_pic3,
            R.id.live_tr_pic4};
    public static final int[] LIVEROOM_NAMEID = {R.id.live_tr_tv1,
            R.id.live_tr_tv2,
            R.id.live_tr_tv3,
            R.id.live_tr_tv4};
    public static final int[] LIVEROOM_DESCID = {R.id.live_tr_desc1,
            R.id.live_tr_desc2,
            R.id.live_tr_desc3,
            R.id.live_tr_desc4};
    public static final int[] TOPIC_RESID = {R.id.topic_tr_pic1,
            R.id.topic_tr_pic2,
            R.id.topic_tr_pic3};
    public static final int[] TOPIC_NAMEID = {R.id.topic_tr_tv1,
            R.id.topic_tr_tv2,
            R.id.topic_tr_tv3};

    public static final String LIVE_STAT_PRESHOW = "TRAILER";
    public static final String LIVE_STAT_ONLINE = "LIVE";
    public static final String LIVE_STAT_RECOMMEND = "RECOMMEND";

    //WebLink
    public static final String CHANNEL_HTTP_PATH = "http://mobileapi.5sing.kugou.com/channel/channel?limit=12&offset=%@&sid=0";
    public static final String DYNAMIC_HOST_HTTP_PATH = "http://mobileapi.5sing.kugou.com/message/square?ordering=2&pageindex=0&pagesize=20";
    public static final String DYNAMIC_NEW_HTTP_PATH = "http://mobileapi.5sing.kugou.com/message/square?ordering=0&pageindex=0&pagesize=20";
    public static final String DYNAMIC_HTTP_PARAM = "&fields=ID%2CNN%2CI%2CB%2CP%2CC%2CSX%2CE%2CM%2CVT%2CCT%2CTYC%2CTFC%2CTBZ%2CTFD%2CTFS%2CSC%2CDJ";
    public static final String SONG_DETAILS_HTTP_PATH = "http://mobileapi.5sing.kugou.com/channel/song?limit=20&offset=0&cid=";
    public static final String HEADICON_INFO_HTTP_PATH = "http://mobileapi.5sing.kugou.com/user/get?userid=";
    public static final String HEADICON_INFO_HTTP_PARAM1 = "&fields=ID";
    public static final String HEADICON_INFO_HTTP_PARAM2 = "%2CNN%2CI%2CVT%2CTYC%2CTFC%2CTBZ%2CM%2CTFD%2CTFS%2CYCRQ%2CFCRQ%2CSC%2CBG%2CCC%2CDJ%2CSX%2CP%2CC%2CVG%2CAU%2CSR%2CSG%2CRC%2CMC%2CISC%2CF%2CUBG";
    public static final String HEADICON_WORK_HTTP_PATH = "http://mobileapi.5sing.kugou.com/song/user?userid=";
    public static final String HEADICON_WORK_HTTP_PARAM1 = "&pageindex=1&pagesize=20&songfields=ID";
    public static final String HEADICON_WORK_HTTP_PARAM2 = "%2CSN%2CSK%2CUID%2CST%2CDD%2CCT%2CGD%2CKM5&userfields=ID%2CNN%2CI";
    public static final String HEADICON_MESSAGE_HTTP_PATH = "http://mobileapi.5sing.kugou.com/comments/list?maxId=0&limit=20&rootId=";
    public static final String HEADICON_MESSAGE_HTTP_PARAM = "&rootKind=guestBook&fields=ID%2CNN%2CI";
    public static final String PROTECT_HTTP_PATH = "http://5sing.kugou.com/topic/help/copyright.html";
    public static final String HELPCENTER_HTTP_PATH = "http://5sing.kugou.com/topic/help/index.html";
    public static final String SONGCOMMENT_HTTP_PATH = "http://mobileapi.5sing.kugou.com/comments/list?maxId=0&limit=30&rootId=";
    public static final String SONGCOMMENT_HTTP_PARAM = "&rootKind=fc&fields=ID%2CNN%2CI";

    public static final String VERSION2 = "6.2.2";

    public static final int NOTIFY_SHOW = 0x10;

    //播放/暂停切换,下一首,上一首命令码
    public static final int MUSIC_PLAY_START = 0x10;
    public static final int MUSIC_PLAY_TOGGLE = 0x11;
    public static final int MUSIC_PLAY_NEXT = 0x12;
    public static final int MUSIC_PLAY_PREV = 0x13;
    public static final int MUSIC_PLAY_CLOSE = 0x14;
    public static final int GET_CURRENT_MUSIC_DETAIL = 0x15;
    public static final int SET_PLAY_POSITIN = 0x16;
    //音乐播放模式:列表循环,单曲循环,顺序播放,随机播放
    public static final int PLAY_MODE_LIST_LOOP = 0x20;
    public static final int PLAY_MODE_SINGLE_LOOP = 0x21;
    public static final int PLAY_MODE_RANDOM = 0x22;
    //音乐播放状态:
    public static final int PLAY_STATE_PREPARE = 0x30;
    public static final int PLAY_STATE_PLAYING = 0x31;
    public static final int PLAY_STATE_PAUSE = 0x32;
    //音乐类型:
    public static final int MUSIC_TYPE_NET = 0x40;
    public static final int MUSIC_TYPE_LOCAL = 0x41;

    public static final int DETAIL_UPDATE = 0x201;
    public static final int DETAIL_PROGRESS = 0x202;
    public static final int DETAIL_DURATION = 0x203;
    public static final int DETAIL_STATE = 0x204;

    public static final int SHOW_SELECT_ITEM = 0x301;

    public static final int MUSICIAN_STATE = 0x400;
}
